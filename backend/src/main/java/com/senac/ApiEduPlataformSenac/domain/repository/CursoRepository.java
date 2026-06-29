package com.senac.ApiEduPlataformSenac.domain.repository;

import com.senac.ApiEduPlataformSenac.domain.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findAllByProfessorId(Long professorId);

    boolean existsByIdAndProfessorId(Long id, Long professorId);
}