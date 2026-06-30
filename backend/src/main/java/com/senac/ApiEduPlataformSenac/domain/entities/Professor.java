package com.senac.ApiEduPlataformSenac.domain.entities;

import com.senac.ApiEduPlataformSenac.domain.valueobjects.CPF;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Embedded
    private CPF cpf;

    private String biografia;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
