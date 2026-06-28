package com.senac.ApiEduPlataformSenac.model.dto;

public record ModuloResponse(
        Long id,
        String titulo,
        Integer ordem,
        Long cursoId
) {
}