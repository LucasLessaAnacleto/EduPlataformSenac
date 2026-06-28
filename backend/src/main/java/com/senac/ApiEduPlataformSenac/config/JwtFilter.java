package com.senac.ApiEduPlataformSenac.config;

import com.senac.ApiEduPlataformSenac.model.entities.Usuario;
import com.senac.ApiEduPlataformSenac.model.repository.UsuarioRepository;
import com.senac.ApiEduPlataformSenac.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Liberação de rotas para não travar o token JWT
        if (path.equals("/auth/login")
                || path.startsWith("/usuarios/adm")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/webjars")
                || path.startsWith("/swagger-resources")
                || path.startsWith("/v3/api-docs")
                || request.getMethod().equals("OPTIONS")) {

            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.replace("Bearer ", "");

            try {
                var decodedToken = tokenService.validarToken(token);

                if (decodedToken == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token inválido");
                    return;
                }

                String email = decodedToken.getSubject();

                Usuario usuarioLogado = usuarioRepository
                        .findByEmail(email)
                        .orElse(null);

                if (usuarioLogado == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Usuário do token não encontrado");
                    return;
                }

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                usuarioLogado,
                                null,
                                usuarioLogado.getAuthorities()
                        );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido ou expirado");
                return;
            }

        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token não informado ou inválido");
            return;
        }

        filterChain.doFilter(request, response);
    }
}