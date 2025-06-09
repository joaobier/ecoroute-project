package com.ecoroute.ecoroute.Services;

import com.ecoroute.ecoroute.Model.Auditoria;
import com.ecoroute.ecoroute.Model.Bairro;
import com.ecoroute.ecoroute.Repositories.BairroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Bairro salvar(Bairro bairro) {
        return bairroRepository.save(bairro);
    }

    public void deletar(int id) {
        bairroRepository.deleteById(id);
    }

}