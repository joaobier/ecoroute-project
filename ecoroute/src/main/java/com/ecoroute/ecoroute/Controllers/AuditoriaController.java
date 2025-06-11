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

    @PutMapping
    public Auditoria editarAuditoria(@RequestBody Auditoria auditoria){
        return auditoriaService.editar(auditoria);
    }

    @DeleteMapping("/{id}")
    public void deletarAuditoria(@PathVariable int id){auditoriaService.deletar(id);}

}