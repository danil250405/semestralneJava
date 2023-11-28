package database;
import AllClasses.Book;
import AllClasses.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import  java.sql.ResultSet;


public class DataBaseHandler extends Configs {
    Connection dbConnection;
    //database connection
    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String conectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(conectionString,dbUser,dbPass);
        return dbConnection;
    }
    //add new user
    public void signUpUser(User user){
        String insert = "INSERT INTO " + ConstForUsers.USER_TABLE + "(" +
                ConstForUsers.USERS_FIRSTNAME + "," + ConstForUsers.USERS_LASTNAME + "," +
                ConstForUsers.USERS_USERNAME + "," + ConstForUsers.USERS_PASSWORD + "," +
                ConstForUsers.USERS_EMAIL + "," + ConstForUsers.USERS_GENDER + ") " +
                "VALUES(?,?,?,?,?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1,user.getFirstName());
            prSt.setString(2,user.getLastName());
            prSt.setString(3,user.getUsername());
            prSt.setString(4,user.getPassword());
            prSt.setString(5,user.getEmail());
            prSt.setString(6,user.getGender());
            prSt.executeUpdate  ();
        } catch (SQLException  | ClassNotFoundException e) {
            e.printStackTrace();
        }



    }
    //take user from database and authorization him
    public ResultSet getUser(User user){
        ResultSet resSet = null;
        String select = "SELECT * FROM users" + ConstForUsers.USER_TABLE + " WHERE " +
                ConstForUsers.USERS_USERNAME + "=? AND " + ConstForUsers.USERS_PASSWORD + "=?";


        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1,user.getUsername());
            prSt.setString(2,user.getPassword());
            resSet = prSt.executeQuery();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    return resSet;
    }
    // checking username is used or no
    public ResultSet getUsername(User user){
        ResultSet resSet = null;
        String select = "SELECT * FROM " + ConstForUsers.USER_TABLE + " WHERE " +
                ConstForUsers.USERS_USERNAME + "=?";


        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1,user.getUsername());
            resSet = prSt.executeQuery();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }
    public void addNewBook(Book book){

        String insert = "INSERT INTO " + ConstForBooks.BOOK_TABLE + "(" +
                ConstForBooks.BOOK_NAME + "," + ConstForBooks.BOOK_AUTHOR + "," +
                ConstForBooks.BOOK_YEAR + "," + ConstForBooks.ID_USER + ") " +
                "VALUES(?,?,?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1,book.getBookName());
            prSt.setString(2,book.getBookAuthor());
            prSt.setInt(3,book.getBookYear());
            prSt.setInt(4,book.getIduser());

            prSt.executeUpdate  ();
        } catch (SQLException  | ClassNotFoundException e) {
            e.printStackTrace();
        }



    }
    public ResultSet getAllBooks() throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT * FROM books";
        PreparedStatement prSt = null;
        prSt =getDbConnection().prepareStatement(select);
        resSet = prSt.executeQuery();


        return resSet;
    }
}
