package com.example.semestralnejava;


import java.net.URL;
import java.util.ResourceBundle;

import database.DataBaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signUpButton;

    @FXML
    private CheckBox signUpCheckBoxFemale;

    @FXML
    private CheckBox signUpCheckBoxMale;

    @FXML
    private TextField signUpEmail;

    @FXML
    private TextField signUpLastName;

    @FXML
    private TextField signUpName;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private TextField signUpUserName;

    @FXML
    void initialize() {
        DataBaseHandler dbHandler = new DataBaseHandler();

        signUpButton.setOnAction(event ->{
             dbHandler.signUpUser(signUpName.getText(), signUpLastName.getText(), signUpUserName.getText(), signUpPassword.getText(), signUpEmail.getText(), "Male");
        });
    }

}
