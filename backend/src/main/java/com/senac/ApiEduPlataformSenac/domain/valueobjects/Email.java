package com.senac.ApiEduPlataformSenac.domain.valueobjects;

public record Email(String valor) {

    public Email {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("EMAIL_OBRIGATORIO");
        }

        if (!valor.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("EMAIL_INVALIDO");
        }
    }
}
