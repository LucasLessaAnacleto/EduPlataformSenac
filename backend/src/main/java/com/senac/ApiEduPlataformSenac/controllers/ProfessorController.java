package com.senac.ApiEduPlataformSenac.controllers;

import com.senac.ApiEduPlataformSenac.model.dto.ProfessorResponse;
import com.senac.ApiEduPlataformSenac.model.entities.Professor;
import com.senac.ApiEduPlataformSenac.model.repository.ProfessorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professores")
@Tag(name = "Professor controller", description = "Responsavel por gerenciar os professores!")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping
    @Operation(summary = "Listar todos", description = "Listar todos os professores!")
    public ResponseEntity<?> listarTodos(){

        List<ProfessorResponse> professores = professorRepository.findAll().stream().map(professor -> new ProfessorResponse(
                professor.getId(),
                professor.getNome(),
                professor.getEmail(),
                professor.getBiografia()
        )).toList();

        return ResponseEntity.ok(professores);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta de professor por ID", description = "Consultar um professor por ID")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){

        Professor professor = professorRepository.findById(id).orElse(null);

        if(professor == null){
            return ResponseEntity.ok(null);
        }

        ProfessorResponse response = new ProfessorResponse(
                professor.getId(),
                professor.getNome(),
                professor.getEmail(),
                professor.getBiografia()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Criar professor", description = "Responsável por criar professor")
    public ResponseEntity<?> salvar(@RequestBody Professor professor){

        Professor novoProfessor = new Professor();
        novoProfessor.setNome(professor.getNome());
        novoProfessor.setEmail(professor.getEmail());
        novoProfessor.setSenha(professor.getSenha());
        novoProfessor.setBiografia(professor.getBiografia());

        return ResponseEntity.ok(professorRepository.save(novoProfessor).getId());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar professor", description = "Responsável por atualizar professor")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Professor professor){

        Professor professorBanco = professorRepository.findById(id).orElse(null);

        if (professorBanco != null){

            professorBanco.setNome(professor.getNome());
            professorBanco.setEmail(professor.getEmail());
            professorBanco.setBiografia(professor.getBiografia());

            professorBanco.setSenha(
                    professor.getSenha() != null ? professor.getSenha() : professorBanco.getSenha()
            );

            professorRepository.save(professorBanco);

            ProfessorResponse response = new ProfessorResponse(
                    professorBanco.getId(),
                    professorBanco.getNome(),
                    professorBanco.getEmail(),
                    professorBanco.getBiografia()
            );

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }
}