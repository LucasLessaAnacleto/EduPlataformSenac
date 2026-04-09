package com.senac.ApiEduPlataformSenac.model.repository;

import com.senac.ApiEduPlataformSenac.model.entities.Curso;
import com.senac.ApiEduPlataformSenac.model.entities.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    List<Matricula> findAllByCursoId(Long cursoId);
}
