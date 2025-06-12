package com.ecoroute.ecoroute.Services;

import com.ecoroute.ecoroute.Model.Bairro;
import com.ecoroute.ecoroute.Model.RuasConexoes;
import com.ecoroute.ecoroute.Repositories.BairroRepository;
import com.ecoroute.ecoroute.Repositories.RuasConexoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        int[][] matriz = new int[totalBairros][totalBairros];

        int INFINITO = Integer.MAX_VALUE;

        //é uma matriz de adjacencia, lembrar aula luiz mario
        for (int i = 0; i < totalBairros; i++) {
            for (int j = 0; j < totalBairros; j++) {
                if (i == j) {
                    matriz[i][j] = 0; //a diagonal principal é sempre 0, pois sua distancia para você mesmo é sempre 0
                } else {
                    matriz[i][j] = INFINITO; //qualquer coisa fora da diagonal principal está recebendo o valor máximo possível dentro de um inteiro
                }
            }
        }

        // Preenche com as conexões do banco
        List<RuasConexoes> conexoes = ruasConexoesRepository.findAll();

        for (RuasConexoes rc : conexoes) {
            int origem = rc.getBairroOrigem().getId() - 1;  // IDs de 1 em diante
            int destino = rc.getBairroDestino().getId() - 1;
            int distancia = rc.getDistancia();

            matriz[origem][destino] = distancia;
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
}