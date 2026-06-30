package com.senac.ApiEduPlataformSenac.presentation;

import com.senac.ApiEduPlataformSenac.application.services.TokenService;
import com.senac.ApiEduPlataformSenac.application.services.UsuarioService;
import com.senac.ApiEduPlataformSenac.application.DTO.LoginRequest;
import com.senac.ApiEduPlataformSenac.application.DTO.LoginResponse;
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
@Tag(description = "Serviço responsavel por controlar a autenticação de usuarios e sessão!",name = "Serviço autenticação")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    @Operation(description = "Autentica usuario e senha!",summary = "Login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){


        if(usuarioService.validaUsuarioSenha(loginRequest)){

            String token = tokenService.gerarToken(loginRequest.email().toLowerCase());

            return ResponseEntity.ok(new LoginResponse(token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}