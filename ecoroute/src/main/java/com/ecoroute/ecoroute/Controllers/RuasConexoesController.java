package com.ecoroute.ecoroute.Controllers;

import com.ecoroute.ecoroute.Model.RuasConexoes;
import com.ecoroute.ecoroute.Services.BairroService;
import com.ecoroute.ecoroute.Services.RuasConexoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ruasConexoes")
public class RuasConexoesController {

    private final RuasConexoesService ruasConexoesService;
    @Autowired
    public RuasConexoesController(RuasConexoesService ruasConexoesService){
        this.ruasConexoesService = ruasConexoesService;
    }

    @GetMapping
    public List<RuasConexoes> listar(){
        return ruasConexoesService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RuasConexoes> buscarPorId(@PathVariable int id) {
        Optional<RuasConexoes> ruasConexoes = ruasConexoesService.buscarPorId(id);

        return ruasConexoes
                .map(ResponseEntity::ok) //tudo certo entrega o dado
                .orElseGet(() -> ResponseEntity.notFound().build()); //não achou nada
    }

    @PostMapping
    public void criarRuasConexoes(@RequestBody RuasConexoes ruasConexoes){
        ruasConexoesService.salvar(ruasConexoes);
    }

    @GetMapping("/grafo")
    public int[][] grafo(){
        return ruasConexoesService.grafo();
    }


}