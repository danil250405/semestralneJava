package com.example.semestralnejava;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import AllClasses.buttonsImages;
import database.DataBaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;

public class HomeController {
    @FXML
    private Button allUsersBtn;

    @FXML
    private Label countBooksLabel;

    @FXML
    private Label countUserLabel;

    @FXML
    private AnchorPane side_ankerpane;

    @FXML
    private ResourceBundle resources;

    @FXML
    private SVGPath imageButtonLogInSvg;

    @FXML
    private URL location;

    @FXML
    private Button addNewBookBtn;

    @FXML
    private Button myLibraryBtn;

    @FXML
    private Label librarynewgen;

    @FXML
    private SVGPath imageButtonHomeSvg;

    @FXML
    private Button showAllBookBtn;

    // Database handler instance
    DataBaseHandler dataBaseHandler = new DataBaseHandler();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        // Event handler for the "Log In" button
        imageButtonLogInSvg.setOnMouseClicked(event -> {
            buttonsImages.buttonReturnToLogInPressed(librarynewgen);
        });

        // Display the count of books and users in the labels
        countBooksLabel.setText(String.valueOf(dataBaseHandler.getCountFromBooks()));
        countUserLabel.setText(String.valueOf(dataBaseHandler.getCountFromUsers()));

        // Event handler for the "Home" button
        imageButtonHomeSvg.setOnMouseClicked(event -> {
            buttonsImages.buttonHomePressed(librarynewgen);
        });

        // Event handler for the "All Users" button
        allUsersBtn.setOnAction(event -> {
            WindowManager.showWindow("allUsers.fxml", addNewBookBtn);
        });

        // Event handler for the "Add New Book" button
        addNewBookBtn.setOnAction(event -> {
            System.out.println("qwerty");
            WindowManager.showWindow("addNewBookApp.fxml", addNewBookBtn);
        });

        // Event handler for the "Show All Books" button
        showAllBookBtn.setOnAction(event -> {
            WindowManager.showWindow("LibraryBooksApp.fxml", showAllBookBtn);
        });

        // Event handler for the "My Library" button
        myLibraryBtn.setOnAction(event -> {
            WindowManager.showWindow("myLibrary.fxml", showAllBookBtn);
        });
    }
}
