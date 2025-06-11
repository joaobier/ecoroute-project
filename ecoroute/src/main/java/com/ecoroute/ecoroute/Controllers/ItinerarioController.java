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

    private final ItinerarioService itinerarioService;

    @Autowired
    public ItinerarioController(ItinerarioService ItinerarioService){
        this.itinerarioService = ItinerarioService;
    }

    @GetMapping
    public List<Itinerario> listar(){
        return itinerarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Itinerario> buscarPorId(@PathVariable int id) {
        Optional<Itinerario> Itinerario = itinerarioService.buscarPorId(id);

        return Itinerario
                .map(ResponseEntity::ok) //tudo certo entrega o dado
                .orElseGet(() -> ResponseEntity.notFound().build()); //não achou nada
    }

    @PostMapping
    public Itinerario criarItinerario(@RequestBody Itinerario Itinerario){
        return itinerarioService.salvar(Itinerario);
    }

    @PutMapping
    public Itinerario editarItinerario(@RequestBody Itinerario Itinerario){
        return itinerarioService.salvar(Itinerario);
    }

    @DeleteMapping("/{id}")
    public void deletarItinerario(@PathVariable int id){itinerarioService.deletar(id);}

}