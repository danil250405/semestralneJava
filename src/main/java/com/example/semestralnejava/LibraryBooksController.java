package com.example.semestralnejava;

import AllClasses.Book;
import AllClasses.buttonsImages;
import database.DataBaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LibraryBooksController extends Controller {

    @FXML
    private Label hideLabel;

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
    private Button deleteBookBtn;

    @FXML
    private TableColumn<Book, Integer> columnYearBook;

    @FXML
    private Label librarynewgen;

    @FXML
    private SVGPath imageButtonHomeSvg;

    // Database handler instance
    DataBaseHandler dataBaseHandler = new DataBaseHandler();

    // Observable list to store books for the table view
    private final ObservableList<Book> bookList = FXCollections.observableArrayList();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        // Event handler for the "Home" button
        imageButtonHomeSvg.setOnMouseClicked(event -> {
            buttonsImages.buttonHomePressed(librarynewgen);
        });

        // Populate the table with books
        addBooksInList();

        // Event handler for the "Add Book" button
        addBookInYourLibraryBtn.setOnAction(event -> {
            try {
                addBookInYourLibrary();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        // Event handler for the "Delete Book" button
        deleteBookBtn.setOnAction(event -> {
            try {
                deleteBook();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Method to delete a book
    private void deleteBook() throws SQLException, ClassNotFoundException {
        int idBookWhichYouWant = Integer.parseInt(bookIdWchichYouWantTextField.getText().trim());
        System.out.println(idBookWhichYouWant);

        // Check if the book was deleted successfully
        if (dataBaseHandler.deleteRowFromBooksTable(idBookWhichYouWant)) {
            hideLabel.setText("Book deleted successfully");
            hideLabel.setTextFill(Color.GREEN);

            // Clear the table and refresh it
            fullTable.getItems().clear();
            addBooksInList();
            fullTable.refresh();
        } else {
            hideLabel.setText("You can't delete it");
            hideLabel.setTextFill(Color.RED);
        }
    }

    // Method to add a book to the user's library
    private void addBookInYourLibrary() throws SQLException, ClassNotFoundException {
        int idBookWhichYouWant = Integer.parseInt(bookIdWchichYouWantTextField.getText().trim());
        Book book = dataBaseHandler.getBookById(idBookWhichYouWant);

        if (book.getIduser() == 0) {
            book.setOpportunityToBorrowABook(true);
        } else {
            book.setOpportunityToBorrowABook(false);
        }

        // Check if the book can be borrowed and is not null
        if (book.isOpportunityToBorrowABook() && book != null) {
            book.setIduser(authorizedUser.getUserId());
            book.setLocation("In " + authorizedUser.getUsername() + " library");
            dataBaseHandler.setNewUserIdForBook(book.getBookId(), book.getIduser(), book.getLocation());

            hideLabel.setText("You took this book");
            hideLabel.setTextFill(Color.GREEN);

            // Clear the list and refresh the table
            bookList.clear();
            addBooksInList();
            fullTable.refresh();
        } else if (!book.isOpportunityToBorrowABook()) {
            hideLabel.setText("This book is taken");
            hideLabel.setTextFill(Color.RED);
        } else {
            hideLabel.setText("Enter a valid ID");
            hideLabel.setTextFill(Color.RED);
        }
    }

    // Method to add books to the observable list for the table view
    private void addBooksInList() throws SQLException, ClassNotFoundException {
        columnIdBook.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        columnNameBook.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        columnAuthorBook.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
        columnYearBook.setCellValueFactory(new PropertyValueFactory<>("bookYear"));
        columnLocationBook.setCellValueFactory(new PropertyValueFactory<>("location"));

        ResultSet books = dataBaseHandler.getAllBooks();
        boolean opportunityToBorrowABook;

        // Iterate through the ResultSet and add books to the list
        while (books.next()) {
            opportunityToBorrowABook = books.getInt(5) == 0;

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
