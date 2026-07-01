package com.example.desktop.controllers;

import com.example.desktop.utils.ShowMessage;
import com.example.desktop.utils.TrocarTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class UsuarioController {

    private static final String SECRET_KEY = "awjidauwdaaid87231dcahbsurnasxvcb34d";

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtSenha;
    @FXML
    private PasswordField txtConfirmarSenha;

    @FXML
    private void onVoltarButtonClick(ActionEvent event) throws IOException {
        TrocarTela.trocarTela("menu-view", getClass(), event);
    }

    @FXML
    private void onSalvarButtonClick(ActionEvent event) throws IOException {

        if (txtNome.getText().isBlank()) {
            ShowMessage.showMessage("Informe o nome.", true);
            return;
        }

        if (txtEmail.getText().isBlank()) {
            ShowMessage.showMessage("Informe o email.", true);
            return;
        }

        if (txtSenha.getText().isBlank()) {
            ShowMessage.showMessage("Informe a senha.", true);
            return;
        }

        if (txtConfirmarSenha.getText().isBlank()) {
            ShowMessage.showMessage("Confirme a senha.", true);
            return;
        }

        if (!txtSenha.getText().equals(txtConfirmarSenha.getText())) {
            ShowMessage.showMessage("As senhas não conferem.", true);
            return;
        }

        URL url = new URL("http://localhost:8080/usuarios/adm");

        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String json = "{"
                    + "\"email\":\"" + txtEmail.getText() + "\","
                    + "\"senha\":\"" + txtSenha.getText() + "\","
                    + "\"secretKey\":\"" + SECRET_KEY + "\""
                    + "}";

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }

            int code = conn.getResponseCode();

            if (code == 200) {
                ShowMessage.showMessage("Administrador criado com sucesso.", false);
                TrocarTela.trocarTela("menu-view", getClass(), event);
            } else {
                String erro = "";
                if (conn.getErrorStream() != null) {
                    erro = new String(conn.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
                    System.out.println(erro);
                }

                if (code == 400) {
                    ShowMessage.showMessage(erro.isBlank() ? "Erro ao cadastrar administrador." : erro, true);
                } else if (code == 403) {
                    ShowMessage.showMessage("Secret Key inválida.", true);
                } else {
                    ShowMessage.showMessage("Erro ao criar administrador.", true);
                }
            }

        } catch (java.net.ConnectException e) {
            ShowMessage.showMessage("Instabilidade no servidor. Tente novamente mais tarde.", true);
        }
    }
}