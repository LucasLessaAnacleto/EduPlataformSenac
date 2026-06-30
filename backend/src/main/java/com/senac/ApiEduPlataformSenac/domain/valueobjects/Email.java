package com.senac.ApiEduPlataformSenac.domain.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public class Email {

    private String email;

    public Email() {
        this.email = "";
    }

    public Email(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("EMAIL_OBRIGATORIO");
        }

        if (!isValid(email)) {
            throw new IllegalArgumentException("EMAIL_INVALIDO");
        }

        this.email = email.trim().toLowerCase();
    }

    private boolean isValid(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public String getValor() {
        return this.email;
    }

    @Override
    public String toString() {
        return this.email;
    }
}
