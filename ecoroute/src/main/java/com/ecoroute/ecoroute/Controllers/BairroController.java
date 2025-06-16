package com.ecoroute.ecoroute.Controllers;

import com.ecoroute.ecoroute.Model.Auditoria;
import com.ecoroute.ecoroute.Model.Bairro;
import com.ecoroute.ecoroute.Services.BairroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bairro")
public class BairroController {

    private final BairroService bairroService;

    @Autowired
    public BairroController(BairroService bairroService){
        this.bairroService = bairroService;
    }

    @GetMapping
    public List<Bairro> listar(){
        return bairroService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bairro> buscarPorId(@PathVariable int id) {
        Optional<Bairro> bairro = bairroService.buscarPorId(id);

        return bairro
                .map(ResponseEntity::ok) //tudo certo entrega o dado
                .orElseGet(()-> ResponseEntity.notFound().build()); //não achou nada
    }

    @GetMapping("/{nome}")
    public ResponseEntity<Bairro> buscarPorNome(@PathVariable String nome) {
        Optional<Bairro> bairro = bairroService.buscarPorNome(nome);

        return bairro
                .map(ResponseEntity::ok) //tudo certo entrega o dado
                .orElseGet(()-> ResponseEntity.notFound().build()); //não achou nada
    }

    @GetMapping("/{bairroId}/tipos-residuos")
    public List<String> listarTiposDeResiduos(@PathVariable int bairroId) {
        return bairroService.listarTiposDeResiduosDoBairro(bairroId);
    }

    @PostMapping("/{id}")
    public Bairro criarBairro(@PathVariable int id, @RequestBody Bairro bairro){
        return bairroService.salvar(bairro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bairro> editarBairro(@PathVariable int id, @RequestBody Bairro bairro){
        Optional<Bairro> existe = bairroService.buscarPorId(id);
        if(existe.isPresent()){
            bairro.setId(id);
            Bairro atualizado = bairroService.editar(bairro);
            return ResponseEntity.ok(atualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarBairro(@PathVariable int id){
        Optional<Bairro> existe = bairroService.buscarPorId(id);
        if (existe.isPresent()) {
            bairroService.deletar(id);
            return ResponseEntity.noContent().build();  // 204 No Content
        } else {
            return ResponseEntity.notFound().build();   // 404 Not Found
        }
    }

}