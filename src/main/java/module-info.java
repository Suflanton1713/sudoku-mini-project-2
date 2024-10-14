module com.example.sudokuminiproject2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sudokuminiproject2 to javafx.fxml;
    exports com.example.sudokuminiproject2;
}