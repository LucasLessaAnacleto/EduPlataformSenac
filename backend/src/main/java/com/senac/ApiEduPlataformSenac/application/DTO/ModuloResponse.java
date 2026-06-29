package com.senac.ApiEduPlataformSenac.application.DTO;

public record ModuloResponse(
        Long id,
        String titulo,
        Integer ordem,
        Long cursoId
) {
}