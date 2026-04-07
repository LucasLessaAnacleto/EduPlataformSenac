package com.senac.ApiEduPlataformSenac.model.entities;

import com.senac.ApiEduPlataformSenac.model.enuns.EnumStatusUsuario;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String senha;

    private EnumStatusUsuario status = EnumStatusUsuario.ATIVO;
}