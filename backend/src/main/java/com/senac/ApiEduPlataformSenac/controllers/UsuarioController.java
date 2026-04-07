package com.senac.ApiEduPlataformSenac.controllers;

import com.senac.ApiEduPlataformSenac.model.dto.UsuarioResponse;
import com.senac.ApiEduPlataformSenac.model.entities.Usuario;
import com.senac.ApiEduPlataformSenac.model.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios controller", description = "Responsavel por gerenciar os usuarios!")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping
    @Operation(summary = "Listar todos",description = "Listar todos os usuários!")
    public ResponseEntity<List<UsuarioResponse>> listarTodos(){

        List<UsuarioResponse> usuarios = usuarioRepository.findAll().stream().map(usuario -> new UsuarioResponse(
                usuario.getId().toString(),
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
        UsuarioResponse usuarioResponse = new UsuarioResponse(usuario.getId().toString(), usuario.getNome(), usuario.getEmail(), usuario.getStatus());
        return ResponseEntity.ok(usuarioResponse);
    }

    @PostMapping
    @Operation(summary = "Criar usuario",description = "Resposavel por criar usuário")
    public ResponseEntity<Long> salvar (@RequestBody Usuario usuario){

        return ResponseEntity.ok(usuarioRepository.save(usuario).getId());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuario",description = "Resposavel por atualizar usuário")
    public ResponseEntity<UsuarioResponse> salvar (@PathVariable Long id, @RequestBody Usuario usuario){

        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);

        if (usuarioBanco != null){
            usuarioBanco.setEmail(usuario.getEmail());
            usuarioBanco.setNome(usuario.getNome());
            usuarioBanco.setSenha(usuario.getSenha());
            usuarioBanco.setStatus(
                    usuario.getStatus() != null ? usuario.getStatus() : usuarioBanco.getStatus()
            );

            usuarioRepository.save(usuarioBanco);

            UsuarioResponse usuarioResponse = new UsuarioResponse(usuarioBanco.getId().toString(), usuarioBanco.getNome(), usuarioBanco.getEmail(), usuarioBanco.getStatus());

            return ResponseEntity.ok(usuarioResponse);
        }


        return ResponseEntity.notFound().build();
    }

}