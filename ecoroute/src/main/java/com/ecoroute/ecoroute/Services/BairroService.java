package com.ecoroute.ecoroute.Services;

import com.ecoroute.ecoroute.Model.Bairro;
import com.ecoroute.ecoroute.Model.PontosDeColeta;
import com.ecoroute.ecoroute.Model.Residuo;
import com.ecoroute.ecoroute.Repositories.BairroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BairroService {

    private final BairroRepository bairroRepository;

    @Autowired
    public BairroService(BairroRepository bairroRepository) {
        this.bairroRepository = bairroRepository;
    }

    public List<Bairro> listarTodos() {
        return bairroRepository.findAll();
    }

    public Optional<Bairro> buscarPorId(int id){
        return bairroRepository.findById(id);
    }

    public Optional<Bairro> buscarPorNome(String nome){return bairroRepository.findByNome(nome);}

    public Bairro adicionarPontoColeta(int id ,PontosDeColeta ponto){
        Bairro bairro = bairroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bairro não encontrado"));

        //ponto.setBairro(bairro); //não tem pq?
        bairro.getPontosDeColeta().add(ponto);

        return bairroRepository.save(bairro);
    }

    public Bairro salvar(Bairro bairro) {
        return bairroRepository.save(bairro);
    }

    public Bairro editar(Bairro bairro){return bairroRepository.save(bairro);}

    public void deletar(int id) {bairroRepository.deleteById(id);}

    public int totalDeBairros(){return bairroRepository.contarTotalDeBairros();}

    //VOLTA TODOS OS RESIDUOS QUE AQUELE BAIRRO ACEITA, PEGANDO DE TODOS OS PONTOS DE COLETA
    public List<String> listarTiposDeResiduosDoBairro(int bairroId) {
        Bairro bairro = bairroRepository.findById(bairroId)
                .orElseThrow(() -> new RuntimeException("Bairro não encontrado"));

        return bairro.getPontosDeColeta().stream()
                .flatMap(p -> p.getResiduosAceitos().stream())
                .map(Residuo::getTipo)
                .distinct()//não deixa duplicados
                .toList();
    }


    //Apenas para usar no grafo NÃO MEXER POR FAVOR
    public List<Integer> obterTodosIdsOrdenados() {
        return bairroRepository.findAll()
                .stream()
                .map(Bairro::getId)
                .sorted()
                .collect(Collectors.toList());
    }


}