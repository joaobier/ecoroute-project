package com.ecoroute.ecoroute.Controllers;

import com.ecoroute.ecoroute.Model.Auditoria;
import com.ecoroute.ecoroute.Model.Residuo;
import com.ecoroute.ecoroute.Services.ResiduoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/residuo")
public class ResiduoController {

    private final ResiduoService residuoService;

    @Autowired
    public ResiduoController(ResiduoService residuoService){this.residuoService = residuoService;}

    @GetMapping
    public List<Residuo> listar(){return residuoService.listarTodos();}

    @PostMapping
    public Residuo criarResiduo(@RequestBody Residuo residuo){return residuoService.salvar(residuo);}

    @PutMapping("/{id}")
    public ResponseEntity<Residuo> editarResiduo(@PathVariable int id,@RequestBody Residuo residuo){
        Optional<Residuo> existe = residuoService.buscarPorId(id);
        if(existe.isPresent()){
            residuo.setId(id); //ATRIBUI O ID AQUI
            Residuo atualizado = residuoService.editar(residuo);
            return ResponseEntity.ok(atualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        Optional<Residuo> existe = residuoService.buscarPorId(id);
        if (existe.isPresent()) {
            residuoService.deletar(id);
            return ResponseEntity.noContent().build();  // 204 No Content
        } else {
            return ResponseEntity.notFound().build();   // 404 Not Found
        }
    }

}
