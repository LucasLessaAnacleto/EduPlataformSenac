package com.senac.ApiEduPlataformSenac.model.repository;

import com.senac.ApiEduPlataformSenac.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
