package com.senac.ApiEduPlataformSenac.presentation;

import com.senac.ApiEduPlataformSenac.application.services.ModuloService;
import com.senac.ApiEduPlataformSenac.application.DTO.ModuloRequest;
import com.senac.ApiEduPlataformSenac.application.DTO.ModuloResponse;
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
@Tag(name = "Modulo controller", description = "Responsável por gerenciar os módulos")
public class ModuloController {

    @Autowired
    private ModuloService moduloService;

    @GetMapping("/cursos/{cursoId}/modulos")
    @Operation(summary = "Listar módulos do curso", description = "Valida professor dono e ordena por ordem")
    public ResponseEntity<List<ModuloResponse>> listarPorCurso(
            @PathVariable Long cursoId,
            Authentication authentication
    ) {
        try {
            return ResponseEntity.ok(moduloService.listarPorCurso(cursoId, authentication));

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

    @PostMapping("/modulos")
    @Operation(summary = "Criar módulo", description = "Recebe cursoId e valida professor dono")
    public ResponseEntity<Long> salvar(
            @RequestBody ModuloRequest request,
            Authentication authentication
    ) {
        try {
            return ResponseEntity.ok(moduloService.salvar(request, authentication));

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

    @PutMapping("/modulos/{id}")
    @Operation(summary = "Atualizar módulo", description = "Valida professor dono e não permite alterar curso")
    public ResponseEntity<ModuloResponse> atualizar(
            @PathVariable Long id,
            @RequestBody ModuloRequest request,
            Authentication authentication
    ) {
        try {
            return ResponseEntity.ok(moduloService.atualizar(id, request, authentication));

        } catch (Exception e) {
            if ("MODULO_NAO_ENCONTRADO".equals(e.getMessage())) {
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

    @DeleteMapping("/modulos/{id}")
    @Operation(summary = "Deletar módulo", description = "Apenas o professor dono do curso pode deletar o módulo")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id,
            Authentication authentication
    ) {
        try {
            moduloService.deletar(id, authentication);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            if ("MODULO_NAO_ENCONTRADO".equals(e.getMessage())) {
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