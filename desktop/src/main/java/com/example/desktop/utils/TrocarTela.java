package com.example.desktop.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class TrocarTela {

    public static void trocarTela(String fxmlView, Class<?> classTela, ActionEvent event) throws IOException {
        String view = fxmlView;

        if (!view.endsWith(".fxml")) {
            view = view + ".fxml";
        }

        URL caminho = TrocarTela.class.getResource("/com/example/desktop/" + view);

        FXMLLoader loader = new FXMLLoader(caminho);
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}