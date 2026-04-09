package com.senac.ApiEduPlataformSenac.model.dto;

public record MatriculaRequest(
        String nomeAluno,
        String emailAluno,
        Long cursoId
) {}