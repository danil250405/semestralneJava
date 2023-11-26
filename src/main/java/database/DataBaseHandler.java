package database;
import com.example.semestralnejava.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import  java.sql.ResultSet;


public class DataBaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String conectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName;
        Class.forName("com.mysql.jdbc.Driver");
        dbConnection = DriverManager.getConnection(conectionString,dbUser,dbPass);
        return dbConnection;
    }
    public void signUpUser(User user){
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USERS_FIRSTNAME + "," + Const.USERS_LASTNAME + "," +
                Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + "," +
                Const.USERS_EMAIL + "," + Const.USERS_GENDER + ") " +
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
    public ResultSet getUser(User user){
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";


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
}
