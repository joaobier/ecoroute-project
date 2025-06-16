package com.ecoroute.ecoroute.Controllers;

import com.ecoroute.ecoroute.Model.Auditoria;
import com.ecoroute.ecoroute.Model.PontosDeColeta;
import com.ecoroute.ecoroute.Model.Residuo;
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

    //ADICIONA UM ÚNICO RESIDUO AO PONTO DE COLETA
    @PostMapping("/{pontoId}/residuos")
    public ResponseEntity<PontosDeColeta> adicionarResiduoAoPonto(@PathVariable int pontoId, @RequestBody Residuo residuo) {
        PontosDeColeta pontoAtualizado = pontosDeColetaService.adicionarResiduoAoPonto(pontoId, residuo.getId());
        return ResponseEntity.ok(pontoAtualizado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PontosDeColeta> editarPontosDeColeta(@PathVariable int id, @RequestBody PontosDeColeta pontosDeColeta){
        Optional<PontosDeColeta> existe = pontosDeColetaService.buscarPorId(id);
        if(existe.isPresent()){
            pontosDeColeta.setId(id); //ATRIBUI O ID AQUI
            PontosDeColeta atualizado = pontosDeColetaService.editar(pontosDeColeta);
            return ResponseEntity.ok(atualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPontosDeColeta(@PathVariable int id){
        Optional<PontosDeColeta> existe = pontosDeColetaService.buscarPorId(id);
        if (existe.isPresent()) {
            pontosDeColetaService.deletar(id);
            return ResponseEntity.noContent().build();  // 204 No Content
        } else {
            return ResponseEntity.notFound().build();   // 404 Not Found
        }
    }

}