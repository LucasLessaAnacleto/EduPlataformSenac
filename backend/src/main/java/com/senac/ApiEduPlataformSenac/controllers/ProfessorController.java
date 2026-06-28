package com.senac.ApiEduPlataformSenac.controllers;

import com.senac.ApiEduPlataformSenac.model.dto.ProfessorRequest;
import com.senac.ApiEduPlataformSenac.model.dto.ProfessorResponse;
import com.senac.ApiEduPlataformSenac.model.entities.Professor;
import com.senac.ApiEduPlataformSenac.model.repository.ProfessorRepository;
import com.senac.ApiEduPlataformSenac.services.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professores")
@Tag(name = "Professor controller", description = "Responsavel por gerenciar os professores!")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    @Operation(summary = "Listar todos", description = "Listar todos os professores!")
    public ResponseEntity<List<ProfessorResponse>> listarTodos() {
        return ResponseEntity.ok(professorService.listarTodos());
    }

    @GetMapping("/professorlogado")
    @Operation(summary = "Consulta professor logado", description = "Busca professor vinculado ao usuário autenticado")
    public ResponseEntity<ProfessorResponse> buscarProfessorLogado(Authentication authentication) {
        return ResponseEntity.ok(professorService.buscarProfessorLogado(authentication));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta de professor por ID", description = "Consultar um professor por ID")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(professorService.buscarPorId(id));
    }

    @PutMapping("/professorlogado")
    @Operation(summary = "Atualizar professor logado", description = "Atualiza dados do professor autenticado")
    public ResponseEntity<?> atualizarProfessorLogado(Authentication authentication, @RequestBody ProfessorRequest professor) {
        return ResponseEntity.ok(professorService.atualizarProfessorLogado(authentication, professor));
    }
}