package com.senac.ApiEduPlataformSenac.application.DTO;

import java.time.LocalDate;

public record MatriculaResponse(
        Long id,
        String nomeAluno,
        String emailAluno,
        LocalDate data,
        Long cursoId
) {
}
