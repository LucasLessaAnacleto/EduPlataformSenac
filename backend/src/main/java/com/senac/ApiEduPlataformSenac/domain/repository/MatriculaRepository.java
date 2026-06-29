package com.senac.ApiEduPlataformSenac.domain.repository;

import com.senac.ApiEduPlataformSenac.domain.entities.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    List<Matricula> findAllByCursoId(Long cursoId);
}
