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
    DataBaseHandler dataBaseHandler = new DataBaseHandler();
    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        imageButtonLogInSvg.setOnMouseClicked(event->{
            buttonsImages.buttonReturnToLogInPressed(librarynewgen);
        });
        imageButtonHomeSvg.setOnMouseClicked(event ->{
            buttonsImages.buttonHomePressed(librarynewgen);
        });
        countBooksLabel.setText(String.valueOf(dataBaseHandler.getCountFromBooks()));
        countUserLabel.setText(String.valueOf(dataBaseHandler.getCountFromUsers()));
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