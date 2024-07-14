package com.example.contactmanagement;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ContactManagementSystem extends Application {

    private TextField nameField;
    private TextField phoneField;
    private TextField emailField;
    private ListView<Contact> contactListView;
    private ObservableList<Contact> contacts;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Contact Management System");

        // Initialize contact list
        contacts = FXCollections.observableArrayList();

        // Setup UI
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // Input fields
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setPadding(new Insets(10));

        Label nameLabel = new Label("Name:");
        nameField = new TextField();
        Label phoneLabel = new Label("Phone:");
        phoneField = new TextField();
        Label emailLabel = new Label("Email:");
        emailField = new TextField();

        inputGrid.add(nameLabel, 0, 0);
        inputGrid.add(nameField, 1, 0);
        inputGrid.add(phoneLabel, 0, 1);
        inputGrid.add(phoneField, 1, 1);
        inputGrid.add(emailLabel, 0, 2);
        inputGrid.add(emailField, 1, 2);

        // Buttons
        HBox buttonBox = new HBox(10);
        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        buttonBox.getChildren().addAll(addButton, updateButton, deleteButton);
        buttonBox.setAlignment(Pos.CENTER);

        // ListView
        contactListView = new ListView<>(contacts);
        contactListView.setPrefHeight(200);
        contactListView.setOnMouseClicked(event -> loadSelectedContact());

        root.getChildren().addAll(inputGrid, buttonBox, contactListView);

        // Button actions
        addButton.setOnAction(e -> addContact());
        updateButton.setOnAction(e -> updateContact());
        deleteButton.setOnAction(e -> deleteContact());

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addContact() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
            Contact contact = new Contact(name, phone, email);
            contacts.add(contact);
            clearFields();
        } else {
            showAlert("Error", "Please fill in all fields.");
        }
    }

    private void updateContact() {
        Contact selectedContact = contactListView.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();

            if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
                selectedContact.setName(name);
                selectedContact.setPhone(phone);
                selectedContact.setEmail(email);
                contactListView.refresh();
                clearFields();
            } else {
                showAlert("Error", "Please fill in all fields.");
            }
        } else {
            showAlert("Error", "No contact selected.");
        }
    }

    private void deleteContact() {
        Contact selectedContact = contactListView.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            contacts.remove(selectedContact);
            clearFields();
        } else {
            showAlert("Error", "No contact selected.");
        }
    }

    private void loadSelectedContact() {
        Contact selectedContact = contactListView.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            nameField.setText(selectedContact.getName());
            phoneField.setText(selectedContact.getPhone());
            emailField.setText(selectedContact.getEmail());
        }
    }

    private void clearFields() {
        nameField.clear();
        phoneField.clear();
        emailField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class Contact {
        private String name;
        private String phone;
        private String email;

        public Contact(String name, String phone, String email) {
            this.name = name;
            this.phone = phone;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return name + " (" + phone + ", " + email + ")";
        }
    }
}
