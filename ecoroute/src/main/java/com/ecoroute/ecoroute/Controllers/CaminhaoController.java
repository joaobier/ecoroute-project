package com.ecoroute.ecoroute.Controllers;

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

    @PutMapping
    public Caminhao editarCaminhao(@RequestBody Caminhao caminhao){return caminhaoService.salvar(caminhao);}

    @DeleteMapping("/{id}")
    public void deletarCaminhao(@PathVariable int id){caminhaoService.deletar(id);}

}
