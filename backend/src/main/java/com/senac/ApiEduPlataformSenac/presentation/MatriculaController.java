package com.senac.ApiEduPlataformSenac.presentation;

import com.senac.ApiEduPlataformSenac.application.services.MatriculaService;
import com.senac.ApiEduPlataformSenac.application.DTO.MatriculaRequest;
import com.senac.ApiEduPlataformSenac.application.DTO.MatriculaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping
@Tag(name = "Matricula controller", description = "Responsável por gerenciar as matrículas")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @GetMapping("/cursos/{cursoId}/matriculas")
    @Operation(summary = "Listar matrículas", description = "Lista alunos do curso somente para o professor dono")
    public ResponseEntity<List<MatriculaResponse>> listarPorCurso(
            @PathVariable Long cursoId,
            Authentication authentication
    ) {
        try {
            return ResponseEntity.ok(matriculaService.listarPorCurso(cursoId, authentication));

        } catch (Exception e) {
            if ("CURSO_NAO_ENCONTRADO".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if ("SEM_PERMISSAO".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/matriculas")
    @Operation(summary = "Criar matrícula", description = "Cadastra aluno em um curso validando professor dono")
    public ResponseEntity<?> salvar(
            @RequestBody MatriculaRequest request,
            Authentication authentication
    ) {
        try {
            return ResponseEntity.ok(matriculaService.salvar(request, authentication));

        } catch (Exception e) {
            if ("EMAIL_NAO_ENVIADO".equals(e.getMessage())) {
                return ResponseEntity.badRequest().body("Não foi possível enviar o e-mail de boas vindas.");
            }

            if ("CURSO_NAO_ENCONTRADO".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if ("SEM_PERMISSAO".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/matriculas/{id}")
    @Operation(summary = "Atualizar matrícula", description = "Atualiza matrícula validando professor dono")
    public ResponseEntity<MatriculaResponse> atualizar(
            @PathVariable Long id,
            @RequestBody MatriculaRequest request,
            Authentication authentication
    ) {
        try {
            return ResponseEntity.ok(matriculaService.atualizar(id, request, authentication));

        } catch (Exception e) {
            if ("MATRICULA_NAO_ENCONTRADA".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if ("CURSO_NAO_ENCONTRADO".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if ("SEM_PERMISSAO".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/matriculas/{id}")
    @Operation(summary = "Deletar matrícula", description = "Remove aluno do curso somente para o professor dono")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id,
            Authentication authentication
    ) {
        try {
            matriculaService.deletar(id, authentication);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            if ("MATRICULA_NAO_ENCONTRADA".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if ("CURSO_NAO_ENCONTRADO".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if ("SEM_PERMISSAO".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}