package com.senac.ApiEduPlataformSenac.domain.repository;

import com.senac.ApiEduPlataformSenac.domain.entities.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findByUsuarioId(Long usuarioId);

    boolean existsByUsuarioId(Long usuarioId);

}
