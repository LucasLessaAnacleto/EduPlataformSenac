package com.senac.ApiEduPlataformSenac.application.services;

import com.senac.ApiEduPlataformSenac.domain.valueobjects.Email;
import com.senac.ApiEduPlataformSenac.infra.external.EnvioDeEmail;
import com.senac.ApiEduPlataformSenac.application.DTO.MatriculaRequest;
import com.senac.ApiEduPlataformSenac.application.DTO.MatriculaResponse;
import com.senac.ApiEduPlataformSenac.domain.entities.Curso;
import com.senac.ApiEduPlataformSenac.domain.entities.Matricula;
import com.senac.ApiEduPlataformSenac.domain.repository.MatriculaRepository;
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
    private EnvioDeEmail envioDeEmail;

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
        matricula.setEmailAluno( new Email(request.emailAluno()) );
        matricula.setData(LocalDate.now());
        matricula.setCurso(curso);

        Matricula matriculaSalva = matriculaRepository.save(matricula);

        try {
            envioDeEmail.enviarBoasVindas(
                    matriculaSalva.getEmailAluno().toString(),
                    matriculaSalva.getNomeAluno(),
                    curso.getTitulo()
            );
        } catch (Exception e) {
            System.out.println("Não foi possível enviar o e-mail de boas-vindas: " + e.getMessage());
        }

        return matriculaSalva.getId();
    }

    public MatriculaResponse atualizar(Long id, MatriculaRequest request, Authentication authentication) throws Exception {
        Matricula matriculaBanco = buscarMatricula(id);

        if (matriculaBanco == null) {
            throw new Exception("MATRICULA_NAO_ENCONTRADA");
        }

        cursoService.buscarCursoDoProfessor(matriculaBanco.getCurso().getId(), authentication);

        matriculaBanco.setNomeAluno(request.nomeAluno());
        matriculaBanco.setEmailAluno( new Email(request.emailAluno()) );

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
                matricula.getEmailAluno().toString(),
                matricula.getData(),
                matricula.getCurso().getId()
        );
    }
}