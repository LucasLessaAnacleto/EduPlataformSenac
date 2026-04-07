package com.senac.ApiEduPlataformSenac.controllers;

import com.senac.ApiEduPlataformSenac.model.dto.LoginRequest;
import com.senac.ApiEduPlataformSenac.model.dto.LoginResponse;
import com.senac.ApiEduPlataformSenac.model.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação controller", description = "Responsavel por autenticar os usuários!")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping("/login")
    @Operation(summary = "Fazer login", description = "Autenticar o usuário retornando o token de acesso ou 401 se erro na autenticação")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){


        if(loginRequest.email().equals("lucas@gmail.com") &&  loginRequest.senha().equals("lucas123")){
            return ResponseEntity.ok(new LoginResponse("tokendolucas123"));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}