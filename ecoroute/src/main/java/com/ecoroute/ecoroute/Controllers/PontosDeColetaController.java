package com.ecoroute.ecoroute.Controllers;

import com.ecoroute.ecoroute.Model.PontosDeColeta;
import com.ecoroute.ecoroute.Services.PontosDeColetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pontosDeColeta")
public class PontosDeColetaController {

    private final PontosDeColetaService pontosDeColetaService;

    @Autowired
    public PontosDeColetaController(PontosDeColetaService pontosDeColetaService){
        this.pontosDeColetaService = pontosDeColetaService;
    }

    @GetMapping
    public List<PontosDeColeta> listar(){
        return pontosDeColetaService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PontosDeColeta> buscarPorId(@PathVariable int id) {
        Optional<PontosDeColeta> pontosDeColeta = pontosDeColetaService.buscarPorId(id);

        return pontosDeColeta
                .map(ResponseEntity::ok) //tudo certo entrega o dado
                .orElseGet(() -> ResponseEntity.notFound().build()); //não achou nada
    }

    @PostMapping
    public PontosDeColeta criarPontosDeColeta(@RequestBody PontosDeColeta pontosDeColeta){
        return pontosDeColetaService.salvar(pontosDeColeta);
    }

    @PutMapping
    public PontosDeColeta editarPontosDeColeta(@RequestBody PontosDeColeta pontosDeColeta){
        return pontosDeColetaService.editar(pontosDeColeta);
    }

    @DeleteMapping("/{id}")
    public void deletarPontosDeColeta(@PathVariable int id){
        pontosDeColetaService.deletar(id);
    }

}