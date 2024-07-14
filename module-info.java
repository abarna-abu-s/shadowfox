module com.example.contactmanagement {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.contactmanagement to javafx.fxml;
    exports com.example.contactmanagement;
}