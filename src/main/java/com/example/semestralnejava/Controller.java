package com.example.semestralnejava;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    @FXML
    void initialize() {
        //button for log in
        loginSignUpButton.setOnAction(event->{
            loginSignUpButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("signUp.fxml"));
            tryCatchLoadNewWindow(loader);

        });

        //button for sign in
      authSignInButton.setOnAction(event ->{
          String loginText = loginField.getText().trim();
          String loginPassword = passwordField.getText().trim();

          if (!loginText.isEmpty() && !loginPassword.isEmpty()){
              loginUser(loginText, loginPassword);
          }
          else System.out.println("Login or Pass is Empty");


          authSignInButton.getScene().getWindow().hide();
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("app.fxml"));
          tryCatchLoadNewWindow(loader);
      });

    }

    private void loginUser(String loginText, String loginPassword) {


    }

    private void tryCatchLoadNewWindow(FXMLLoader loader) {
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

}

