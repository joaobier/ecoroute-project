package com.ecoroute.ecoroute.Controllers;

import com.ecoroute.ecoroute.Model.Residuo;
import com.ecoroute.ecoroute.Services.ResiduoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping
    public Residuo editarResiduo(@RequestBody Residuo residuo){return residuoService.editar(residuo);}

    @DeleteMapping
    public void deletarResiduo(@PathVariable int id){residuoService.deletar(id);}

}
