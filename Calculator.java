package com.example.demo11;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Calculator extends Application {

    private TextField display;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Calculator");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        display = new TextField();
        display.setPrefHeight(50);
        grid.add(display, 0, 0, 4, 1);

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "sqrt", "^", "clear", "convert"
        };

        int row = 1;
        int col = 0;
        for (String label : buttonLabels) {
            Button button = new Button(label);
            button.setPrefSize(50, 50);
            button.setOnAction(e -> handleButtonPress(button.getText()));
            grid.add(button, col, row);
            col++;
            if (col > 3) {
                col = 0;
                row++;
            }
        }

        Scene scene = new Scene(grid, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleButtonPress(String label) {
        switch (label) {
            case "clear":
                display.clear();
                break;
            case "=":
                calculateResult();
                break;
            case "sqrt":
                calculateSquareRoot();
                break;
            case "^":
                display.appendText("^");
                break;
            case "convert":
                convertUnits();
                break;
            default:
                display.appendText(label);
                break;
        }
    }

    private void calculateResult() {
        String expression = display.getText();
        try {
            if (expression.contains("^")) {
                String[] parts = expression.split("\\^");
                double base = Double.parseDouble(parts[0]);
                double exponent = Double.parseDouble(parts[1]);
                double result = Math.pow(base, exponent);
                display.setText(String.valueOf(result));
            } else {
                double result = eval(expression);
                display.setText(String.valueOf(result));
            }
        } catch (Exception e) {
            display.setText("Error");
        }
    }

    private void calculateSquareRoot() {
        try {
            double value = Double.parseDouble(display.getText());
            double result = Math.sqrt(value);
            display.setText(String.valueOf(result));
        } catch (Exception e) {
            display.setText("Error");
        }
    }

    private void convertUnits() {
        // Simple unit conversion example: cm to inches
        try {
            double value = Double.parseDouble(display.getText());
            double result = value / 2.54; // Convert cm to inches
            display.setText(String.valueOf(result));
        } catch (Exception e) {
            display.setText("Error");
        }
    }

    private double eval(String expression) {
        String[] tokens = expression.split(" ");
        double operand1 = Double.parseDouble(tokens[0]);
        String operator = tokens[1];
        double operand2 = Double.parseDouble(tokens[2]);

        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                return operand1 / operand2;
            default:
                throw new UnsupportedOperationException("Unknown operator: " + operator);
        }
    }
}
