package com.senac.ApiEduPlataformSenac.presentation;

import com.senac.ApiEduPlataformSenac.application.DTO.*;
import com.senac.ApiEduPlataformSenac.application.services.UsuarioService;
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
@RequestMapping("/usuarios")
@Tag(name = "Usuarios controller", description = "Responsável por gerenciar os usuários")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listar todos", description = "Listar todos os usuários")
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        try {
            return ResponseEntity.ok(usuarioService.listarTodos());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta de usuario por ID", description = "Responsável por consultar um usuário por ID")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.buscarPorId(id));

        } catch (Exception e) {
            if ("USUARIO_NAO_ENCONTRADO".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping
    @Operation(summary = "Criar usuário professor", description = "Cria usuário com ROLE_PROFESSOR e perfil de professor")
    public ResponseEntity<?> salvar(@RequestBody UsuarioProfessorRequest usuarioProfessorRequest) {
        try {
            UsuarioResponse usuario = usuarioService.criarUsuarioProfessor(usuarioProfessorRequest);
            return ResponseEntity.ok(usuario.id());

        } catch (Exception e) {
            if ("EMAIL_JA_CADASTRADO".equals(e.getMessage())) {
                return ResponseEntity.badRequest().body("E-mail já cadastrado.");
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/adm")
    @Operation(summary = "Criar usuario Administrador", description = "Responsável por criar um usuário admin")
    public ResponseEntity<?> salvarAdmin(@RequestBody UsuarioAdmRequest usuarioRequest) {
        try {
            UsuarioResponse usuario = usuarioService.criarUsuarioAdm(usuarioRequest);
            return ResponseEntity.ok(usuario.id());

        } catch (Exception e) {
            if ("EMAIL_JA_CADASTRADO".equals(e.getMessage())) {
                return ResponseEntity.badRequest().body("E-mail já cadastrado.");
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuario", description = "Responsável por atualizar usuário")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Long id, @RequestBody UsuarioRequest usuarioRequest) {
        try {
            return ResponseEntity.ok(usuarioService.alterarUsuario(id, usuarioRequest));

        } catch (Exception e) {
            if ("USUARIO_NAO_ENCONTRADO".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if ("EMAIL_JA_CADASTRADO".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}/AlterarStatus")
    @Operation(summary = "Alterar status do usuário", description = "Responsável por ativar/inativar usuário")
    public ResponseEntity<Void> alterarStatus(
            @PathVariable Long id,
            @RequestBody StatusUsuarioRequest alterarStatusUsuario
    ) {
        try {
            usuarioService.alterarStatusUsuario(id, alterarStatusUsuario);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            if ("USUARIO_NAO_ENCONTRADO".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/usuariologado")
    @Operation(summary = "Consulta usuario logado", description = "Busca usuário da sessão")
    public ResponseEntity<UsuarioResponse> buscarUsuarioLogado(Authentication authentication) {
        try {
            return ResponseEntity.ok(usuarioService.buscarUsuarioLogado(authentication));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}