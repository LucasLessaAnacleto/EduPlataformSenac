package com.example.desktop.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TrocarTela {
    public static void trocarTela(String fmxlView, Class classTela, ActionEvent event) throws IOException {
        String view = fmxlView;
        if(!fmxlView.contains(".fxml")){
            view = view.concat(".fxml");
        }
        FXMLLoader loader = new FXMLLoader(classTela.getResource(view));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
    }
}
