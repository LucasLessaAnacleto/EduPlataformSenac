package com.senac.ApiEduPlataformSenac.application.services;

import com.senac.ApiEduPlataformSenac.application.DTO.ProfessorRequest;
import com.senac.ApiEduPlataformSenac.application.DTO.ProfessorResponse;
import com.senac.ApiEduPlataformSenac.domain.entities.Professor;
import com.senac.ApiEduPlataformSenac.domain.entities.Usuario;
import com.senac.ApiEduPlataformSenac.domain.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public Professor buscarProfessorLogadoEntidade(Authentication authentication) {
        try {
            Usuario usuario = (Usuario) authentication.getPrincipal();

            return professorRepository.findByUsuarioId(usuario.getId())
                    .orElseThrow(() -> new RuntimeException("Usuário logado não possui professor vinculado"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ProfessorResponse buscarProfessorLogado(Authentication authentication) {
        Professor professor = buscarProfessorLogadoEntidade(authentication);
        return new ProfessorResponse(
                professor.getId(),
                professor.getNome(),
                professor.getCpf(),
                professor.getBiografia()
        );
    }

    public List<ProfessorResponse> listarTodos() {
        return professorRepository.findAll()
                .stream()
                .map(professor -> new ProfessorResponse(
                        professor.getId(),
                        professor.getNome(),
                        professor.getCpf(),
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
                professor.getCpf(),
                professor.getBiografia()
        );
    }

    public ProfessorResponse atualizarProfessorLogado(Authentication authentication, ProfessorRequest professorRequest) {
        Professor professorBanco = buscarProfessorLogadoEntidade(authentication);

        professorBanco.setNome(professorRequest.nome());
        professorBanco.setCpf(professorRequest.cpf());
        professorBanco.setBiografia(professorRequest.biografia());

        professorRepository.save(professorBanco);

        return new ProfessorResponse(
                professorBanco.getId(),
                professorBanco.getNome(),
                professorBanco.getCpf(),
                professorBanco.getBiografia()
        );
    }

    public Professor criarProfessor(Usuario usuario,ProfessorRequest professorRequest) {
        Professor professor = new Professor();

        professor.setNome(professorRequest.nome());
        professor.setCpf(professorRequest.cpf());
        professor.setBiografia(professorRequest.biografia());
        professor.setUsuario(usuario);

        return professorRepository.save(professor);
    }
}