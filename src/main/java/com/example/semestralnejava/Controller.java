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
    public static User authorizedUser = new User();




    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        //button for log in
        loginSignUpButton.setOnAction(event->{
            WindowManager.showWindow("signUp.fxml" , loginSignUpButton);
        });

        //button for sign in
      authSignInButton.setOnAction(event ->{
          String loginText = loginField.getText().trim();
          String loginPassword = passwordField.getText().trim();

          if (!loginText.isEmpty() && !loginPassword.isEmpty()){
              try {
                  authorizedUser = loginUser(loginText, loginPassword);
              } catch (SQLException | ClassNotFoundException e) {
                  throw new RuntimeException(e);
              }
          }
          else System.out.println("Login or Pass is Empty");
      });
       System.out.println(authorizedUser.getEmail());
    }

    private User loginUser(String loginText, String loginPassword) throws SQLException, ClassNotFoundException {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        User authorizedUser = new User();
        authorizedUser.setUsername(loginText);
        authorizedUser.setPassword(loginPassword);
        ResultSet result = dataBaseHandler.getUser();

        int counter = 0;
        while (result.next()){
           if (result.getString(4).equals(authorizedUser.getUsername()) && result.getString(5).equals(authorizedUser.getPassword())){

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
        if(counter == 1){
            System.out.println("Successful");
            if (authorizedUser.getUsername().equals("admin"))
            WindowManager.showWindow("app.fxml", authSignInButton);
            else WindowManager.showWindow("appForUser.fxml", authSignInButton);
           // setAuthorizedUser(authorizedUser);
           // System.out.println(authorizedUser.getUsername());
            }
        else {
            Shake userLoginAnim = new Shake(loginField);
            Shake passAnim = new Shake(passwordField);
            passAnim.playAnim();
            userLoginAnim.playAnim();
        }

        return  authorizedUser;

    }



}

