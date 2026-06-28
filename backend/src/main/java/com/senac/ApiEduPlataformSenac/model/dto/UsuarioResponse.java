package com.senac.ApiEduPlataformSenac.model.dto;

import com.senac.ApiEduPlataformSenac.model.enuns.EnumStatusUsuario;

public record UsuarioResponse(Long id, String email, EnumStatusUsuario status, String role) {
}
