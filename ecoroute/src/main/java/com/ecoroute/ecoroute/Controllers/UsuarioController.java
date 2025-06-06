package com.ecoroute.ecoroute.Controllers;

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
    public ResponseEntity<Usuario> buscarPorLogin(@PathVariable String login) {
        Optional<Usuario> usuario = usuarioService.buscarPorLogin(login);

        return usuario
                .map(ResponseEntity::ok) //tudo certo entrega o dado
                .orElseGet(() -> ResponseEntity.notFound().build()); //não achou nada
    }

    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario){
        return usuarioService.salvar(usuario);
    }

}