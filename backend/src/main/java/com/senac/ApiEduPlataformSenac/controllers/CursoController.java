package com.senac.ApiEduPlataformSenac.controllers;

import com.senac.ApiEduPlataformSenac.model.entities.Curso;
import com.senac.ApiEduPlataformSenac.model.entities.Professor;
import com.senac.ApiEduPlataformSenac.model.repository.CursoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/cursos")
@Tag(name = "Cursos controller", description = "Responsavel por gerenciar os cursos!")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    @Operation(summary = "Listar cursos", description = "Listar cursos do professor logado")
    public ResponseEntity<?> listarTodos(@RequestAttribute("usuario") Professor usuario){

        List<Curso> cursos = cursoRepository.findAllByProfessorId(usuario.getId());

        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar curso por ID", description = "Apenas o professor dono pode visualizar")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id, @RequestAttribute("usuario") Professor usuario){

        Curso curso = cursoRepository.findById(id).orElse(null);

        if (curso == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!curso.getProfessor().getId().equals(usuario.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(curso);
    }

    @PostMapping
    @Operation(summary = "Criar curso", description = "Criar curso vinculado ao professor logado")
    public ResponseEntity<?> salvar(@RequestBody Curso curso,@RequestAttribute("usuario") Professor usuario){

        Curso novoCurso = new Curso();
        novoCurso.setTitulo(curso.getTitulo());
        novoCurso.setDescricao(curso.getDescricao());
        novoCurso.setPreco(curso.getPreco());
        novoCurso.setProfessor(usuario);

        return ResponseEntity.ok(cursoRepository.save(novoCurso).getId());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar curso", description = "Apenas o professor dono pode atualizar")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @RequestBody Curso curso,
                                       @RequestAttribute("usuario") Professor usuario){

        Curso cursoBanco = cursoRepository.findById(id).orElse(null);

        if (cursoBanco == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!cursoBanco.getProfessor().equals(usuario)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        cursoBanco.setTitulo(curso.getTitulo());
        cursoBanco.setDescricao(curso.getDescricao());
        cursoBanco.setPreco(curso.getPreco());

        cursoRepository.save(cursoBanco);

        return ResponseEntity.ok(cursoBanco);
    }
}