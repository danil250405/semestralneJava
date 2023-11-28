package com.example.semestralnejava;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import AllClasses.Book;
import AllClasses.buttonHome;
import database.DataBaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class LibraryBooksController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TableView<Book> fullTable;

    @FXML
    private TableColumn<Book, String> columnLocationBook;

    @FXML
    private Button addBookInYourLibraryBtn;

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
    private ImageView imageButtonHome;
    DataBaseHandler dataBaseHandler = new DataBaseHandler();
    private final ObservableList<Book> bookList = FXCollections.observableArrayList();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        imageButtonHome.setOnMouseClicked(event ->{
            buttonHome.buttonHomePressed(imageButtonHome);
        });
        addBooksInList();



    }

    private void addBooksInList() throws SQLException, ClassNotFoundException {
        columnIdBook.setCellValueFactory(new PropertyValueFactory<Book, Integer>("bookId"));
        columnNameBook.setCellValueFactory(new PropertyValueFactory<Book, String>("bookName"));
        columnAuthorBook.setCellValueFactory(new PropertyValueFactory<Book, String>("bookAuthor"));
        columnYearBook.setCellValueFactory(new PropertyValueFactory<Book, Integer>("bookYear"));
        columnLocationBook.setCellValueFactory(new PropertyValueFactory<Book, String>("location"));
        ResultSet books = dataBaseHandler.getAllBooks();
        String location;
        while (books.next()) {
            if (books.getInt(5) == 0) location = "In library";
            else location = "Not in library";

            Book book = new Book(books.getInt(1),
                    books.getString(2),
                    books.getString(3),
                    books.getInt(4),
                    location);
            bookList.add(book);
            fullTable.setItems(bookList);
        }
    }

}
