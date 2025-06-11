package com.ecoroute.ecoroute.Controllers;

import com.ecoroute.ecoroute.Model.Rota;
import com.ecoroute.ecoroute.Services.RotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rota")
public class RotaController {

    private final RotaService rotaService;

    @Autowired
    public RotaController(RotaService rotaService){
        this.rotaService = rotaService;
    }

    @GetMapping
    public List<Rota> listar(){
        return rotaService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rota> buscarPorId(@PathVariable int id) {
        Optional<Rota> rota = rotaService.buscarPorId(id);

        return rota
                .map(ResponseEntity::ok) //tudo certo entrega o dado
                .orElseGet(() -> ResponseEntity.notFound().build()); //não achou nada
    }

    @PostMapping
    public Rota criarRota(@RequestBody Rota rota){
        return rotaService.salvar(rota);
    }

    @PutMapping
    public Rota editarRota(@RequestBody Rota rota){
        return rotaService.salvar(rota);
    }

    @DeleteMapping("/{id}")
    public void deletarRota(@PathVariable int id){rotaService.deletar(id);}

}