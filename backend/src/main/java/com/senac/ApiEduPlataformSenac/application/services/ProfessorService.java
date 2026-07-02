package com.senac.ApiEduPlataformSenac.application.services;

import com.senac.ApiEduPlataformSenac.application.DTO.ProfessorRequest;
import com.senac.ApiEduPlataformSenac.application.DTO.ProfessorResponse;
import com.senac.ApiEduPlataformSenac.domain.entities.Professor;
import com.senac.ApiEduPlataformSenac.domain.entities.Usuario;
import com.senac.ApiEduPlataformSenac.domain.repository.ProfessorRepository;
import com.senac.ApiEduPlataformSenac.domain.valueobjects.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public Professor buscarProfessorLogadoEntidade(Authentication authentication) throws Exception {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();

        Optional<Professor> professor = professorRepository.findByUsuarioId(usuarioLogado.getId());

        if (professor.isEmpty()) {
            throw new Exception("USUARIO_SEM_PROFESSOR");
        }

        return professor.get();
    }

    public ProfessorResponse buscarProfessorLogado(Authentication authentication) throws Exception {
        Professor professor = buscarProfessorLogadoEntidade(authentication);
        return new ProfessorResponse(
                professor.getId(),
                professor.getNome(),
                professor.getCpf().toString(),
                professor.getBiografia()
        );
    }

    public List<ProfessorResponse> listarTodos() {
        return professorRepository.findAll()
                .stream()
                .map(professor -> new ProfessorResponse(
                        professor.getId(),
                        professor.getNome(),
                        professor.getCpf().toString(),
                        professor.getBiografia()
                ))
                .toList();
    }

    public ProfessorResponse buscarPorId(Long id) {
        Professor professor = professorRepository.findById(id).orElse(null);

        if (professor == null) {
            return null;
        }

        return new ProfessorResponse(
                professor.getId(),
                professor.getNome(),
                professor.getCpf().toString(),
                professor.getBiografia()
        );
    }

    public ProfessorResponse atualizarProfessorLogado(Authentication authentication, ProfessorRequest professorRequest) throws Exception {
        Professor professorBanco = buscarProfessorLogadoEntidade(authentication);

        professorBanco.setNome(professorRequest.nome());
        professorBanco.setCpf( new CPF(professorRequest.cpf()) );
        professorBanco.setBiografia(professorRequest.biografia());

        professorRepository.save(professorBanco);

        return new ProfessorResponse(
                professorBanco.getId(),
                professorBanco.getNome(),
                professorBanco.getCpf().toString(),
                professorBanco.getBiografia()
        );
    }

    public Professor criarProfessor(Usuario usuario, ProfessorRequest professorRequest) {
        Professor professor = new Professor();

        professor.setNome(professorRequest.nome());
        professor.setCpf( new CPF(professorRequest.cpf()) );
        professor.setBiografia(professorRequest.biografia());
        professor.setUsuario(usuario);

        return professorRepository.save(professor);
    }

    public ProfessorResponse buscarProfessorPorUsuarioId(Long usuarioId, Authentication authentication) throws Exception {
        if (!usuarioEhAdmin(authentication)) {
            throw new Exception("SEM_PERMISSAO");
        }

        Optional<Professor> professorOptional = professorRepository.findByUsuarioId(usuarioId);

        if (professorOptional.isEmpty()) {
            throw new Exception("PROFESSOR_NAO_ENCONTRADO");
        }

        return converterParaResponse(professorOptional.get());
    }

    public ProfessorResponse atualizarProfessorPorUsuarioId(
            Long usuarioId,
            ProfessorRequest professorRequest,
            Authentication authentication
    ) throws Exception {

        if (!usuarioEhAdmin(authentication)) {
            throw new Exception("SEM_PERMISSAO");
        }

        Optional<Professor> professorOptional = professorRepository.findByUsuarioId(usuarioId);

        if (professorOptional.isEmpty()) {
            throw new Exception("PROFESSOR_NAO_ENCONTRADO");
        }

        Professor professor = professorOptional.get();

        professor.setNome(professorRequest.nome());
        professor.setCpf(new CPF(professorRequest.cpf()));
        professor.setBiografia(professorRequest.biografia());

        Professor professorAtualizado = professorRepository.save(professor);

        return converterParaResponse(professorAtualizado);
    }

    private boolean usuarioEhAdmin(Authentication authentication) {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();

        return usuarioLogado.getRole() != null
                && usuarioLogado.getRole().toString().equals("ROLE_ADMIN");
    }

    private ProfessorResponse converterParaResponse(Professor professor) {
        return new ProfessorResponse(
                professor.getId(),
                professor.getNome(),
                professor.getCpf().toString(),
                professor.getBiografia()
        );
    }
}