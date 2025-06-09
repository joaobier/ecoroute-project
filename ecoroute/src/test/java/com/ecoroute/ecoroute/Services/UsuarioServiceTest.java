package com.ecoroute.ecoroute.Services;

import com.ecoroute.ecoroute.Model.Usuario;
import com.ecoroute.ecoroute.Repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvarUsuario() {
        Usuario usuario = new Usuario("João", "joao", "1234");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario salvo = usuarioService.salvar(usuario);

        assertNotNull(salvo);
        assertEquals("João", salvo.getNome());
        verify(usuarioRepository, times(1)).save(usuario);
    }


    @Test
    void testBuscarUsuarioPorLogin() {
        Usuario usuario = new Usuario("Ana", "ana123", "senha");
        usuario.setId(10);

    }

}
