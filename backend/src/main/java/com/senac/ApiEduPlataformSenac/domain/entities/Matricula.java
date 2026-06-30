package com.senac.ApiEduPlataformSenac.domain.entities;

import com.senac.ApiEduPlataformSenac.domain.valueobjects.Email;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "matricula")
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeAluno;

    @Embedded
    private Email emailAluno;
    private LocalDate data;

    @ManyToOne
    private Curso curso;

}
