package com.ecoroute.ecoroute.Controllers;

import com.ecoroute.ecoroute.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * Endpoint para autenticação de usuários.
     *
     * @param email E-mail do usuário
     * @param senha Senha do usuário
     * @return 200 OK se sucesso, 401 Unauthorized se falha
     */
    @PostMapping
    public ResponseEntity<String> login(
            @RequestParam String email,
            @RequestParam String senha) {

        boolean autorizado = loginService.autenticar(email, senha);

        if (autorizado) {
            return ResponseEntity.ok("Login bem-sucedido!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou senha inválidos.");
        }
    }
}