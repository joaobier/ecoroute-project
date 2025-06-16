package com.ecoroute.ecoroute.Controllers;

import com.ecoroute.ecoroute.Model.Rota;
import com.ecoroute.ecoroute.Services.RotaService;
import com.ecoroute.ecoroute.Services.RuasConexoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rota")
public class RotaController {

    private final RotaService rotaService;
    private final RuasConexoesService ruasConexoesService;

    @Autowired
    public RotaController(RotaService rotaService, RuasConexoesService ruasConexoesService){
        this.rotaService = rotaService;
        this.ruasConexoesService = ruasConexoesService;
    }

    @GetMapping
    public List<Rota> listar(){
        return rotaService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rota> buscarPorId(@PathVariable int id) {
        Optional<Rota> rota = rotaService.buscarPorId(id);

        return rota
                .map(ResponseEntity::ok) //tudo certo entrega o dado
                .orElseGet(() -> ResponseEntity.notFound().build()); //não achou nada
    }

    @GetMapping("/melhor/{idOrigem}/{idDestino}")
    public Rota acharMelhorRota(@PathVariable int idOrigem, @PathVariable int idDestino){
        return ruasConexoesService.melhorRota(idOrigem,idDestino);
    }

    @PostMapping("/melhor/{idOrigem}/{idDestino}")
    public Rota salvarMelhorRota(@PathVariable int idOrigem, @PathVariable int idDestino){
        return rotaService.salvar(ruasConexoesService.melhorRota(idOrigem,idDestino));
    }

    @PostMapping
    public Rota criarRota(@RequestBody Rota rota){
        return rotaService.salvar(rota);
    }

    /*
    @PutMapping("/{id}")
    public Rota editarRota(@PathVariable int id, @RequestBody Rota rota){
        return rotaService.salvar(rota);
    }


    @DeleteMapping("/{id}")
    public void deletarRota(@PathVariable int id){rotaService.deletar(id);}
    */
}