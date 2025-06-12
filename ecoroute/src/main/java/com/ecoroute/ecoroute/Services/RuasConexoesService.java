package com.ecoroute.ecoroute.Services;

import com.ecoroute.ecoroute.Model.Bairro;
import com.ecoroute.ecoroute.Model.RuasConexoes;
import com.ecoroute.ecoroute.Repositories.BairroRepository;
import com.ecoroute.ecoroute.Repositories.RuasConexoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RuasConexoesService {

    private final RuasConexoesRepository ruasConexoesRepository;
    private final BairroService bairroService;
    private int[][] grafo;
    private int[][] matrizAdjacencia;

    @Autowired
    public RuasConexoesService(RuasConexoesRepository ruasConexoesRepository, BairroService bairroService) {
        this.ruasConexoesRepository = ruasConexoesRepository;
        this.bairroService = bairroService;
        carregarGrafo();
        carregarMatrizAdjacente();
    }

    public int[][] grafo(){
        return this.grafo;
    }

    private void carregarGrafo() {
        List<RuasConexoes> ruasConexoes = ruasConexoesRepository.findAll();
        this.grafo = new int[ruasConexoes.size()][3];

        for (int i = 0; i < ruasConexoes.size(); i++) {
            RuasConexoes rc = ruasConexoes.get(i);
            this.grafo[i][0] = rc.getBairroOrigem().getId();
            this.grafo[i][1] = rc.getBairroDestino().getId();
            this.grafo[i][2] = rc.getDistancia(); // metros
        }

        System.out.println("MATRIZ DE INCIDENCIA");
        System.out.println("========================================================================================");
        imprimirMatriz(this.grafo);
        System.out.println("========================================================================================");

    }

    //só para atualizar se precisar
    public void atualizarGrafo(){
        carregarGrafo();
    }

    public List<RuasConexoes> listarTodos() {
        return ruasConexoesRepository.findAll();
    }

    public Optional<RuasConexoes> buscarPorId(int id){
        return ruasConexoesRepository.findById(id);
    }

    public void salvar(RuasConexoes ruasConexoes) {
        //Está olhando se a ida e volta já existem
        boolean Existe = ruasConexoesRepository.existsByBairroOrigemIdAndBairroDestinoId(
                ruasConexoes.getBairroOrigem().getId(),
                ruasConexoes.getBairroDestino().getId()
        );

        //Se não existir ele registra os 2
        if (!Existe) {
            // Cria a conexão de volta
            RuasConexoes ruaVolta = new RuasConexoes(
                    ruasConexoes.getBairroDestino(),
                    ruasConexoes.getBairroOrigem(),
                    ruasConexoes.getDistancia()
            );
            ruasConexoesRepository.save(ruasConexoes);//SALVA A IDA
            ruasConexoesRepository.save(ruaVolta);//SALVA A VOLTA
            atualizarGrafo(); //atualiza o grafo atual
        } else {
            throw new RuntimeException("Conexão já existente entre os bairros.");
        }
    }

    public void deletar(int id) {
        /*
           POR QUE ESSE MÉTODO É COMPLEXO E ASSIM?
           porque o que está acontecendo aqui é o seguinte
           O GRAFO É DE MÃO DUPLA, OU SEJA, CADA PONTO DO GRAFO QUE VAI, VOLTA
           ISSO SIGNIFICA QUE SE A>B é implicito que B>A com a mesma distância
           Eu poderia fazer isso no carregamento do grafo? Sim. MAS Haveriam algums problemas
           As atualizações do grafo seriam mais pesadas, lentas e passariam por mais validações
           Resolvi salvar o matriz pronta para uso no banco direto, não economiza com espaço, mas economiza com processamento, e na situação atual do desenvolvimento
           acredito ser mais importante, logo, ao salvar os grafos no banco fiz um processo que ele escreveu cada linha com o seu inverso abaixo, logo no grafo salvo
           no banco estará algo parecido com isso
           6 -> 8 distancia
           8 -> 6 distancia     A DISTÂNCIA É A MESMA DE CIMA
           esse padrão é importante para que o algoritmo de djisktra seja executado
           o problema que isso causou é que na hora de deletar uma conexão de vertice o banco estava deletando só a IDA e não deletava a volta
           esse código resolveu esse problema com uma query custom no Repository que equivale a falar para o banco o seguinte comando
           DELETE FROM ruas_conexoes
           WHERE (bairro_origem_id = :origemId AND bairro_destino_id = :destinoId)
           OR (bairro_origem_id = :destinoId AND bairro_destino_id = :origemId);

           Ou seja, ele olha para o 6 -> 8 e deleta
           e procura o 8 -> 6 e deleta também
           sendo: SE BAIRRO (6 ^ 8) V (8 ^ 6) DELETE
           Dessa maneira eu garanto que a volta, o inverso será deletado.
        */
        Optional<RuasConexoes> conexao = ruasConexoesRepository.findById(id);

        if (conexao.isPresent()) {
            RuasConexoes rc = conexao.get();

            //deleta a conexão normal
            ruasConexoesRepository.deleteById(id);

            //deleta a conexão de volta
            //com uma query costumizada no repository, veja bem que ela tá deletando no sentido origem>destino e depois destino>origem
            ruasConexoesRepository.deleteByBairroOrigemIdAndBairroDestinoId(
                    rc.getBairroDestino().getId(),
                    rc.getBairroOrigem().getId()
            );
        } else {
            throw new RuntimeException("Conexão com ID " + id + " não encontrada.");
        }
    }

    //MESMO PROBLEMA DO DELETAR, NESSE CASO ELE EDITA A IDA E A VOLTA ATRAVÉS DOS NOVOS DADOS INFORMADOS
    public void atualizarDistanciaEntreBairros(RuasConexoes ruasConexao) {
        // Buscar ida
        int origemId = ruasConexao.getBairroOrigem().getId();
        int destinoId = ruasConexao.getBairroDestino().getId();
        int novaDistancia = ruasConexao.getDistancia();

        Optional<RuasConexoes> ida = ruasConexoesRepository
                .findByBairroOrigemIdAndBairroDestinoId(origemId, destinoId);

        ida.ifPresent(conexao -> {
            conexao.setDistancia(novaDistancia);
            ruasConexoesRepository.save(conexao);
        });

        // Buscar volta
        Optional<RuasConexoes> volta = ruasConexoesRepository
                .findByBairroOrigemIdAndBairroDestinoId(destinoId, origemId);

        volta.ifPresent(conexao -> {
            conexao.setDistancia(novaDistancia);
            ruasConexoesRepository.save(conexao);
        });
    }

    public int[][] carregarMatrizAdjacente(){
        int totalBairros = bairroService.totalDeBairros();

        // Matriz com +1 coluna (coluna 0 guarda o ID do bairro)
        int[][] matriz = new int[totalBairros][totalBairros + 1];

        int INFINITO = Integer.MAX_VALUE;

        // IDs reais dos bairros em ordem
        List<Integer> idsBairros = bairroService.obterTodosIdsOrdenados(); // você precisa garantir isso
        Map<Integer, Integer> idParaIndice = new HashMap<>();

        // Mapeia o ID real para o índice da matriz
        for (int i = 0; i < totalBairros; i++) {
            int idReal = idsBairros.get(i);
            matriz[i][0] = idReal; // coluna 0 armazena o ID real
            idParaIndice.put(idReal, i);
        }

        // Inicializa a matriz com 0 na diagonal e INFINITO nas demais posições
        for (int i = 0; i < totalBairros; i++) {
            for (int j = 1; j <= totalBairros; j++) {
                if ((j - 1) == i) {
                    matriz[i][j] = 0; // distância de um bairro para ele mesmo
                } else {
                    matriz[i][j] = INFINITO;
                }
            }
        }

        // Preenche com as conexões do banco
        List<RuasConexoes> conexoes = ruasConexoesRepository.findAll();
        for (RuasConexoes rc : conexoes) {
            int idOrigem = rc.getBairroOrigem().getId();
            int idDestino = rc.getBairroDestino().getId();
            int distancia = rc.getDistancia();

            Integer i = idParaIndice.get(idOrigem);
            Integer j = idParaIndice.get(idDestino);

            if (i != null && j != null) {
                matriz[i][j + 1] = distancia; // +1 pois col 0 guarda o ID
            }
        }

        System.out.println("MATRIZ DE ADJACENCIA");
        System.out.println("========================================================================================");
        imprimirMatriz(matriz);
        System.out.println("========================================================================================");
        return matriz;

    }

    public static void imprimirMatriz(int[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + "\t"); // \t para alinhar colunas
            }
            System.out.println(); // Pula para a próxima linha
        }
    }

    //AQUI COMEÇARA A APLICAÇÃO DE DJISKTRA PARA ACHAR O CAMINHO MENOR ENTRE DOIS PONTOS
    public String djkstra(int[][] matriz, int origemId, int destinoId){

        int n = matriz.length;
        int INFINITO = Integer.MAX_VALUE;

        int[] distancias = new int[n];
        boolean[] visitado = new boolean[n];
        int[] anteriores = new int[n];

        // Inicializar
        //TODAS AS DISTÂNCIAS SÃO INFINITAS
        //NÃO TEM INTERIORES PQ NÃO ANDOU
        //COMEÇO
        for (int i = 0; i < n; i++) {
            distancias[i] = INFINITO;
            anteriores[i] = -1;
        }

        int indiceOrigem = -1; //VALOR IMPOSSÍVEL DE ACONTECER POIS NÃO EXISTE POSIÇÃO -1 EM UM VETOR
        int indiceDestino = -1; //PQ? PQ SE ELE PASSAR NO VETOR, NÃO ENCONTRAR, O VALOR CONTINUA E EU SEI QUE OS NÓS NÃO EXISTEM

        for (int i = 0; i < n; i++) {
            if (matriz[i][0] == origemId) {
                indiceOrigem = i;
            }
            if (matriz[i][0] == destinoId) {
                indiceDestino = i;
            }
        }

        //AQUI O MOTIVO DO -1, CHECA SE OS NÓS EXISTEM OU NÃO
        if (indiceOrigem == -1 || indiceDestino == -1) {
            return "Erro: Bairro de origem ou destino não encontrado na matriz.";
        }

        distancias[indiceOrigem] = 0;

        //AQUI SIM COMEÇA A INTERAR O ALGORITMO
        for(int count = 0; count < (n-1); count++){
            //Escolher o nó não visitado com a menor distância
            //VARIÁVEL PARA PEGAR A POSIÇÃO DO VERTICE MAIS BARATO PARA IR
            int u = -1;
            //MAIOR DISTÂNCIA IMPOSSÍVEL PQ QUALQUER COISA MENOR QUE ISSO ELE VAI SUBSTITUINDO
            int menorDistancia = INFINITO;

            for(int i = 0; i < n; i++){
                //SE O NÓ NÃO FOI VISITADO e A DISTÂNCIA É MENOR (NO INICÍO É INFINITO, OU SEJA, BASICAMENTE QUALQEUR COISA PASSA NA PRIMEIRA INTERAÇÃO)
                if (!visitado[i] && distancias[i] < menorDistancia) {
                    //ELE CAPTURA A DISTANCIA QUE ESTÁ NAQUELA LINHA PARA SER A NOVA MENOR DISTÂNCIA E INTERAR COM BASE NELA
                    menorDistancia = distancias[i];
                    //A NOVA LINHA COM ESSA CARACTERÍSTICA É O i ENCONTRADO
                    u = i;
                    //VAI INTERANDO PASSANDO POR TODAS AS COLUNAS ATÉ ACHAR A COM MENOR DISTÂNCIA
                }
            }

            if (u == -1) break; //PASSOU EM TODAS AS LINHAS E NENHUM NÓ É MENOR QUE INFINITO, SIGNIFICA QUE O NÓ QUE COMEÇAMOS É ISOLADO, GG

            visitado[u] = true;

            // Atualizar as distâncias dos vizinhos de u
            for (int v = 1; v < matriz[u].length; v++) {
                int distanciaParaVizinho = matriz[u][v];
                int idVizinho = v; // como a coluna 1 representa o bairro de ID 1, etc.

                // Encontrar o índice da linha que tem o bairro de ID v
                int indiceVizinho = -1;
                for (int i = 0; i < n; i++) {
                    if (matriz[i][0] == idVizinho) {
                        indiceVizinho = i;
                        break;
                    }
                }

                if (indiceVizinho != -1 &&
                        !visitado[indiceVizinho] &&
                        distanciaParaVizinho != INFINITO &&
                        distancias[u] + distanciaParaVizinho < distancias[indiceVizinho]) {

                    distancias[indiceVizinho] = distancias[u] + distanciaParaVizinho;
                    anteriores[indiceVizinho] = u;
                }
            }
        }

        // Reconstruir o caminho
        List<Integer> caminho = new ArrayList<>();
        int atual = indiceDestino;
        while (atual != -1) {
            caminho.add(0, matriz[atual][0]); // Adiciona o ID do bairro no início da lista
            atual = anteriores[atual];
        }

        if (distancias[indiceDestino] == INFINITO) {
            return "Não há caminho entre os bairros selecionados.";
        }

        // Construir string de saída
        StringBuilder resultado = new StringBuilder();
        resultado.append("Menor caminho de ").append(origemId)
                .append(" para ").append(destinoId).append(": ");
        for (int i = 0; i < caminho.size(); i++) {
            resultado.append(caminho.get(i));
            if (i != caminho.size() - 1) {
                resultado.append(" -> ");
            }
        }
        resultado.append("\nDistância total: ").append(distancias[indiceDestino]).append(" metros");

        return resultado.toString();

    }
}