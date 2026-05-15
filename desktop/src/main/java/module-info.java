module com.example.desktop {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.desktop to javafx.fxml;
    exports com.example.desktop;
}