package com.example.semestralnejava;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import AllClasses.Book;
import AllClasses.User;
import AllClasses.buttonsImages;
import database.DataBaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class AllUsersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<User, String> columnEmailUser;

    @FXML
    private TableColumn<User, String> columnGenderUser;

    @FXML
    private TableColumn<User, Integer> columnIdUser;

    @FXML
    private TableColumn<User, String> columnLastNameUser;

    @FXML
    private TableColumn<User, String> columnNameUser;

    @FXML
    private TableColumn<User, String> columnPasswordUser;

    @FXML
    private TableColumn<User, String> columnUsernameUser;

    @FXML
    private Button deleteUserbtn;

    @FXML
    private TableView<User> fullTable;

    @FXML
    private Label hideLabel;

    @FXML
    private SVGPath imageButtonHomeSvg;

    @FXML
    private Label librarynewgen;

    @FXML
    private TextField userIdWchichYouWantTextField;
    DataBaseHandler dataBaseHandler = new DataBaseHandler();
    private final ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        imageButtonHomeSvg.setOnMouseClicked(event ->{
            buttonsImages.buttonHomePressed(librarynewgen);
        });
        addBooksInList();
        deleteUserbtn.setOnAction(event->{
            try {
                deleteUser();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }


    private void deleteUser() throws SQLException, ClassNotFoundException {
        int userIdWhichYouWant = Integer.parseInt(userIdWchichYouWantTextField.getText().trim()) ;
        //Book book = bookList.get(idBookWhichYouWant);
        System.out.println(userIdWhichYouWant);
        if (Controller.authorizedUser.getUserId() != userIdWhichYouWant) {
            if ( !dataBaseHandler.posibilityDeleteUser(userIdWhichYouWant)) {
                if (dataBaseHandler.deleteRowFromUsersTable(userIdWhichYouWant) ) {

                    hideLabel.setText("Users deleted susccseful");
                    hideLabel.setTextFill(Color.GREEN);
                    fullTable.getItems().clear();
                    addBooksInList();
                    fullTable.refresh();
                }
                else{

                    hideLabel.setText("Enter real id");
                    hideLabel.setTextFill(Color.RED);
                }
            } else if (dataBaseHandler.posibilityDeleteUser(userIdWhichYouWant)) {
                hideLabel.setText("This user have a book/s in library");
                hideLabel.setTextFill(Color.RED);

            }
        }
        else {
            hideLabel.setText("You can`t delete yourself");
            hideLabel.setTextFill(Color.RED);
        }
    }
    private void addBooksInList() throws SQLException, ClassNotFoundException {
        columnIdUser.setCellValueFactory(new PropertyValueFactory<User, Integer>("userId"));
        columnNameUser.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        columnLastNameUser.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        columnUsernameUser.setCellValueFactory(new PropertyValueFactory<User, String>("username"));


        columnGenderUser.setCellValueFactory(new PropertyValueFactory<User, String>("gender"));
        columnPasswordUser.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        columnEmailUser.setCellValueFactory(new PropertyValueFactory<User, String>("email"));

        ResultSet users = dataBaseHandler.getAllUsers();
        System.out.println(users);

        while (users.next()) {


            User user = new User(users.getInt(1),
                    users.getString(2),
                    users.getString(3),
                    users.getString(4),
                    users.getString(5),
                    users.getString(6),
                    users.getString(7));
            userList.add(user);
            fullTable.setItems(userList);
        }
    }

}
