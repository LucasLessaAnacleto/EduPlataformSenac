package com.senac.ApiEduPlataformSenac.services;

import com.senac.ApiEduPlataformSenac.model.dto.LoginRequest;
import com.senac.ApiEduPlataformSenac.model.dto.StatusUsuarioRequest;
import com.senac.ApiEduPlataformSenac.model.entities.Usuario;
import com.senac.ApiEduPlataformSenac.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    public UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public boolean validaUsuarioSenha(LoginRequest loginRequest) {
        try{
            return usuarioRepository.existsByEmailAndSenha(loginRequest.email(), loginRequest.senha());
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public Usuario alterarUsuario(Long id, Usuario usuario) {
        try {
            Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);

            if (usuarioBanco != null) {
                usuarioBanco.setEmail(usuario.getEmail());
                usuarioBanco.setNome(usuario.getNome());
                usuarioBanco.setSenha(
                        usuario.getSenha() != null ? usuario.getSenha() : usuarioBanco.getSenha()
                );
                usuarioBanco.setStatus(
                        usuario.getStatus() != null ? usuario.getStatus() : usuarioBanco.getStatus()
                );

                usuarioRepository.save(usuarioBanco);
            }
            return usuarioBanco;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

//    public void alterarStatusUsuario(Long id, StatusUsuarioRequest alterarStatusUsuario) {
//
//    }
}
