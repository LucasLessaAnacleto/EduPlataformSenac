package com.senac.ApiEduPlataformSenac.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${app.email.remetente}")
    private String remetente;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void enviarBoasVindas(String emailAluno, String nomeAluno, String tituloCurso) throws Exception {
        try {
            SimpleMailMessage mensagem = new SimpleMailMessage();

            mensagem.setFrom(remetente);
            mensagem.setTo(emailAluno);
            mensagem.setSubject("Bem-vindo ao curso " + tituloCurso);

            mensagem.setText(
                    "Olá, " + nomeAluno + "!\n\n" +
                            "Sua matrícula no curso \"" + tituloCurso + "\" foi realizada com sucesso.\n\n" +
                            "Seja bem-vindo à EduPlatform!\n\n" +
                            "Bons estudos!"
            );

            javaMailSender.send(mensagem);

        } catch (Exception e) {
            throw new Exception("EMAIL_NAO_ENVIADO");
        }
    }
}
