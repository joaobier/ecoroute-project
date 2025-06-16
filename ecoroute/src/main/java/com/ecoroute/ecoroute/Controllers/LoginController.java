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

    @PostMapping
    public Boolean login(
            @RequestParam String email,
            @RequestParam String senha) {

        boolean autorizado = loginService.autenticar(email, senha);

        if (autorizado) {
            return true;
        } else {
            return false;
        }
    }
}