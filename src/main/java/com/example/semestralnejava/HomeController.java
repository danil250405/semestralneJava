package com.example.semestralnejava;

import java.net.URL;
import java.util.ResourceBundle;

import AllClasses.buttonHome;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class HomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addNewBookBtn;

    @FXML
    private Button deleteBookBtn;

    @FXML
    private ImageView imageButtonHome;

    @FXML
    private Button showAllBookBtn;

    @FXML
    void initialize() {

        imageButtonHome.setOnMouseClicked(event ->{
            buttonHome.buttonHomePressed(imageButtonHome);
        });
        addNewBookBtn.setOnAction(event ->{
            System.out.println("qwerty");
            WindowManager.showWindow("addNewBookApp.fxml", addNewBookBtn);
        });
        showAllBookBtn.setOnAction(event->{
            WindowManager.showWindow("LibraryBooksApp.fxml", showAllBookBtn);
        });

    }

}
