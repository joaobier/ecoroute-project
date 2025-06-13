package com.ecoroute.ecoroute.Services;

import com.ecoroute.ecoroute.Model.Usuario;
import com.ecoroute.ecoroute.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Verifica se o login é válido com base no e-mail e senha fornecidos.
     *
     * @param email E-mail informado pelo usuário
     * @param senha Senha informada pelo usuário (texto puro)
     * @return true se a autenticação for bem-sucedida, false caso contrário
     */
    public boolean autenticar(String email, String senha) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isEmpty()) {
            return false; // Usuário não encontrado
        }

        Usuario usuario = usuarioOptional.get();

        // Verifica se a senha informada corresponde ao hash armazenado
        return passwordEncoder.matches(senha, usuario.getSenha());
    }
}
