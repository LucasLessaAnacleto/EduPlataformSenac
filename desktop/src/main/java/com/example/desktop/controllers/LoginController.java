package com.example.desktop.controllers;

import com.example.desktop.utils.ShowMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private void onLoginButtonClick(ActionEvent event) throws IOException {

        if (txtLogin.getText().equals("admin") && txtSenha.getText().equals("12345")){
            ShowMessage.showMessage("Login efetuado com email: "+txtLogin.getText(), false);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu-view"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            return;
        }
        ShowMessage.showMessage("Usuario e senha invalido.", true);
    }


}
