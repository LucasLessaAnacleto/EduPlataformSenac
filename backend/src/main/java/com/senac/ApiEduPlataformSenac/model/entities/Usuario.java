package com.senac.ApiEduPlataformSenac.model.entities;

import com.senac.ApiEduPlataformSenac.model.enuns.EnumStatusUsuario;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String senha;

    private String role;

    private EnumStatusUsuario status = EnumStatusUsuario.ATIVO;
}