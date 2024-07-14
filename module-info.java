module com.example.librarymanagement {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.librarymanagement to javafx.fxml;
    exports com.example.librarymanagement;
}