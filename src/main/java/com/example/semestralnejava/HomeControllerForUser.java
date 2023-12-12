package com.example.semestralnejava;

import java.net.URL;
import java.util.ResourceBundle;

import AllClasses.buttonsImages;
import database.DataBaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class HomeControllerForUser {

    @FXML
    private ResourceBundle resources;

    @FXML
    private ImageView imageButtonLogIn;
    @FXML
    private URL location;

    @FXML
    private Button myLibraryBtn;

    @FXML
    private ImageView imageButtonHome;

    @FXML
    private Button showAllBookBtn;
    DataBaseHandler dataBaseHandler = new DataBaseHandler();
    @FXML
    void initialize() {
        imageButtonLogIn.setOnMouseClicked(event->{
            buttonsImages.buttonReturnToLogInPressed(imageButtonHome);
        });
        imageButtonHome.setOnMouseClicked(event ->{
            buttonsImages.buttonHomePressed(imageButtonHome);
        });

        showAllBookBtn.setOnAction(event->{
            if (Controller.authorizedUser.getUsername().equals("admin"))
            WindowManager.showWindow("LibraryBooksApp.fxml", showAllBookBtn);
            else WindowManager.showWindow("LibraryBooksAppForUser.fxml", showAllBookBtn);
            //  dataBaseHandler.refreshBooksId();
        });
        myLibraryBtn.setOnAction(event ->{
            WindowManager.showWindow("myLibrary.fxml", showAllBookBtn);
        });

    }

}
