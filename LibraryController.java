package com.example.librarymanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


public class LibraryController {
    private VBox view;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registerButton;
    private TextField titleField;
    private TextField authorField;
    private TextField isbnField;
    private Button addBookButton;
    private ListView<Book> bookListView;
    private ObservableList<Book> books;

    public LibraryController() {
        DatabaseHelper.initializeDatabase();
        initializeView();
    }

    private void initializeView() {
        view = new VBox(10);
        view.setPadding(new Insets(10));

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        loginButton = new Button("Login");
        registerButton = new Button("Register");

        loginButton.setOnAction(e -> login());
        registerButton.setOnAction(e -> register());

        VBox loginBox = new VBox(5, usernameField, passwordField, loginButton, registerButton);
        loginBox.setPadding(new Insets(10));
        loginBox.setStyle("-fx-border-color: gray; -fx-border-radius: 5; -fx-border-width: 1;");

        titleField = new TextField();
        titleField.setPromptText("Title");
        authorField = new TextField();
        authorField.setPromptText("Author");
        isbnField = new TextField();
        isbnField.setPromptText("ISBN");
        addBookButton = new Button("Add Book");

        addBookButton.setOnAction(e -> addBook());

        books = FXCollections.observableArrayList(DatabaseHelper.getAllBooks());
        bookListView = new ListView<>(books);

        VBox bookBox = new VBox(5, titleField, authorField, isbnField, addBookButton, bookListView);
        bookBox.setPadding(new Insets(10));
        bookBox.setStyle("-fx-border-color: gray; -fx-border-radius: 5; -fx-border-width: 1;");

        view.getChildren().addAll(loginBox, bookBox);
    }

    public VBox getView() {
        return view;
    }

    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = DatabaseHelper.getUser(username, password);
        if (user != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Login Successful", ButtonType.OK);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Credentials", ButtonType.OK);
            alert.show();
        }
    }

    private void register() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        DatabaseHelper.addUser(username, password);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Registration Successful", ButtonType.OK);
        alert.show();
    }

    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();
        DatabaseHelper.addBook(title, author, isbn);
        books.setAll(DatabaseHelper.getAllBooks());
        titleField.clear();
        authorField.clear();
        isbnField.clear();
    }
}
