package com.senac.ApiEduPlataformSenac.presentation;

import com.senac.ApiEduPlataformSenac.application.services.CursoService;
import com.senac.ApiEduPlataformSenac.application.DTO.CursoRequest;
import com.senac.ApiEduPlataformSenac.application.DTO.CursoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/cursos")
@Tag(name = "Cursos controller", description = "Responsável por gerenciar os cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    @Operation(summary = "Listar cursos", description = "Lista cursos do professor logado")
    public ResponseEntity<List<CursoResponse>> listarTodos(Authentication authentication) {
        try {
            return ResponseEntity.ok(cursoService.listarTodos(authentication));

        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar curso por ID", description = "Busca um curso do professor logado")
    public ResponseEntity<CursoResponse> buscarPorId(
            @PathVariable Long id,
            Authentication authentication
    ) {
        try {
            return ResponseEntity.ok(cursoService.buscarPorId(id, authentication));

        } catch (Exception e) {
            if (e.getMessage().equals("CURSO_NAO_ENCONTRADO")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if (e.getMessage().equals("SEM_PERMISSAO")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping
    @Operation(summary = "Criar curso", description = "Cria um curso vinculado ao professor logado")
    public ResponseEntity<CursoResponse> salvar(
            @RequestBody CursoRequest cursoRequest,
            Authentication authentication
    ) {
        try {
            CursoResponse cursoSalvo = cursoService.salvar(cursoRequest, authentication);

            return ResponseEntity.ok(cursoSalvo);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar curso", description = "Atualiza um curso do professor logado")
    public ResponseEntity<CursoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody CursoRequest cursoRequest,
            Authentication authentication
    ) {
        try {
            return ResponseEntity.ok(cursoService.atualizar(id, cursoRequest, authentication));

        } catch (Exception e) {
            if (e.getMessage().equals("CURSO_NAO_ENCONTRADO")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if (e.getMessage().equals("SEM_PERMISSAO")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar curso", description = "Deleta um curso do professor logado")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id,
            Authentication authentication
    ) {
        try {
            cursoService.deletar(id, authentication);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            if (e.getMessage().equals("CURSO_NAO_ENCONTRADO")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if (e.getMessage().equals("SEM_PERMISSAO")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}