package com.senac.ApiEduPlataformSenac.application.services;

import com.senac.ApiEduPlataformSenac.application.DTO.CursoRequest;
import com.senac.ApiEduPlataformSenac.application.DTO.CursoResponse;
import com.senac.ApiEduPlataformSenac.domain.entities.Curso;
import com.senac.ApiEduPlataformSenac.domain.entities.Professor;
import com.senac.ApiEduPlataformSenac.domain.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ProfessorService professorService;

    public List<CursoResponse> listarTodos(Authentication authentication) throws Exception {
        Professor professorLogado = professorService.buscarProfessorLogadoEntidade(authentication);

        return cursoRepository.findAllByProfessorId(professorLogado.getId())
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public CursoResponse buscarPorId(Long id, Authentication authentication) throws Exception {
        Professor professorLogado = professorService.buscarProfessorLogadoEntidade(authentication);

        Curso curso = buscarCurso(id);

        if (curso == null) {
            throw new Exception("CURSO_NAO_ENCONTRADO");
        }

        if (!cursoPertenceAoProfessor(curso, professorLogado)) {
            throw new Exception("SEM_PERMISSAO");
        }

        return converterParaResponse(curso);
    }

    public CursoResponse salvar(CursoRequest cursoRequest, Authentication authentication) throws Exception {
        Professor professorLogado = professorService.buscarProfessorLogadoEntidade(authentication);

        Curso curso = new Curso();
        curso.setTitulo(cursoRequest.titulo());
        curso.setDescricao(cursoRequest.descricao());
        curso.setPreco(cursoRequest.preco());
        curso.setProfessor(professorLogado);

        Curso cursoSalvo = cursoRepository.save(curso);

        return converterParaResponse(cursoSalvo);
    }

    public CursoResponse atualizar(Long id, CursoRequest cursoRequest, Authentication authentication) throws Exception {
        Professor professorLogado = professorService.buscarProfessorLogadoEntidade(authentication);

        Curso curso = buscarCurso(id);

        if (curso == null) {
            throw new Exception("CURSO_NAO_ENCONTRADO");
        }

        if (!cursoPertenceAoProfessor(curso, professorLogado)) {
            throw new Exception("SEM_PERMISSAO");
        }

        curso.setTitulo(cursoRequest.titulo());
        curso.setDescricao(cursoRequest.descricao());
        curso.setPreco(cursoRequest.preco());

        Curso cursoAtualizado = cursoRepository.save(curso);

        return converterParaResponse(cursoAtualizado);
    }

    public void deletar(Long id, Authentication authentication) throws Exception {
        Professor professorLogado = professorService.buscarProfessorLogadoEntidade(authentication);

        Curso curso = buscarCurso(id);

        if (curso == null) {
            throw new Exception("CURSO_NAO_ENCONTRADO");
        }

        if (!cursoPertenceAoProfessor(curso, professorLogado)) {
            throw new Exception("SEM_PERMISSAO");
        }

        cursoRepository.delete(curso);
    }

    private Curso buscarCurso(Long id) {
        return cursoRepository.findById(id).orElse(null);
    }

    public Curso buscarCursoDoProfessor(Long cursoId, Authentication authentication) throws Exception {
        Professor professorLogado = professorService.buscarProfessorLogadoEntidade(authentication);

        Curso curso = buscarCurso(cursoId);

        if (curso == null) {
            throw new Exception("CURSO_NAO_ENCONTRADO");
        }

        if (!cursoPertenceAoProfessor(curso, professorLogado)) {
            throw new Exception("SEM_PERMISSAO");
        }

        return curso;
    }

    private boolean cursoPertenceAoProfessor(Curso curso, Professor professorLogado) {
        if (curso.getProfessor() == null) {
            return false;
        }

        return curso.getProfessor().getId().equals(professorLogado.getId());
    }

    private CursoResponse converterParaResponse(Curso curso) {
        return new CursoResponse(
                curso.getId(),
                curso.getTitulo(),
                curso.getDescricao(),
                curso.getPreco(),
                curso.getProfessor().getId()
        );
    }
}