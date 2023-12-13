package com.example.semestralnejava;

import java.net.URL;
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
    DataBaseHandler dataBaseHandler = new DataBaseHandler();
    @FXML
    void initialize() {
      imageButtonLogInSvg.setOnMouseClicked(event->{
            buttonsImages.buttonReturnToLogInPressed(librarynewgen);
        });
        imageButtonHomeSvg.setOnMouseClicked(event ->{
            buttonsImages.buttonHomePressed(librarynewgen);
        });
        addNewBookBtn.setOnAction(event ->{
            System.out.println("qwerty");
            WindowManager.showWindow("addNewBookApp.fxml", addNewBookBtn);
        });
        showAllBookBtn.setOnAction(event->{
            WindowManager.showWindow("LibraryBooksApp.fxml", showAllBookBtn);
            //  dataBaseHandler.refreshBooksId();
        });
        myLibraryBtn.setOnAction(event ->{
            WindowManager.showWindow("myLibrary.fxml", showAllBookBtn);
        });

    }

}
