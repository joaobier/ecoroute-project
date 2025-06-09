package com.ecoroute.ecoroute.Services;

import com.ecoroute.ecoroute.Model.RuasConexoes;
import com.ecoroute.ecoroute.Repositories.RuasConexoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuasConexoesService {

    private final RuasConexoesRepository ruasConexoesRepository;

    @Autowired
    public RuasConexoesService(RuasConexoesRepository ruasConexoesRepository) {
        this.ruasConexoesRepository = ruasConexoesRepository;
    }

    public List<RuasConexoes> listarTodos() {
        return ruasConexoesRepository.findAll();
    }

    public Optional<RuasConexoes> buscarPorId(int id){
        return ruasConexoesRepository.findById(id);
    }

    public RuasConexoes salvar(RuasConexoes ruasConexoes) {
        return ruasConexoesRepository.save(ruasConexoes);
    }

    public void deletar(int id) {
        ruasConexoesRepository.deleteById(id);
    }

}