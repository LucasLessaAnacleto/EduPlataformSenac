package com.senac.ApiEduPlataformSenac.controllers;

import com.senac.ApiEduPlataformSenac.model.dto.LoginRequest;
import com.senac.ApiEduPlataformSenac.model.dto.LoginResponse;
import com.senac.ApiEduPlataformSenac.model.dto.UsuarioResponse;
import com.senac.ApiEduPlataformSenac.model.repository.ProfessorRepository;
import com.senac.ApiEduPlataformSenac.model.repository.UsuarioRepository;
import com.senac.ApiEduPlataformSenac.services.TokenService;
import com.senac.ApiEduPlataformSenac.services.UsuarioService;
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
    private ProfessorRepository profssorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService authService;

    /*
    @PostMapping("/login")
    @Operation(summary = "Fazer login", description = "Autenticar do professor")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){

        Professor professorDb = professorRepository.findByEmail(loginRequest.email()).orElse(null);

        if (professorDb != null && professorDb.getSenha().equals(loginRequest.senha()) ) {

            Long professorId = professorDb.getId();
            String email = professorDb.getEmail();

            LoginResponse loginResponse = new LoginResponse(TokenUtil.generateToken(professorId, email),
                    new ProfessorResponse(professorId, professorDb.getNome(), email, professorDb.getBiografia())
            );

            return ResponseEntity.ok(loginResponse);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }*/

    @PostMapping("/login")
    @Operation(summary = "Fazer login", description = "Autenticar do professor")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){

        if (authService.ValidaUsuarioSenha(loginRequest)) {
            String token = tokenService.gerarToken(loginRequest.email());
            if(token == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            return ResponseEntity.ok(new LoginResponse(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}