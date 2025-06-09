package com.ecoroute.ecoroute.Services;

import com.ecoroute.ecoroute.Model.PontosDeColeta;
import com.ecoroute.ecoroute.Repositories.PontosDeColetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PontosDeColetaService {

    private final PontosDeColetaRepository pontosDeColetaRepository;

    @Autowired
    public PontosDeColetaService(PontosDeColetaRepository pontosDeColetaRepository) {
        this.pontosDeColetaRepository = pontosDeColetaRepository;
    }

    public List<PontosDeColeta> listarTodos() {
        return pontosDeColetaRepository.findAll();
    }

    public Optional<PontosDeColeta> buscarPorId(int id){
        return pontosDeColetaRepository.findById(id);
    }

    public PontosDeColeta salvar(PontosDeColeta pontosDeColeta) {
        return pontosDeColetaRepository.save(pontosDeColeta);
    }

    public void deletar(int id) {
        pontosDeColetaRepository.deleteById(id);
    }

}