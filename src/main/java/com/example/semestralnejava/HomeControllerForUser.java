package com.example.semestralnejava;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import AllClasses.buttonsImages;
import database.DataBaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.SVGPath;

import static animations.Hover.hoverIcons;

public class HomeControllerForUser {
    @FXML
    private SVGPath imageButtonHomeSvg;

    @FXML
    private Label librarynewgen;

    @FXML
    private SVGPath imageButtonLogInSvg;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button myLibraryBtn;

    @FXML
    private Label countBooksLabel;

    @FXML
    private Label countUserLabel;

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

        // Event handler for the "Home" button
        imageButtonHomeSvg.setOnMouseClicked(event -> {
            buttonsImages.buttonHomePressed(librarynewgen);
        });

        // Display the count of books and users in the labels
        countBooksLabel.setText(String.valueOf(dataBaseHandler.getCountFromBooks()));
        countUserLabel.setText(String.valueOf(dataBaseHandler.getCountFromUsers()));

        // Event handler for the "Show All Books" button
        showAllBookBtn.setOnAction(event -> {
            // Show different windows based on the user's role
            if (Controller.authorizedUser.getUsername().equals("admin")) {
                WindowManager.showWindow("LibraryBooksApp.fxml", showAllBookBtn);
            } else {
                WindowManager.showWindow("LibraryBooksAppForUser.fxml", showAllBookBtn);
            }
            //  dataBaseHandler.refreshBooksId();
        });

        // Event handler for the "My Library" button
        myLibraryBtn.setOnAction(event -> {
            WindowManager.showWindow("myLibrary.fxml", showAllBookBtn);
        });
    }
}
