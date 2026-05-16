package com.example.desktop.utils;

import javafx.scene.control.Alert;

public class ShowMessage {
    public static void showMessage(String mensagem, boolean tipoErro){
        Alert.AlertType tipoAlert = tipoErro ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION;
        Alert alert = new Alert(tipoAlert);
        alert.setTitle("Login");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
