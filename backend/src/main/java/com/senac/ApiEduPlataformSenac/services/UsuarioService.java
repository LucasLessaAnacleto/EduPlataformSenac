package com.senac.ApiEduPlataformSenac.services;

import com.senac.ApiEduPlataformSenac.model.dto.*;
import com.senac.ApiEduPlataformSenac.model.entities.Usuario;
import com.senac.ApiEduPlataformSenac.model.enuns.EnumStatusUsuario;
import com.senac.ApiEduPlataformSenac.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    public UsuarioRepository usuarioRepository;

    @Autowired
    public ProfessorService professorService;

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

    public void alterarStatusUsuario(Long id, StatusUsuarioRequest alterarStatusUsuario) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        usuario.setStatus(alterarStatusUsuario.status());

        usuarioRepository.save(usuario);
    }

    public UsuarioResponse buscarUsuarioLogado(Authentication authentication) {
        try {
            Usuario usuario = (Usuario) authentication.getPrincipal();

            return usuarioRepository.findById(usuario.getId())
                    .map(usuarioBanco -> new UsuarioResponse(
                            usuarioBanco.getId(),
                            usuarioBanco.getEmail(),
                            usuarioBanco.getStatus(),
                            usuarioBanco.getRole()
                    ))
                    .orElse(null);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UsuarioResponse criarUsuarioProfessor(UsuarioProfessorRequest usuarioRequest) throws Exception {
        if (usuarioRepository.findByEmail(usuarioRequest.email()).isPresent()) {
            throw new Exception("EMAIL_JA_CADASTRADO");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioRequest.email());
        usuario.setSenha(usuarioRequest.senha());
        usuario.setRole("PROFESSOR");
        usuario.setStatus(EnumStatusUsuario.ATIVO);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        ProfessorRequest professorRequest = new ProfessorRequest(
                usuarioRequest.nome(),
                usuarioRequest.cpf(),
                usuarioRequest.biografia()
        );

        professorService.criarProfessor(usuarioSalvo, professorRequest);

        return new UsuarioResponse(
                usuarioSalvo.getId(),
                usuarioSalvo.getEmail(),
                usuarioSalvo.getStatus(),
                usuarioSalvo.getRole()
        );
    }

    public UsuarioResponse criarUsuarioAdm(UsuarioAdmRequest usuarioRequest) throws Exception {
        if (usuarioRepository.findByEmail(usuarioRequest.email()).isPresent()) {
            throw new Exception("EMAIL_JA_CADASTRADO");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioRequest.email());
        usuario.setSenha(usuarioRequest.senha());
        usuario.setRole("ADMIN");
        usuario.setStatus(EnumStatusUsuario.ATIVO);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new UsuarioResponse(
                usuarioSalvo.getId(),
                usuarioSalvo.getEmail(),
                usuarioSalvo.getStatus(),
                usuarioSalvo.getRole()
        );
    }
}
