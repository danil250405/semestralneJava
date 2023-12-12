package com.example.semestralnejava;

import AllClasses.Book;
import AllClasses.buttonsImages;
import database.DataBaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LibraryBooksControllerForUser {

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
    private TableColumn<Book, Integer> columnYearBook;

    @FXML
    private SVGPath imageButtonHomeSvg;
    DataBaseHandler dataBaseHandler = new DataBaseHandler();
    private final ObservableList<Book> bookList = FXCollections.observableArrayList();
    @FXML
    private Label librarynewgen;
    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        System.out.println(Controller.authorizedUser.getUserId());
        imageButtonHomeSvg.setOnMouseClicked(event ->{
            buttonsImages.buttonHomePressed(librarynewgen);
        });
        addBooksInList();

        addBookInYourLibraryBtn.setOnAction(event ->{
            try {
                addBookInYourLibrary();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        });




    }


    private void addBookInYourLibrary() throws SQLException, ClassNotFoundException {
        int idBookWhichYouWant = Integer.parseInt(bookIdWchichYouWantTextField.getText().trim());
        Book book = dataBaseHandler.getBookById(idBookWhichYouWant);

        // Book book = dataBaseHandler.getBookById(idBookWhichYouWant);
        System.out.println(book.getBookAuthor());
        if (book.getIduser() == 0){
            book.setOpportunityToBorrowABook(true);
        }
        else book.setOpportunityToBorrowABook(false);
        if (book.isOpportunityToBorrowABook() && book != null){
            System.out.println(Controller.authorizedUser.getUsername());
            book.setIduser(Controller.authorizedUser.getUserId());
            book.setLocation("In " +Controller.authorizedUser.getUsername() + " library");
            dataBaseHandler.setNewUserIdForBook(book.getBookId(), book.getIduser(), book.getLocation());

            hideLabel.setText("You take this book");
            hideLabel.setTextFill(Color.GREEN);
            bookList.clear();
            addBooksInList();
            fullTable.refresh();
        }
        else if (!book.isOpportunityToBorrowABook()){
            hideLabel.setText("This book is taken");
            hideLabel.setTextFill(Color.RED);
        }

        else
        {
            hideLabel.setText("enter a real id");
            hideLabel.setTextFill(Color.RED);

        }


    }

    private void addBooksInList() throws SQLException, ClassNotFoundException {
        columnIdBook.setCellValueFactory(new PropertyValueFactory<Book, Integer>("bookId"));
        columnNameBook.setCellValueFactory(new PropertyValueFactory<Book, String>("bookName"));
        columnAuthorBook.setCellValueFactory(new PropertyValueFactory<Book, String>("bookAuthor"));
        columnYearBook.setCellValueFactory(new PropertyValueFactory<Book, Integer>("bookYear"));
        columnLocationBook.setCellValueFactory(new PropertyValueFactory<Book, String>("location"));

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
