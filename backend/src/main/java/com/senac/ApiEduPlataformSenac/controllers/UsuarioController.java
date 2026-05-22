package com.senac.ApiEduPlataformSenac.controllers;

import com.senac.ApiEduPlataformSenac.model.dto.StatusUsuarioRequest;
import com.senac.ApiEduPlataformSenac.model.dto.UsuarioResponse;
import com.senac.ApiEduPlataformSenac.model.entities.Usuario;
import com.senac.ApiEduPlataformSenac.model.enuns.EnumStatusUsuario;
import com.senac.ApiEduPlataformSenac.model.repository.UsuarioRepository;
import com.senac.ApiEduPlataformSenac.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios controller", description = "Responsavel por gerenciar os usuarios!")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;


    @GetMapping
    @Operation(summary = "Listar todos",description = "Listar todos os usuários!")
    public ResponseEntity<?> listarTodos(){

        List<UsuarioResponse> usuarios = usuarioRepository.findAll().stream().map(usuario -> new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getStatus()
        )).toList();
        return ResponseEntity.ok(usuarios);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Consulta de usuario por ID", description = "Responsavel por consultar um unico usuario por ID e se não existir retorna null!")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if(usuario == null){
            return ResponseEntity.ok(null);
        }
        UsuarioResponse usuarioResponse = new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getStatus());
        return ResponseEntity.ok(usuarioResponse);
    }

    @PostMapping
    @Operation(summary = "Criar usuario",description = "Resposavel por criar usuário")
    public ResponseEntity<?> salvar (@RequestBody Usuario usuario){
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(usuario.getNome());
        novoUsuario.setEmail(usuario.getEmail());
        novoUsuario.setSenha(usuario.getSenha());
        novoUsuario.setStatus(EnumStatusUsuario.ATIVO);

        return ResponseEntity.ok(usuarioRepository.save(novoUsuario).getId());
    }

    @PostMapping("/adm")
    @Operation(summary = "Criar usuario Administrador",description = "Resposavel por criar um usuário admin")
    public ResponseEntity<?> salvarAdmin (@RequestBody Usuario usuario){
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(usuario.getNome());
        novoUsuario.setEmail(usuario.getEmail());
        novoUsuario.setSenha(usuario.getSenha());
        novoUsuario.setStatus(EnumStatusUsuario.ATIVO);

        return ResponseEntity.ok(usuarioRepository.save(novoUsuario).getId());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuario",description = "Resposavel por atualizar usuário")
    public ResponseEntity<?> atualizar (@PathVariable Long id, @RequestBody Usuario usuario){

        var usuarioAlterado = usuarioService.alterarUsuario(id, usuario);
        if(usuarioAlterado != null){
            return ResponseEntity.ok(
                    new UsuarioResponse(usuarioAlterado.getId(), usuarioAlterado.getNome(), usuarioAlterado.getEmail(), usuarioAlterado.getStatus())
            );
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/AlterarStatus")
    @Operation(summary = "Alterar status do usuário",description = "Resposavel por ativar/inativar usuário")
    public ResponseEntity<?> alterarStatus(@PathVariable Long id, @RequestBody StatusUsuarioRequest alterarStatusUsuario){
//        usuarioService.alterarStatusUsuario(id, alterarStatusUsuario);
        return ResponseEntity.ok().build();
        // return ResponseEntity.notFound().build();
    }

}