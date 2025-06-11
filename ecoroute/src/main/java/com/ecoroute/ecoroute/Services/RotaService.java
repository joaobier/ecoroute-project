package com.ecoroute.ecoroute.Services;

import com.ecoroute.ecoroute.Model.Rota;
import com.ecoroute.ecoroute.Repositories.RotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RotaService {

    private final RotaRepository rotaRepository;

    @Autowired
    public RotaService(RotaRepository rotaRepository) {
        this.rotaRepository = rotaRepository;
    }

    public List<Rota> listarTodos() {
        return rotaRepository.findAll();
    }

    public Optional<Rota> buscarPorId(int id){
        return rotaRepository.findById(id);
    }

    public Rota salvar(Rota rota) {
        return rotaRepository.save(rota);
    }

    public Rota editar(Rota rota) {
        return rotaRepository.save(rota);
    }

    public void deletar(int id) {
        rotaRepository.deleteById(id);
    }

}