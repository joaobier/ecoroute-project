package com.ecoroute.ecoroute.Services;

import com.ecoroute.ecoroute.Model.Itinerario;
import com.ecoroute.ecoroute.Repositories.ItinerarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItinerarioService {

    private final ItinerarioRepository itinerarioRepository;

    @Autowired
    public ItinerarioService(ItinerarioRepository itinerarioRepository) {
        this.itinerarioRepository = itinerarioRepository;
    }

    public List<Itinerario> listarTodos() {
        return itinerarioRepository.findAll();
    }

    public Optional<Itinerario> buscarPorId(int id){
        return itinerarioRepository.findById(id);
    }

    public Itinerario salvar(Itinerario itinerario) {
        return itinerarioRepository.save(itinerario);
    }

    public Itinerario editar(Itinerario itinerario) {
        return itinerarioRepository.save(itinerario);
    }

    public void deletar(int id) {
        itinerarioRepository.deleteById(id);
    }

}