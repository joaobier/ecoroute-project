package com.ecoroute.ecoroute.Controllers;

import com.ecoroute.ecoroute.DTOs.ItinerarioDTO;
import com.ecoroute.ecoroute.Model.*;
import com.ecoroute.ecoroute.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    private final RuasConexoesService ruasConexoesService;
    private final ResiduoService residuoService;

    @Autowired
    public ItinerarioController(ItinerarioService ItinerarioService, UsuarioService usuarioService, CaminhaoService caminhaoService, RotaService rotaService, RuasConexoesService ruasConexoesService, ResiduoService residuoService){
        this.itinerarioService = ItinerarioService;
        this.usuarioService = usuarioService;
        this.caminhaoService = caminhaoService;
        this.rotaService = rotaService;
        this.ruasConexoesService = ruasConexoesService;
        this.residuoService = residuoService;
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

    //@Transactional //pq não post? pq tem várias requisições ao banco e capturas de dados para isso ser feito, com transactional ele só faz o commit de tudo se tudo der certo
    @PostMapping
    public Itinerario criarItinerario(@RequestBody ItinerarioDTO itinerarioDTO){

        Usuario usuario = usuarioService.buscarPorId(itinerarioDTO.getResponsavelId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario de ID:"+itinerarioDTO.getResponsavelId()+ " não encontrado"));

        Caminhao caminhao = caminhaoService.buscarPorId(itinerarioDTO.getCaminhaoId())
                .orElseThrow(() -> new ResourceNotFoundException("Caminhão de ID:" + itinerarioDTO.getCaminhaoId() + " não encontrado"));

        Rota rota = ruasConexoesService.melhorRota(itinerarioDTO.getOrigemId(), itinerarioDTO.getDestinoId());

        Residuo residuo = residuoService.buscarPorId(itinerarioDTO.getResiduoId())
                .orElseThrow(() -> new ResourceNotFoundException("Residuo de ID:"+itinerarioDTO.getResiduoId()+ " não encontrado"));

        String dataExecucao = itinerarioDTO.getDataExecucao();

        Itinerario itinerario = new Itinerario(usuario,caminhao,rota,dataExecucao,residuo);

        // Liga a rota de volta ao itinerario para salvar tudo junto, o JPA faz
        rota.setItinerario(itinerario);

        return itinerarioService.salvar(itinerario);
    }

    @PutMapping
    public Itinerario editarItinerario(@RequestBody Itinerario Itinerario){
        return itinerarioService.salvar(Itinerario);
    }

    @DeleteMapping("/{id}")
    public void deletarItinerario(@PathVariable int id){itinerarioService.deletar(id);}

}