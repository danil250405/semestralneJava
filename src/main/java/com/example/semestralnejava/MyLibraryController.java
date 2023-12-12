package com.example.semestralnejava;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import AllClasses.Book;
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
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class MyLibraryController extends Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField bookIdWchichYouWantTextField;

    @FXML
    private TableColumn<Book, String> columnAuthorBook;

    @FXML
    private TableColumn<Book, Integer> columnIdBook;

    @FXML
    private TableColumn<Book, String> columnNameBook;

    @FXML
    private TableColumn<Book, Integer> columnYearBook;

    @FXML
    private Button deleteBookBtn;

    @FXML
    private TableView<Book> fullTable;

    @FXML
    private Label hideLabel;

    @FXML
    private ImageView imageButtonHome;
    DataBaseHandler dataBaseHandler = new DataBaseHandler();
    private final ObservableList<Book> bookList = FXCollections.observableArrayList();


    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        //System.out.println(getAuthorizedUser().getUserId());
        //DataBaseHandler dataBaseHandler = new DataBaseHandler();
        imageButtonHome.setOnMouseClicked(event ->{
            buttonsImages.buttonHomePressed(imageButtonHome);
        });
        addBooksInList();
deleteBookBtn.setOnAction(event->{
    try {
        deletebook();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    }
});
    }

    private void deletebook() throws SQLException, ClassNotFoundException {
        int idBookWhichYouWant = Integer.parseInt(bookIdWchichYouWantTextField.getText().trim());

        System.out.println(idBookWhichYouWant);

       // Book book = dataBaseHandler.getBookById(idBookWhichYouWant);
        if (  dataBaseHandler.deleteBookFromUserLibrary(idBookWhichYouWant)){
            hideLabel.setText("Book deleted susccseful");
            hideLabel.setTextFill(Color.GREEN);
            fullTable.getItems().clear();
            addBooksInList();
            fullTable.refresh();
        }

        else
        {

            hideLabel.setText("You can`t do it");
            hideLabel.setTextFill(Color.RED);
        }
    }

    private void addBooksInList() throws SQLException, ClassNotFoundException {
        columnIdBook.setCellValueFactory(new PropertyValueFactory<Book, Integer>("bookId"));
        columnNameBook.setCellValueFactory(new PropertyValueFactory<Book, String>("bookName"));
        columnAuthorBook.setCellValueFactory(new PropertyValueFactory<Book, String>("bookAuthor"));
        columnYearBook.setCellValueFactory(new PropertyValueFactory<Book, Integer>("bookYear"));

        System.out.println();
        ResultSet books = dataBaseHandler.getAllBooks();
        System.out.println(books);
        boolean opportunityToBorrowABook = false;
        String location;
        while (books.next()) {
            if (books.getInt(5) == 0) {
                opportunityToBorrowABook = true;
            }
            else {
                opportunityToBorrowABook = false;
            }
            if (authorizedUser.getUserId() == (books.getInt(5))) {
                Book book = new Book(books.getInt(1),
                        books.getString(2),
                        books.getString(3),
                        books.getInt(4),
                        books.getString(6),
                        opportunityToBorrowABook);

                bookList.add(book);
                fullTable.setItems(bookList);
            }
        }
    }
}
