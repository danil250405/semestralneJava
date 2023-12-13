package com.example.semestralnejava;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import AllClasses.User;
import animations.Shake;
import database.DataBaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button authSignInButton;

    @FXML
    private TextField loginField;

    @FXML
    private Button loginSignUpButton;

    // Static variable to store the authorized user across different instances
    public static User authorizedUser = new User();

    @FXML
    private Label librarynewgen;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        // Event handler for the "Sign Up" button
        loginSignUpButton.setOnAction(event -> {
            // Show the sign-up window when the button is clicked
            WindowManager.showWindow("signUp.fxml", loginSignUpButton);
        });

        // Event handler for the "Sign In" button
        authSignInButton.setOnAction(event -> {
            String loginText = loginField.getText().trim();
            String loginPassword = passwordField.getText().trim();

            if (!loginText.isEmpty() && !loginPassword.isEmpty()) {
                try {
                    // Attempt to log in the user
                    authorizedUser = loginUser(loginText, loginPassword);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("Login or Password is Empty");
            }
        });

        // Print the email of the authorized user to the console
        System.out.println(authorizedUser.getEmail());
    }

    // Method to log in the user
    private User loginUser(String loginText, String loginPassword) throws SQLException, ClassNotFoundException {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        User authorizedUser = new User();
        authorizedUser.setUsername(loginText);
        authorizedUser.setPassword(loginPassword);
        ResultSet result = dataBaseHandler.getUser();

        int counter = 0;
        while (result.next()) {
            if (result.getString(4).equals(authorizedUser.getUsername()) && result.getString(5).equals(authorizedUser.getPassword())) {

                authorizedUser.setUserId(result.getInt(1));
                authorizedUser.setFirstName(result.getString(2));
                authorizedUser.setLastName(result.getString(3));
                authorizedUser.setEmail(result.getString(6));
                authorizedUser.setGender(result.getString(7));
                counter++;
                System.out.println(authorizedUser.getUserId());
                break;
            }
        }
        if (counter == 1) {
            System.out.println("Successful");
            // Show the appropriate window based on the user's role
            if (authorizedUser.getUsername().equals("admin")) {
                WindowManager.showWindow("app.fxml", authSignInButton);
            } else {
                WindowManager.showWindow("appForUser.fxml", authSignInButton);
            }
        } else {
            // Play shake animation for login and password fields if login is unsuccessful
            Shake userLoginAnim = new Shake(loginField);
            Shake passAnim = new Shake(passwordField);
            passAnim.playAnim();
            userLoginAnim.playAnim();
        }

        return authorizedUser;
    }
}
