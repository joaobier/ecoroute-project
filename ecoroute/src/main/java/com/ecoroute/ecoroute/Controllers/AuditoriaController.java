package com.ecoroute.ecoroute.Controllers;

import com.ecoroute.ecoroute.Model.Auditoria;
import com.ecoroute.ecoroute.Services.AuditoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auditoria")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    @Autowired
    public AuditoriaController(AuditoriaService auditoriaService){
        this.auditoriaService = auditoriaService;
    }

    @GetMapping
    public List<Auditoria> listarUsuarios(){
        return auditoriaService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auditoria> buscarPorId(@PathVariable int id) {
        Optional<Auditoria> auditoria = auditoriaService.buscarPorId(id);

        return auditoria
                .map(ResponseEntity::ok) //tudo certo entrega o dado
                .orElseGet(()-> ResponseEntity.notFound().build()); //não achou nada
    }

    @PostMapping
    public Auditoria criarAuditoria(@RequestBody Auditoria auditoria){
        return auditoriaService.salvar(auditoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Auditoria> editarAuditoria(@PathVariable int id, @RequestBody Auditoria auditoria){
        Optional<Auditoria> existe = auditoriaService.buscarPorId(id);
        if(existe.isPresent()){
            auditoria.setId(id); //ATRIBUI O ID AQUI
            Auditoria atualizado = auditoriaService.editar(auditoria);
            return ResponseEntity.ok(atualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAuditoria(@PathVariable int id){
        Optional<Auditoria> existe = auditoriaService.buscarPorId(id);
        if (existe.isPresent()) {
            auditoriaService.deletar(id);
            return ResponseEntity.noContent().build();  // 204 No Content
        } else {
            return ResponseEntity.notFound().build();   // 404 Not Found
        }

    }

}