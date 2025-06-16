package com.ecoroute.ecoroute.Controllers;

import com.ecoroute.ecoroute.Model.Auditoria;
import com.ecoroute.ecoroute.Model.Usuario;
import com.ecoroute.ecoroute.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> listarUsuarios(){
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/login/{login}")
    public ResponseEntity<Usuario> buscarPorLogin(@PathVariable String email) {
        Optional<Usuario> usuario = usuarioService.buscarPorEmail(email);

        return usuario
                .map(ResponseEntity::ok) //tudo certo entrega o dado
                .orElseGet(() -> ResponseEntity.notFound().build()); //não achou nada
    }

    @PostMapping
    public ResponseEntity<Void> criarUsuario(@RequestBody Usuario usuario){
        return usuarioService.salvar(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editarUsuario(@PathVariable int id, @RequestBody Usuario usuario){
        Optional<Usuario> existe = usuarioService.buscarPorId(id);
        if(existe.isPresent()){
            usuario.setId(id); //ATRIBUI O ID AQUI
            usuarioService.editar(usuario);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable int id){
        Optional<Usuario> existe = usuarioService.buscarPorId(id);
        if (existe.isPresent()) {
            usuarioService.deletar(id);
            return ResponseEntity.noContent().build();  // 204 No Content
        } else {
            return ResponseEntity.notFound().build();   // 404 Not Found
        }
    }

}