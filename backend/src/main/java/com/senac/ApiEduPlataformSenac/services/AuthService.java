package com.senac.ApiEduPlataformSenac.services;

import com.senac.ApiEduPlataformSenac.model.dto.LoginRequest;
import com.senac.ApiEduPlataformSenac.model.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    public ProfessorRepository professorRepository;

    public boolean ValidaUsuarioSenha(LoginRequest loginRequest) {
        try{

            return professorRepository.existsProfessorByEmailAndSenha(loginRequest.email(), loginRequest.senha());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
