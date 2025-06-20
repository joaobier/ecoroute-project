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
    public void criarItinerario(@RequestBody ItinerarioDTO itinerarioDTO){

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

         itinerarioService.salvar(itinerario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Itinerario> editarItinerario(@PathVariable int id, @RequestBody Itinerario itinerario){
        Optional<Itinerario> existe = itinerarioService.buscarPorId(id);
        if(existe.isPresent()){
            itinerario.setId(id); //ATRIBUI O ID AQUI
            Itinerario atualizado = itinerarioService.editar(itinerario);
            return ResponseEntity.ok(atualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItinerario(@PathVariable int id){
        Optional<Itinerario> existe = itinerarioService.buscarPorId(id);
        if (existe.isPresent()) {
            itinerarioService.deletar(id);
            return ResponseEntity.noContent().build();  // 204 No Content
        } else {
            return ResponseEntity.notFound().build();   // 404 Not Found
        }
    }

}