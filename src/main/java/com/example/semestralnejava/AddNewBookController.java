package com.example.semestralnejava;

import database.DataBaseHandler;
import AllClasses.Book;
import AllClasses.buttonHome;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

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
    private ImageView imageButtonHome;

    @FXML
    private Label labelCheckFullFields;

    @FXML
    void initialize() {


        imageButtonHome.setOnMouseClicked(event ->{
            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            buttonHome.buttonHomePressed(imageButtonHome);

        });
                //allow for user only number in field year
        bookYearTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                bookYearTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        addNewBookInTableBtn.setOnAction(event ->{
            pushNewBook();
        });

    }

    private void pushNewBook() {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        String bookName = bookNameTextField.getText();
        String bookAuthor = bookAuthorTextField.getText();
        String bookYearText = bookYearTextField.getText();

        if (bookName.isEmpty() || bookAuthor.isEmpty() || bookYearText.isEmpty()) {
            labelCheckFullFields.setTextFill(Color.RED);
            labelCheckFullFields.setText("Fill in all the fields");
        } else {
            int bookYear = 0;
            try {
                bookYear = Integer.parseInt(bookYearText);
                System.out.println("good");
            } catch (NumberFormatException e) {
                // Handle the case where the input in bookYearTextField is not a valid integer
                labelCheckFullFields.setText("Invalid year format");
            }
            Book book = new Book(bookName, bookAuthor, bookYear, 0);
            dataBaseHandler.addNewBook(book);
            labelCheckFullFields.setTextFill(Color.GREEN);
            labelCheckFullFields.setText("Book " + bookName + " added successful");


        }
    }

}
