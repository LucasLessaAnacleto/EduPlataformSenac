package com.senac.ApiEduPlataformSenac.application.DTO;

import com.senac.ApiEduPlataformSenac.domain.enuns.EnumStatusUsuario;

public record UsuarioResponse(Long id, String email, EnumStatusUsuario status, String role) {
}
