package com.senac.ApiEduPlataformSenac.util;

import com.senac.ApiEduPlataformSenac.model.entities.Usuario;
import com.senac.ApiEduPlataformSenac.model.enuns.EnumStatusUsuario;
import com.senac.ApiEduPlataformSenac.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TokenUtil {

    public static String generateToken(Long userId, String email) {

        long expiration = System.currentTimeMillis() + 1000 * 60 * 60;

        String token = userId + ":" + email + ":" + expiration;

        return Base64.getEncoder().encodeToString(token.getBytes(StandardCharsets.UTF_8));
    }

    public static Long validateToken(String token) {

        if (token == null) {
            throw new RuntimeException("Token não informado");
        }

        try {
            String decodeToken = new String(Base64.getDecoder().decode(token), StandardCharsets.UTF_8);

            String[] parts = decodeToken.split(":");

            if (parts.length < 3) {
                throw new RuntimeException("Token inválido");
            }

            Long userId = Long.parseLong(parts[0]);
            String email = parts[1];
            long expiration = Long.parseLong(parts[2]);

            if (System.currentTimeMillis() > expiration) {
                throw new RuntimeException("Token expirado");
            }

            // Usuario usuario = usuarioRepository.findByIdAndEmail(userId, email).orElse(null);

            return userId;

        } catch (Exception e) {
            throw new RuntimeException("Token inválido");
        }
    }
}