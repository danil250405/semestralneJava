package com.example.semestralnejava.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import AllClasses.User;
import database.DataBaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ToggleGroup gender;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField signUpEmail;

    @FXML
    private TextField signUpLastName;

    @FXML
    private TextField signUpName;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private RadioButton signUpRadioBtnFemale;

    @FXML
    private RadioButton signUpRadioBtnMale;

    @FXML
    private TextField signUpUserName;

    @FXML
    void initialize() {


        signUpButton.setOnAction(event ->{
            signUpNewUser();
        });
    }

    private void signUpNewUser() {
        DataBaseHandler dbHandler = new DataBaseHandler();
        String firstName = signUpName.getText();
        String  lastName = signUpLastName.getText();
        String  username = signUpUserName.getText();
        String password = signUpPassword.getText();
        String email = signUpEmail.getText();
        String gender;
        if (signUpRadioBtnMale.isSelected())
            gender = "Male";
        else if (signUpRadioBtnFemale.isSelected())
            gender = "Female";
        else gender = "Gay";

        User user = new User(firstName,lastName,username,password,email,gender);
        dbHandler.signUpUser(user);
    }

}
