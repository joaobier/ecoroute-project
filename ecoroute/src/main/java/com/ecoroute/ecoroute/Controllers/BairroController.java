package com.ecoroute.ecoroute.Controllers;

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

    @PostMapping
    public Bairro criarBairro(@RequestBody Bairro bairro){
        return bairroService.salvar(bairro);
    }

}