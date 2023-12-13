package com.example.semestralnejava;

import AllClasses.User;

import AllClasses.buttonsImages;
import database.DataBaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private SVGPath imageButtonLogInSvg;

    // ToggleGroup for gender selection
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
    private Label usernameIsUsedTextField;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private RadioButton signUpRadioBtnFemale;

    @FXML
    private RadioButton signUpRadioBtnMale;

    @FXML
    private TextField signUpUserName;

    @FXML
    private Label librarynewgen;

    @FXML
    private Label labelCheckFullFields;

    // Static User instance to store user information during sign-up
    public static User user = new User();

    @FXML
    void initialize() {

        // Event handler for returning to the login view
        imageButtonLogInSvg.setOnMouseClicked(event->{
            buttonsImages.buttonReturnToLogInPressed(librarynewgen);
        });

        // Event handler for the sign-up button
        signUpButton.setOnAction(event ->{
            signUpNewUser();
        });
    }

    // Method to handle user sign-up
    private void signUpNewUser() {
        DataBaseHandler dbHandler = new DataBaseHandler();

        // Retrieve user input
        String firstName = signUpName.getText();
        String  lastName = signUpLastName.getText();
        String  username = signUpUserName.getText();
        String password = signUpPassword.getText();
        String email = signUpEmail.getText();
        String gender;

        // Determine the selected gender
        if (signUpRadioBtnMale.isSelected())
            gender = "Male";
        else if (signUpRadioBtnFemale.isSelected())
            gender = "Female";
        else gender = "";

        // Check if any field is empty
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty() || gender.isEmpty()){
            labelCheckFullFields.setText("Fill in all the fields");
        }
        else {
            // Check if the username is available
            if (checkUsername(username)) {
                // Create a new User object
                User user = new User(firstName, lastName, username, password, email, gender);

                // Sign up the user
                dbHandler.signUpUser(user);

                // Switch to the login view after successful sign-up
                WindowManager.showWindow("hello-view.fxml", signUpButton);
            }
            else {
                // Clear the username field and display an error message
                signUpUserName.clear();
                usernameIsUsedTextField.setText("This username is used!!!");
            }
        }
    }

    // Method to check if the username is available
    private boolean checkUsername(String username){
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        User user = new User();
        user.setUsername(username);

        ResultSet result = dataBaseHandler.getUsername(user);
        int counter = 0;

        // Count the number of rows returned from the database query
        while (true){
            try {
                if (!result.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            counter++;
        }

        // If there is at least one row, the username is already in use
        if(counter >= 1){
            System.out.println("this username is used");
            return false;
        }
        // If no rows are returned, the username is available
        else return true;
    }
}
