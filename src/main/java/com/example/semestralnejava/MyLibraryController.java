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
import javafx.scene.shape.SVGPath;

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
    private SVGPath imageButtonHomeSvg;

    // Database handler instance
    DataBaseHandler dataBaseHandler = new DataBaseHandler();

    // Observable list to store books for the table view
    private final ObservableList<Book> bookList = FXCollections.observableArrayList();

    @FXML
    private Label librarynewgen;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        System.out.println(Controller.authorizedUser.getUserId());

        // Event handler for the "Home" button
        imageButtonHomeSvg.setOnMouseClicked(event -> {
            buttonsImages.buttonHomePressed(librarynewgen);
        });

        // Populate the table with books from the user's library
        addBooksInList();

        // Event handler for the "Delete Book" button
        deleteBookBtn.setOnAction(event -> {
            try {
                deletebook();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Method to delete a book from the user's library
    private void deletebook() throws SQLException, ClassNotFoundException {
        int idBookWhichYouWant = Integer.parseInt(bookIdWchichYouWantTextField.getText().trim());

        System.out.println(idBookWhichYouWant);

        // Check if the book was successfully deleted
        if (dataBaseHandler.deleteBookFromUserLibrary(idBookWhichYouWant)) {
            hideLabel.setText("Book deleted successfully");
            hideLabel.setTextFill(Color.GREEN);

            // Clear the list and refresh the table
            fullTable.getItems().clear();
            addBooksInList();
            fullTable.refresh();
        } else {
            hideLabel.setText("You can't do it");
            hideLabel.setTextFill(Color.RED);
        }
    }

    // Method to add books from the user's library to the observable list for the table view
    private void addBooksInList() throws SQLException, ClassNotFoundException {
        columnIdBook.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        columnNameBook.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        columnAuthorBook.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
        columnYearBook.setCellValueFactory(new PropertyValueFactory<>("bookYear"));

        ResultSet books = dataBaseHandler.getAllBooks();
        boolean opportunityToBorrowABook;

        // Iterate through the ResultSet and add books to the list for the current user
        while (books.next()) {
            if (books.getInt(5) == 0) {
                opportunityToBorrowABook = true;
            } else {
                opportunityToBorrowABook = false;
            }

            // Check if the book belongs to the current user
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
