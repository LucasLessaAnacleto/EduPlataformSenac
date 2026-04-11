package com.senac.ApiEduPlataformSenac.config;

import com.senac.ApiEduPlataformSenac.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        // liberação de metodos
        if(path.equals("/auth/login")
            || path.startsWith("/swagger-ui")
            || path.startsWith("/v3/api-docs")
            || path.startsWith("/webjars")
            || path.startsWith("/swagger-resources"))
        {
            filterChain.doFilter(request,response);
            return;
        }

        String header = request.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer ")){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token não informado ou invalido");
            return;
        } // verificar se vai dar erro na ordem

        String token = header.replace("Bearer ","");
        var returnToken = tokenService.validarToken(token);

        String username = returnToken.getSubject();

        System.out.println("Username: "+username);


        filterChain.doFilter(request, response);
    }
}
