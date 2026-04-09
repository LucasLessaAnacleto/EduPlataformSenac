package com.senac.ApiEduPlataformSenac.controllers;

import com.senac.ApiEduPlataformSenac.model.dto.MatriculaRequest;
import com.senac.ApiEduPlataformSenac.model.entities.Curso;
import com.senac.ApiEduPlataformSenac.model.entities.Matricula;
import com.senac.ApiEduPlataformSenac.model.entities.Professor;
import com.senac.ApiEduPlataformSenac.model.repository.CursoRepository;
import com.senac.ApiEduPlataformSenac.model.repository.MatriculaRepository;
import com.senac.ApiEduPlataformSenac.model.repository.ProfessorRepository;
import com.senac.ApiEduPlataformSenac.util.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping
public class MatriculaController {

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    private Professor getProfessor(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token == null) {
            throw new RuntimeException("Token não informado");
        }

        Long professorId = TokenUtil.validateToken(token);

        Professor professor = professorRepository.findById(professorId).orElse(null);

        if(professor == null){
            throw new RuntimeException("Professor não autorizado");
        }
        return professor;
    }

    @GetMapping("/cursos/{cursoId}/matriculas")
    @Operation(summary = "Listar matrículas", description = "Lista alunos do curso (somente professor dono)")
    public ResponseEntity<?> listarPorCurso(@PathVariable Long cursoId, HttpServletRequest request){

        try {
            Professor professor = getProfessor(request);

            Curso curso = cursoRepository.findById(cursoId).orElse(null);

            if (curso == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if (!curso.getProfessor().equals(professor)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            List<Matricula> matriculas = matriculaRepository.findAllByCursoId(cursoId);

            return ResponseEntity.ok(matriculas);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/matriculas")
    @Operation(summary = "Criar matrícula", description = "Cadastrar aluno em um curso (validando professor)")
    public ResponseEntity<?> salvar(@RequestBody MatriculaRequest requestDto, HttpServletRequest request){

        try {
            Professor professor = getProfessor(request);

            Curso curso = cursoRepository.findById(requestDto.cursoId()).orElse(null);

            if (curso == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if (!curso.getProfessor().equals(professor)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            Matricula matricula = new Matricula();
            matricula.setNomeAluno(requestDto.nomeAluno());
            matricula.setEmailAluno(requestDto.emailAluno());
            matricula.setData(LocalDate.now());
            matricula.setCurso(curso);

            return ResponseEntity.ok(matriculaRepository.save(matricula).getId());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/matriculas/{id}")
    @Operation(summary = "Atualizar matrícula", description = "Valida professor dono e não permite alterar curso")
    public ResponseEntity<?> atualizar(@PathVariable Long id,@RequestBody MatriculaRequest requestDto,HttpServletRequest request) {

        try {
            Professor professor = getProfessor(request);

            Matricula matriculaBanco = matriculaRepository.findById(id).orElse(null);

            if (matriculaBanco == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Curso curso = matriculaBanco.getCurso();

            if (!curso.getProfessor().equals(professor)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            matriculaBanco.setNomeAluno(requestDto.nomeAluno());
            matriculaBanco.setEmailAluno(requestDto.emailAluno());

            matriculaRepository.save(matriculaBanco);

            return ResponseEntity.ok(matriculaBanco);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/matriculas/{id}")
    @Operation(summary = "Deletar matrícula", description = "Remove aluno do curso (somente professor dono)")
    public ResponseEntity<?> deletar(@PathVariable Long id, HttpServletRequest request){

        try {
            Professor professor = getProfessor(request);

            Matricula matricula = matriculaRepository.findById(id).orElse(null);

            if (matricula == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Curso curso = matricula.getCurso();

            if (!curso.getProfessor().equals(professor)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            matriculaRepository.delete(matricula);

            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}