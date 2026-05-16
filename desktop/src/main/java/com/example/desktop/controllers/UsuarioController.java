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

public class UsuarioController {
    @FXML
    private TextField     txtNome;
    @FXML
    private TextField     txtEmail;
    @FXML
    private PasswordField txtSenha;


    @FXML
    private void onVoltarButtonClick(ActionEvent event) throws IOException  {
        TrocarTela.trocarTela("menu-view", getClass(), event);
    }

    @FXML
    private void onSalvarButtonClick(ActionEvent event) throws IOException {
        URL url = new URL("http://localhost:8080/usuarioAdmin");
        try{
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String json = "{"+
                    "\"nome\": \""+txtNome.getText()+"\"" +
                    "\"email\": \""+txtEmail.getText()+"\"" +
                    "\"senha\": \""+txtSenha.getText()+"\"" +
                    "}";

            conn.setDoOutput(true);

            try(OutputStream os = conn.getOutputStream()){
                os.write(json.getBytes());
            }

            var code = conn.getResponseCode();
            if(code == 200){
                TrocarTela.trocarTela("menu-view", getClass(), event);
            }
        } catch (java.net.ConnectException e) {
            System.out.println(e);
            ShowMessage.showMessage("Instabilidade no servidor. Tente novamente mais tarde.", true);
        }
    }
}
