package com.ecoroute.ecoroute.Services;

import com.ecoroute.ecoroute.Model.Bairro;
import com.ecoroute.ecoroute.Repositories.BairroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public Bairro salvar(Bairro bairro) {
        return bairroRepository.save(bairro);
    }

    public Bairro editar(Bairro bairro){return bairroRepository.save(bairro);}

    public void deletar(int id) {bairroRepository.deleteById(id);}

    public int totalDeBairros(){return bairroRepository.contarTotalDeBairros();}

    //Apenas para usar no grafo
    public List<Integer> obterTodosIdsOrdenados() {
        return bairroRepository.findAll()
                .stream()
                .map(Bairro::getId)
                .sorted()
                .collect(Collectors.toList());
    }


}