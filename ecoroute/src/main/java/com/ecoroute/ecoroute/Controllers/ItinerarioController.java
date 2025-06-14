package com.ecoroute.ecoroute.Controllers;

import com.ecoroute.ecoroute.DTOs.ItinerarioDTO;
import com.ecoroute.ecoroute.Model.Caminhao;
import com.ecoroute.ecoroute.Model.Itinerario;
import com.ecoroute.ecoroute.Model.Rota;
import com.ecoroute.ecoroute.Model.Usuario;
import com.ecoroute.ecoroute.Services.CaminhaoService;
import com.ecoroute.ecoroute.Services.ItinerarioService;
import com.ecoroute.ecoroute.Services.RotaService;
import com.ecoroute.ecoroute.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/itinerario")
public class ItinerarioController {

    private final ItinerarioService itinerarioService;
    private final UsuarioService usuarioService;
    private final CaminhaoService caminhaoService;
    private final RotaService rotaService;
    @Autowired
    public ItinerarioController(ItinerarioService ItinerarioService, UsuarioService usuarioService, CaminhaoService caminhaoService, RotaService rotaService){
        this.itinerarioService = ItinerarioService;
        this.usuarioService = usuarioService;
        this.caminhaoService = caminhaoService;
        this.rotaService = rotaService;
    }

    @GetMapping
    public List<Itinerario> listar(){
        return itinerarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Itinerario> buscarPorId(@PathVariable int id) {
        Optional<Itinerario> Itinerario = itinerarioService.buscarPorId(id);

        return Itinerario
                .map(ResponseEntity::ok) //tudo certo entrega o dado
                .orElseGet(() -> ResponseEntity.notFound().build()); //não achou nada
    }

    @PostMapping
    public Itinerario criarItinerario(@RequestBody ItinerarioDTO itinerarioDTO){

        Usuario usuario = usuarioService.buscarPorId(itinerarioDTO.getResponsavelId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario de ID:"+itinerarioDTO.getResponsavelId()+ " não encontrado"));

        Caminhao caminhao = caminhaoService.buscarPorId(itinerarioDTO.getResponsavelId())
                .orElseThrow(() -> new ResourceNotFoundException("Caminhão não encontrado"));

        Rota rota = rotaService.buscarPorId(itinerarioDTO.getResponsavelId())
                .orElseThrow(() -> new ResourceNotFoundException("Rota não encontrada"));

        String dataExecucao = itinerarioDTO.getDataExecucao();

        String tipoDeResiduo = itinerarioDTO.getTiposResiduo();

        Itinerario itinerario = new Itinerario(usuario,caminhao,rota,dataExecucao,tipoDeResiduo);

        return itinerarioService.salvar(itinerario);
    }

    @PutMapping
    public Itinerario editarItinerario(@RequestBody Itinerario Itinerario){
        return itinerarioService.salvar(Itinerario);
    }

    @DeleteMapping("/{id}")
    public void deletarItinerario(@PathVariable int id){itinerarioService.deletar(id);}

}