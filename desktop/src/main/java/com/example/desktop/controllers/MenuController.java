package com.example.desktop.controllers;

import com.example.desktop.utils.TrocarTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MenuController {
    @FXML
    private void onVoltarButtonClick(ActionEvent event) throws IOException {
        TrocarTela.trocarTela("login-view", getClass(), event);
    }

    @FXML
    private void onIrParaCadastroUsuario(ActionEvent event) throws IOException {
        TrocarTela.trocarTela("usuario-view", getClass(), event);
    }
}
