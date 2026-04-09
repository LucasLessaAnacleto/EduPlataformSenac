package com.senac.ApiEduPlataformSenac.controllers;

import com.senac.ApiEduPlataformSenac.model.entities.Curso;
import com.senac.ApiEduPlataformSenac.model.entities.Professor;
import com.senac.ApiEduPlataformSenac.model.repository.CursoRepository;
import com.senac.ApiEduPlataformSenac.model.repository.ProfessorRepository;
import com.senac.ApiEduPlataformSenac.util.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
@Tag(name = "Cursos controller", description = "Responsavel por gerenciar os cursos!")
public class CursoController {

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

    @GetMapping
    @Operation(summary = "Listar cursos", description = "Listar cursos do professor logado")
    public ResponseEntity<?> listarTodos(HttpServletRequest request){

        try {
            Professor professor = getProfessor(request);

            List<Curso> cursos = cursoRepository.findAllByProfessorId(professor.getId());

            return ResponseEntity.ok(cursos);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar curso por ID", description = "Apenas o professor dono pode visualizar")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id, HttpServletRequest request){

        try {
            Professor professor = getProfessor(request);

            Curso curso = cursoRepository.findById(id).orElse(null);

            if (curso != null && !curso.getProfessor().equals(professor)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            return ResponseEntity.ok(curso);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping
    @Operation(summary = "Criar curso", description = "Criar curso vinculado ao professor logado")
    public ResponseEntity<?> salvar(@RequestBody Curso curso, HttpServletRequest request){

        try {
            Professor professor = getProfessor(request);

            Curso novoCurso = new Curso();
            novoCurso.setTitulo(curso.getTitulo());
            novoCurso.setDescricao(curso.getDescricao());
            novoCurso.setPreco(curso.getPreco());
            novoCurso.setProfessor(professor);

            return ResponseEntity.ok(cursoRepository.save(novoCurso).getId());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar curso", description = "Apenas o professor dono pode atualizar")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Curso curso, HttpServletRequest request){

        try {
            Professor professor = getProfessor(request);

            Curso cursoBanco = cursoRepository.findById(id).orElse(null);

            if(cursoBanco == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if (!cursoBanco.getProfessor().equals(professor)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            cursoBanco.setTitulo(curso.getTitulo());
            cursoBanco.setDescricao(curso.getDescricao());
            cursoBanco.setPreco(curso.getPreco());

            cursoRepository.save(cursoBanco);

            return ResponseEntity.ok(cursoBanco);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
