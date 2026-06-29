package com.senac.ApiEduPlataformSenac.application.DTO;

import com.senac.ApiEduPlataformSenac.domain.enuns.EnumStatusUsuario;

public record UsuarioRequest(String email, String senha, EnumStatusUsuario status) {
}
