package com.example.semestralnejava;

import AllClasses.Book;
import AllClasses.buttonHome;
import database.DataBaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LibraryBooksController extends Controller{

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
    private ImageView imageButtonHome;
    DataBaseHandler dataBaseHandler = new DataBaseHandler();
    private final ObservableList<Book> bookList = FXCollections.observableArrayList();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        //System.out.println(getAuthorizedUser().getUserId());
        imageButtonHome.setOnMouseClicked(event ->{
            buttonHome.buttonHomePressed(imageButtonHome);
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
        int idBookWhichYouWant = Integer.parseInt(bookIdWchichYouWantTextField.getText().trim()) - 1;
        Book book = bookList.get(idBookWhichYouWant);
        if (book.isOpportunityToBorrowABook()){
            System.out.println(authorizedUser.getUsername());
            book.setIduser(authorizedUser.getUserId());
            book.setLocation("In " + authorizedUser.getUsername() + " library");
            dataBaseHandler.setNewUserIdForBook(book.getBookId(), book.getIduser(), book.getLocation());

            hideLabel.setText("You take this book");
            hideLabel.setTextFill(Color.GREEN);
            fullTable.refresh();
        }
        else
        {

            hideLabel.setText("This book is taken");
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
