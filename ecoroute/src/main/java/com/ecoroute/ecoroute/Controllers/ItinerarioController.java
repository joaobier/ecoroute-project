package com.ecoroute.ecoroute.Controllers;

import com.ecoroute.ecoroute.Model.Itinerario;
import com.ecoroute.ecoroute.Services.ItinerarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Itinerario")
public class ItinerarioController {

    private final ItinerarioService ItinerarioService;

    @Autowired
    public ItinerarioController(ItinerarioService ItinerarioService){
        this.ItinerarioService = ItinerarioService;
    }

    @GetMapping
    public List<Itinerario> listar(){
        return ItinerarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Itinerario> buscarPorId(@PathVariable int id) {
        Optional<Itinerario> Itinerario = ItinerarioService.buscarPorId(id);

        return Itinerario
                .map(ResponseEntity::ok) //tudo certo entrega o dado
                .orElseGet(() -> ResponseEntity.notFound().build()); //não achou nada
    }

    @PostMapping
    public Itinerario criarItinerario(@RequestBody Itinerario Itinerario){
        return ItinerarioService.salvar(Itinerario);
    }

}