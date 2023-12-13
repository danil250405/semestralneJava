package com.example.semestralnejava;

import database.DataBaseHandler;
import AllClasses.Book;
import AllClasses.buttonsImages;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.ResourceBundle;

public class AddNewBookController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addNewBookInTableBtn;

    @FXML
    private TextField bookAuthorTextField;

    @FXML
    private TextField bookNameTextField;

    @FXML
    private TextField bookYearTextField;

    @FXML
    private SVGPath imageButtonHomeSvg;

    @FXML
    private Label labelCheckFullFields;

    @FXML
    private Label librarynewgen;

    @FXML
    void initialize() {
        // Event handler for the "Home" button
        imageButtonHomeSvg.setOnMouseClicked(event -> {
            // Creating an instance of the database handler
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            // Calling a method to change the image when the "Home" button is pressed
            buttonsImages.buttonHomePressed(librarynewgen);
        });

        // Allowing only numbers in the "Year" field
        bookYearTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                bookYearTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Event handler for the "Add New Book" button
        addNewBookInTableBtn.setOnAction(event -> {
            // Calling a method to add a new book
            pushNewBook();
        });
    }

    // Method to add a new book
    private void pushNewBook() {
        // Creating an instance of the database handler
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        // Getting data from text fields
        String bookName = bookNameTextField.getText();
        String bookAuthor = bookAuthorTextField.getText();
        String bookYearText = bookYearTextField.getText();

        // Checking if all fields are filled
        if (bookName.isEmpty() || bookAuthor.isEmpty() || bookYearText.isEmpty()) {
            labelCheckFullFields.setTextFill(Color.RED);
            labelCheckFullFields.setText("Fill in all the fields");
        } else {
            // Attempting to parse the text in the "Year" field to an integer
            int bookYear = 0;
            try {
                bookYear = Integer.parseInt(bookYearText);
                System.out.println("good");
            } catch (NumberFormatException e) {
                // Handling the case where the input in the "Year" field is not a valid integer
                labelCheckFullFields.setText("Invalid year format");
            }
            // Creating a book object
            Book book = new Book(bookName, bookAuthor, bookYear, 0);
            // Adding a new book to the database
            dataBaseHandler.addNewBook(book);
            labelCheckFullFields.setTextFill(Color.GREEN);
            labelCheckFullFields.setText("Book " + bookName + " added successfully");
        }
    }
}
