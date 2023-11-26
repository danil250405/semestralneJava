package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    public void signUpUser(String firstname, String lastName, String username,
                            String password, String email, String gender){
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USERS_FIRSTNAME + "," + Const.USERS_LASTNAME + "," +
                Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + "," +
                Const.USERS_EMAIL + "," + Const.USERS_GENDER + ") " +
                "VALUES(?,?,?,?,?,?)";
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(insert);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            prSt.setString(1,firstname);
            prSt.setString(2,lastName);
            prSt.setString(3,username);
            prSt.setString(4,password);
            prSt.setString(5,email);
            prSt.setString(6,gender);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
