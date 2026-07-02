package com.senac.ApiEduPlataformSenac.presentation;

import com.senac.ApiEduPlataformSenac.application.services.ProfessorService;
import com.senac.ApiEduPlataformSenac.domain.repository.ProfessorRepository;
import com.senac.ApiEduPlataformSenac.application.DTO.ProfessorRequest;
import com.senac.ApiEduPlataformSenac.application.DTO.ProfessorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professores")
@Tag(name = "Professor controller", description = "Responsavel por gerenciar os professores!")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    @Operation(summary = "Listar todos", description = "Listar todos os professores!")
    public ResponseEntity<List<ProfessorResponse>> listarTodos() {
        try{
            return ResponseEntity.ok(professorService.listarTodos());
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/professorlogado")
    @Operation(summary = "Consulta professor logado", description = "Busca professor vinculado ao usuário autenticado")
    public ResponseEntity<ProfessorResponse> buscarProfessorLogado(Authentication authentication) {
        try{
            return ResponseEntity.ok(professorService.buscarProfessorLogado(authentication));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta de professor por ID", description = "Consultar um professor por ID")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(professorService.buscarPorId(id));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/professorlogado")
    @Operation(summary = "Atualizar professor logado", description = "Atualiza dados do professor autenticado")
    public ResponseEntity<?> atualizarProfessorLogado(Authentication authentication, @RequestBody ProfessorRequest professor) {
        try{
            return ResponseEntity.ok(professorService.atualizarProfessorLogado(authentication, professor));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<ProfessorResponse> buscarProfessorPorUsuarioId(
            @PathVariable Long usuarioId,
            Authentication authentication
    ) {
        try {
            ProfessorResponse professorResponse = professorService.buscarProfessorPorUsuarioId(usuarioId, authentication);

            return ResponseEntity.ok(professorResponse);

        } catch (Exception e) {
            if (e.getMessage().equals("SEM_PERMISSAO")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            if (e.getMessage().equals("PROFESSOR_NAO_ENCONTRADO")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/usuario/{usuarioId}")
    public ResponseEntity<ProfessorResponse> atualizarProfessorPorUsuarioId(
            @PathVariable Long usuarioId,
            @RequestBody ProfessorRequest professorRequest,
            Authentication authentication
    ) {
        try {
            ProfessorResponse professorResponse = professorService.atualizarProfessorPorUsuarioId(
                    usuarioId,
                    professorRequest,
                    authentication
            );

            return ResponseEntity.ok(professorResponse);

        } catch (Exception e) {
            if (e.getMessage().equals("SEM_PERMISSAO")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            if (e.getMessage().equals("PROFESSOR_NAO_ENCONTRADO")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}