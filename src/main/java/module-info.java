module com.example.sudokuminiproject2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.management;
    requires java.desktop;
    opens com.example.sudokuminiproject2 to javafx.fxml;
    opens com.example.sudokuminiproject2.controller to javafx.fxml;
    exports com.example.sudokuminiproject2;
}