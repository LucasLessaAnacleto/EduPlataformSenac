module com.example.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.example.desktop to javafx.fxml;
    exports com.example.desktop;
    exports com.example.desktop.controllers;
    opens com.example.desktop.controllers to javafx.fxml;
}