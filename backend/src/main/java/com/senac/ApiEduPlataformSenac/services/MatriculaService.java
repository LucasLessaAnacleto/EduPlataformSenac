package com.senac.ApiEduPlataformSenac.services;

import com.senac.ApiEduPlataformSenac.model.dto.MatriculaRequest;
import com.senac.ApiEduPlataformSenac.model.dto.MatriculaResponse;
import com.senac.ApiEduPlataformSenac.model.entities.Curso;
import com.senac.ApiEduPlataformSenac.model.entities.Matricula;
import com.senac.ApiEduPlataformSenac.model.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private EmailService emailService;

    public List<MatriculaResponse> listarPorCurso(Long cursoId, Authentication authentication) throws Exception {
        cursoService.buscarCursoDoProfessor(cursoId, authentication);

        return matriculaRepository.findAllByCursoId(cursoId)
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public Long salvar(MatriculaRequest request, Authentication authentication) throws Exception {
        if (request.cursoId() == null) {
            throw new Exception("CURSO_ID_OBRIGATORIO");
        }

        Curso curso = cursoService.buscarCursoDoProfessor(request.cursoId(), authentication);

        Matricula matricula = new Matricula();
        matricula.setNomeAluno(request.nomeAluno());
        matricula.setEmailAluno(request.emailAluno());
        matricula.setData(LocalDate.now());
        matricula.setCurso(curso);

        emailService.enviarBoasVindas(
                matricula.getEmailAluno(),
                matricula.getNomeAluno(),
                curso.getTitulo()
        );

        return matriculaRepository.save(matricula).getId();
    }

    public MatriculaResponse atualizar(Long id, MatriculaRequest request, Authentication authentication) throws Exception {
        Matricula matriculaBanco = buscarMatricula(id);

        if (matriculaBanco == null) {
            throw new Exception("MATRICULA_NAO_ENCONTRADA");
        }

        cursoService.buscarCursoDoProfessor(matriculaBanco.getCurso().getId(), authentication);

        matriculaBanco.setNomeAluno(request.nomeAluno());
        matriculaBanco.setEmailAluno(request.emailAluno());

        matriculaRepository.save(matriculaBanco);

        return converterParaResponse(matriculaBanco);
    }

    public void deletar(Long id, Authentication authentication) throws Exception {
        Matricula matricula = buscarMatricula(id);

        if (matricula == null) {
            throw new Exception("MATRICULA_NAO_ENCONTRADA");
        }

        cursoService.buscarCursoDoProfessor(matricula.getCurso().getId(), authentication);

        matriculaRepository.delete(matricula);
    }

    private Matricula buscarMatricula(Long id) {
        return matriculaRepository.findById(id).orElse(null);
    }

    private MatriculaResponse converterParaResponse(Matricula matricula) {
        return new MatriculaResponse(
                matricula.getId(),
                matricula.getNomeAluno(),
                matricula.getEmailAluno(),
                matricula.getData(),
                matricula.getCurso().getId()
        );
    }
}