package com.example.semestralnejava;

import AllClasses.User;

import database.DataBaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpController extends Controller  {

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
    private Label labelCheckFullFields;
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
        else gender = "";
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty() || gender.isEmpty()){
            labelCheckFullFields.setText("Fill in all the fields");
        }
        else {
            if (checkUsername(username)) {
                User user = new User(firstName, lastName, username, password, email, gender);
                dbHandler.signUpUser(user);
                WindowManager.showWindow("hello-view.fxml", signUpButton);

            }
            else {
                System.out.println("dinahui");

                signUpUserName.clear();
                usernameIsUsedTextField.setText("This username is used!!!");
            }
        }
    }
    private boolean checkUsername(String username){
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        User user = new User();
        user.setUsername(username);

        ResultSet result = dataBaseHandler.getUsername(user);
        int counter = 0;
        while (true){
            try {
                if (!result.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            counter++;
        }
        if(counter >=1){
            System.out.println("this username is used");
            return false;

        }
        else return true;
    }



}
