package com.ecoroute.ecoroute.Controllers;

import com.ecoroute.ecoroute.Model.Auditoria;
import com.ecoroute.ecoroute.Model.Caminhao;
import com.ecoroute.ecoroute.Services.CaminhaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/caminhoes")
public class CaminhaoController {

    private final CaminhaoService caminhaoService;

    @Autowired
    public CaminhaoController(CaminhaoService caminhaoService){
        this.caminhaoService = caminhaoService;
    }

    @GetMapping
    public List<Caminhao> listarCaminhoes(){
        return caminhaoService.listarCaminhoes();
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<Caminhao> buscarPorPlaca(@PathVariable String placa){

        Optional<Caminhao> caminhao = caminhaoService.buscarPorPlaca(placa);

        return caminhao
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public Caminhao criarCaminhao(@RequestBody Caminhao caminhao){return caminhaoService.salvar(caminhao);}

    @PutMapping("/{id}")
    public ResponseEntity<Caminhao> editarCaminhao(@PathVariable int id, @RequestBody Caminhao caminhao){
        Optional<Caminhao> existe = caminhaoService.buscarPorId(id);
        if(existe.isPresent()){
            caminhao.setId(id); //ATRIBUI O ID AQUI
            Caminhao atualizado = caminhaoService.editar(caminhao);
            return ResponseEntity.ok(atualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCaminhao(@PathVariable int id){
        Optional<Caminhao> existe = caminhaoService.buscarPorId(id);
        if (existe.isPresent()) {
            caminhaoService.deletar(id);
            return ResponseEntity.noContent().build();  // 204 No Content
        } else {
            return ResponseEntity.notFound().build();   // 404 Not Found
        }
    }

}