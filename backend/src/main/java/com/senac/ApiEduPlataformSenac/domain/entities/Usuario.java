package com.senac.ApiEduPlataformSenac.domain.entities;

import com.senac.ApiEduPlataformSenac.application.DTO.UsuarioAdmRequest;
import com.senac.ApiEduPlataformSenac.application.DTO.UsuarioProfessorRequest;
import com.senac.ApiEduPlataformSenac.application.DTO.UsuarioRequest;
import com.senac.ApiEduPlataformSenac.domain.enuns.EnumStatusUsuario;
import com.senac.ApiEduPlataformSenac.domain.valueobjects.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(unique = true)
    private Email email;

    private String senha;

    private String role;

    private EnumStatusUsuario status = EnumStatusUsuario.ATIVO;

    public Usuario(UsuarioProfessorRequest usuario) {
        this.email = new Email(usuario.email());
        this.senha = usuario.senha();
        this.role = "ROLE_PROFESSOR";
    }

    public Usuario(UsuarioAdmRequest usuario) {
        this.email = new Email(usuario.email());
        this.senha = usuario.senha();
        this.role = "ROLE_ADMIN";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email != null ? this.email.toString() : "";
    }
}