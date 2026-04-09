package com.senac.ApiEduPlataformSenac.model.repository;

import com.senac.ApiEduPlataformSenac.model.entities.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findByEmail(String email);
    Optional<Professor> findByIdAndEmail(Long id, String email);
}
