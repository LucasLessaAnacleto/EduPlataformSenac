package com.senac.ApiEduPlataformSenac.model.entities;

import com.senac.ApiEduPlataformSenac.model.enuns.EnumStatusUsuario;
import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    public EnumStatusUsuario getStatus() {
        return status;
    }

    public void setStatus(EnumStatusUsuario status) {
        this.status = status;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String senha;

    private EnumStatusUsuario status = EnumStatusUsuario.ATIVO;
}
