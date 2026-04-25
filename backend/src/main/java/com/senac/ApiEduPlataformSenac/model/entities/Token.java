package com.senac.ApiEduPlataformSenac.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "token")
public class Token {
    @Id
    private Long id;

    private String token;

    @ManyToOne
    private Usuario usuario;

    public Token(String token, Usuario usuario){
        this.token   = token;
        this.usuario = usuario;
    }
}
