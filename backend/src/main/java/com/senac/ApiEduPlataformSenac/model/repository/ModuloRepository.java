package com.senac.ApiEduPlataformSenac.model.repository;

import com.senac.ApiEduPlataformSenac.model.entities.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Long> {
    List<Modulo> findAllByCursoId(Long cursoId);
}
