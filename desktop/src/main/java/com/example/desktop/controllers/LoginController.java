package com.example.desktop.controllers;

import com.example.desktop.utils.ShowMessage;
import com.example.desktop.utils.TrocarTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private void onLoginButtonClick(ActionEvent event) throws IOException {

        if (txtLogin.getText().equals("admin") && txtSenha.getText().equals("12345")) {
            ShowMessage.showMessage("Login efetuado.", false);
            TrocarTela.trocarTela("menu-view", getClass(), event);
            return;
        }

        ShowMessage.showMessage("Usuário e senha inválidos.", true);
    }
}