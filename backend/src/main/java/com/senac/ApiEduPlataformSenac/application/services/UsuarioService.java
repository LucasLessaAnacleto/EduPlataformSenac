package com.senac.ApiEduPlataformSenac.application.services;

import com.senac.ApiEduPlataformSenac.application.DTO.*;
import com.senac.ApiEduPlataformSenac.domain.entities.Usuario;
import com.senac.ApiEduPlataformSenac.domain.repository.UsuarioRepository;
import com.senac.ApiEduPlataformSenac.domain.valueobjects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    public UsuarioRepository usuarioRepository;

    @Autowired
    public ProfessorService professorService;

    @Value("${spring.secretKey}")
    private String secret;

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
            return usuarioRepository.existsByEmail_emailAndSenha(loginRequest.email().toLowerCase(), loginRequest.senha());
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

        if (usuarioRequest.email() != null && !usuarioRequest.email().equals(usuarioBanco.getEmail().toString())) {
            if (usuarioRepository.findByEmail_email(usuarioRequest.email()).isPresent()) {
                throw new Exception("EMAIL_JA_CADASTRADO");
            }

            usuarioBanco.setEmail( new Email(usuarioRequest.email()) );
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
        if (usuarioRepository.findByEmail_email(usuarioRequest.email()).isPresent()) {
            throw new Exception("EMAIL_JA_CADASTRADO");
        }

        Usuario usuarioSalvo = usuarioRepository.save(new Usuario(usuarioRequest));

        ProfessorRequest professorRequest = new ProfessorRequest(
                usuarioRequest.nome(),
                usuarioRequest.cpf(),
                usuarioRequest.biografia()
        );

        professorService.criarProfessor(usuarioSalvo, professorRequest);

        return new UsuarioResponse(
                usuarioSalvo.getId(),
                usuarioSalvo.getEmail().toString(),
                usuarioSalvo.getStatus(),
                usuarioSalvo.getRole()
        );
    }

    public UsuarioResponse criarUsuarioAdm(UsuarioAdmRequest usuarioRequest) throws Exception {
        if(usuarioRequest.secretKey() != null && usuarioRequest.secretKey().equals( secret )) {
            if (usuarioRepository.findByEmail_email(usuarioRequest.email()).isPresent()) {
                throw new Exception("EMAIL_JA_CADASTRADO");
            }

            Usuario usuarioSalvo = usuarioRepository.save( new Usuario(usuarioRequest) );

            return new UsuarioResponse(
                    usuarioSalvo.getId(),
                    usuarioSalvo.getEmail().toString(),
                    usuarioSalvo.getStatus(),
                    usuarioSalvo.getRole()
            );
        }else {
            throw new Exception("SECRET_INVALIDO");
        }
    }

    public UsuarioResponse buscarUsuarioLogado(Authentication authentication) {
        try {
            Usuario usuario = (Usuario) authentication.getPrincipal();

            return usuarioRepository.findById(usuario.getId())
                    .map(usuarioBanco -> new UsuarioResponse(
                            usuarioBanco.getId(),
                            usuarioBanco.getEmail().toString(),
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
                usuario.getEmail().toString(),
                usuario.getStatus(),
                usuario.getRole()
        );
    }
}
