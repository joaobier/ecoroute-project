package com.ecoroute.ecoroute.Controllers;

import com.ecoroute.ecoroute.Model.Residuo;
import com.ecoroute.ecoroute.Model.Rota;
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

    @PostMapping("/{idOrigem}/{idDestino}/{distancia}")
    public ResponseEntity<String> criarRuaConexaoBairros(@PathVariable int idOrigem, @PathVariable int idDestino, @PathVariable int distancia){
        return ruasConexoesService.salvarComBairros(idOrigem,idDestino,distancia);
    }

    @GetMapping("/grafo")
    public int[][] grafo(){
        return ruasConexoesService.grafo();
    }

    @GetMapping("/{idOrigem}/{idDestino}")
    public String acharMelhorCaminho(@PathVariable int idOrigem, @PathVariable int idDestino){
        return ruasConexoesService.melhorCaminho(idOrigem, idDestino);
    }

    @GetMapping("/rota/{idOrigem}/{idDestino}")
    public Rota acharMelhorRota(@PathVariable int idOrigem, @PathVariable int idDestino){
        return ruasConexoesService.melhorRota(idOrigem,idDestino);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        Optional<RuasConexoes> existe = ruasConexoesService.buscarPorId(id);
        if (existe.isPresent()) {
            if(ruasConexoesService.deletar(id)){
                return ResponseEntity.noContent().build();
            } else return ResponseEntity.notFound().build();   // 404 Not Found
        } else {
            return ResponseEntity.notFound().build();   // 404 Not Found
        }
    }

}