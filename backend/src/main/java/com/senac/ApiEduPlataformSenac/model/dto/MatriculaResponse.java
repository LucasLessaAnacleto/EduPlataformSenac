package com.senac.ApiEduPlataformSenac.model.dto;

import java.time.LocalDate;

public record MatriculaResponse(
        Long id,
        String nomeAluno,
        String emailAluno,
        LocalDate data,
        Long cursoId
) {
}
