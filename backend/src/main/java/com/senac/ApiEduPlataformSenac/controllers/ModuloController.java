package com.senac.ApiEduPlataformSenac.controllers;

import com.senac.ApiEduPlataformSenac.model.repository.ProfessorRepository;
import org.springframework.web.bind.annotation.RestController;

import com.senac.ApiEduPlataformSenac.model.dto.ModuloRequest;
import com.senac.ApiEduPlataformSenac.model.entities.Curso;
import com.senac.ApiEduPlataformSenac.model.entities.Modulo;
import com.senac.ApiEduPlataformSenac.model.entities.Professor;
import com.senac.ApiEduPlataformSenac.model.repository.CursoRepository;
import com.senac.ApiEduPlataformSenac.model.repository.ModuloRepository;
import com.senac.ApiEduPlataformSenac.util.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping
public class ModuloController {

    @Autowired
    private ModuloRepository moduloRepository;

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

    @GetMapping("/cursos/{cursoId}/modulos")
    @Operation(summary = "Listar módulos do curso", description = "Valida professor dono e ordena por ordem")
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

            List<Modulo> modulos = moduloRepository.findAllByCursoId(cursoId)
                    .stream()
                    .sorted(Comparator.comparing(Modulo::getOrdem))
                    .toList();

            return ResponseEntity.ok(modulos);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/modulos")
    @Operation(summary = "Criar módulo", description = "Recebe cursoId e valida professor dono")
    public ResponseEntity<?> salvar(@RequestBody ModuloRequest requestDto, HttpServletRequest request){

        try {
            Professor professor = getProfessor(request);

            Curso curso = cursoRepository.findById(requestDto.cursoId()).orElse(null);

            if (curso == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if (!curso.getProfessor().equals(professor)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            Modulo modulo = new Modulo();
            modulo.setTitulo(requestDto.titulo());
            modulo.setOrdem(requestDto.ordem());
            modulo.setCurso(curso);

            return ResponseEntity.ok(moduloRepository.save(modulo).getId());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/modulos/{id}")
    @Operation(summary = "Atualizar módulo", description = "Valida professor dono e não permite alterar curso")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @RequestBody ModuloRequest requestDto,
                                       HttpServletRequest request){

        try {
            Professor professor = getProfessor(request);

            Modulo moduloBanco = moduloRepository.findById(id).orElse(null);

            if (moduloBanco == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Curso curso = moduloBanco.getCurso();

            if (!curso.getProfessor().equals(professor)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            moduloBanco.setTitulo(requestDto.titulo());
            moduloBanco.setOrdem(requestDto.ordem());

            moduloRepository.save(moduloBanco);

            return ResponseEntity.ok(moduloBanco);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/modulos/{id}")
    @Operation(summary = "Deletar módulo", description = "Apenas o professor dono do curso pode deletar o módulo")
    public ResponseEntity<?> deletar(@PathVariable Long id, HttpServletRequest request){

        try {
            Professor professor = getProfessor(request);

            Modulo modulo = moduloRepository.findById(id).orElse(null);

            if (modulo == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Curso curso = modulo.getCurso();

            if (!curso.getProfessor().equals(professor)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            moduloRepository.delete(modulo);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}