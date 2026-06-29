package com.senac.ApiEduPlataformSenac.application.services;

import com.senac.ApiEduPlataformSenac.application.DTO.*;
import com.senac.ApiEduPlataformSenac.domain.entities.Usuario;
import com.senac.ApiEduPlataformSenac.domain.enuns.EnumStatusUsuario;
import com.senac.ApiEduPlataformSenac.domain.repository.UsuarioRepository;
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

    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public UsuarioResponse buscarPorId(Long id) throws Exception {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);

        if (usuario == null) {
            throw new Exception("USUARIO_NAO_ENCONTRADO");
        }

        return converterParaResponse(usuario);
    }

    public boolean validaUsuarioSenha(LoginRequest loginRequest) {
        try{
            return usuarioRepository.existsByEmailAndSenha(loginRequest.email(), loginRequest.senha());
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public UsuarioResponse alterarUsuario(Long id, UsuarioRequest usuarioRequest) throws Exception {
        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);

        if (usuarioBanco == null) {
            throw new Exception("USUARIO_NAO_ENCONTRADO");
        }

        if (usuarioRequest.email() != null && !usuarioRequest.email().equals(usuarioBanco.getEmail())) {
            if (usuarioRepository.findByEmail(usuarioRequest.email()).isPresent()) {
                throw new Exception("EMAIL_JA_CADASTRADO");
            }

            usuarioBanco.setEmail(usuarioRequest.email());
        }

        if (usuarioRequest.senha() != null && !usuarioRequest.senha().isBlank()) {
            usuarioBanco.setSenha(usuarioRequest.senha());
        }

        if( usuarioRequest.status() != null ) {
            usuarioBanco.setStatus(usuarioRequest.status());
        }

        Usuario usuarioAlterado = usuarioRepository.save(usuarioBanco);

        return converterParaResponse(usuarioAlterado);
    }

    public void alterarStatusUsuario(Long id, StatusUsuarioRequest alterarStatusUsuario) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        usuario.setStatus(alterarStatusUsuario.status());

        usuarioRepository.save(usuario);
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

    private UsuarioResponse converterParaResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getStatus(),
                usuario.getRole()
        );
    }
}
