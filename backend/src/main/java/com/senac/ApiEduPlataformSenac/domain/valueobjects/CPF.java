package com.senac.ApiEduPlataformSenac.domain.valueobjects;

public class CPF {

    private String cpf;

    public CPF() {
        this.cpf = "";
    }

    public CPF(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("CPF_NAO_INFORMADO");
        }

        String cpfTratado = cpf.replaceAll("[^0-9]", "");

        if (!isValid(cpfTratado)) {
            throw new IllegalArgumentException("CPF_INVALIDO");
        }

        this.cpf = cpfTratado;
    }

    private boolean isValid(String cpfTratado) {
        if (cpfTratado.length() != 11 || cpfTratado.matches("(\\d)\\1{10}")) {
            return false;
        }

        return validarDigitosVerificadores(cpfTratado);
    }

    private boolean validarDigitosVerificadores(String cpfTratado) {
        for (int j = 9; j < 11; j++) {
            int soma = 0;
            int peso = j + 1;

            for (int i = 0; i < j; i++) {
                soma += Character.getNumericValue(cpfTratado.charAt(i)) * peso;
                peso--;
            }

            int resto = soma % 11;
            char digito = (resto < 2) ? '0' : (char) (11 - resto + '0');

            if (digito != cpfTratado.charAt(j)) {
                return false;
            }
        }

        return true;
    }

    public String getNumeros() {
        return this.cpf;
    }

    @Override
    public String toString() {
        if (cpf == null || cpf.isBlank()) {
            return "";
        }

        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
}
