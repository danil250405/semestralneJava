package database;
import AllClasses.Book;
import AllClasses.User;

import java.sql.*;


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
    public ResultSet getUser() throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT * FROM users";
        PreparedStatement prSt = null;
        prSt =getDbConnection().prepareStatement(select);
        resSet = prSt.executeQuery();


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

    public boolean setNewUserIdForBook(int bookId, int newUserId, String newUsername) throws SQLException, ClassNotFoundException {
        String updateQuery = "UPDATE " + ConstForBooks.BOOK_TABLE +
                " SET " + ConstForBooks.ID_USER + " = ?, " +
                ConstForBooks.BOOK_LOCATION + " =?" +
                " WHERE " + ConstForBooks.BOOK_ID + " = ?";

        try (Connection connection = getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setInt(1, newUserId);
            preparedStatement.setString(2, newUsername);
            preparedStatement.setInt(3, bookId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Данные успешно обновлены");
                return true;
            } else {
                System.out.println("Обновление данных не выполнено");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public int getCountFromBooks(){

        String sql = "SELECT COUNT(*) FROM books";
        // Подготавливаем SQL-запрос
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {
            // Выполняем запрос и получаем результат
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Переходим к первой строке результата
                resultSet.next();

                // Получаем значение из первой колонки (количество строк)
                int rowCount = resultSet.getInt(1);

                // Выводим результат
                System.out.println("Количество строк в таблице: " + rowCount);
                return rowCount;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    public boolean deleteRowFromBooksTable(int rowIdDelete) {
        String sqlDelete = "DELETE FROM books WHERE " + ConstForBooks.BOOK_ID + " = " + rowIdDelete;

        // Подготавливаем SQL-запрос для удаления
        try (PreparedStatement deleteStatement = dbConnection.prepareStatement(sqlDelete)) {
            // Выполняем запрос на удаление
            int rowsAffected = deleteStatement.executeUpdate();
            // Выводим результат удаления
            System.out.println("Количество удаленных строк: " + rowsAffected);
            System.out.println("Номер удаленоой строки: " + rowIdDelete);
            if (rowsAffected > 0) {
                return true; // Успешное удаление
            } else {
                return false; // Ничего не было удалено
            }
        } catch (SQLException ex) {

            throw new RuntimeException("Ошибка при удалении строки", ex);

        }



    }
    public void refreshBooksId() {
        // Обнуляем счетчик
        String resetCounterQuery = "SET @counter = 0;";
        try (Statement resetCounterStatement = dbConnection.createStatement()) {
            resetCounterStatement.executeUpdate(resetCounterQuery);
        } catch (SQLException ex) {
            throw new RuntimeException("Ошибка при обнулении счетчика", ex);
        }

        // Обновляем идентификаторы с использованием счетчика
        String updateQuery = "UPDATE books SET " + ConstForBooks.BOOK_ID + " = (@counter:=@counter+1) ORDER BY " + ConstForBooks.BOOK_ID + ";";
        try (Statement updateStatement = dbConnection.createStatement()) {
            updateStatement.executeUpdate(updateQuery);
        } catch (SQLException ex) {
            throw new RuntimeException("Ошибка при обновлении идентификаторов", ex);
        }
    }
    public Book getBookById(int bookIdfromUser) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT * FROM books WHERE " + ConstForBooks.BOOK_ID + "=?";
        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
            prSt.setInt(1, bookIdfromUser);
            resSet = prSt.executeQuery();

            if (resSet.next()) {
                // Продолжайте извлекать данные только если есть строки в результате запроса
                Book book = new Book(resSet.getInt(1),resSet.getString(2), resSet.getString(3),
                        resSet.getInt(4), resSet.getInt(5), resSet.getString(6));
                System.out.println(book.getBookId() +","+ book.getBookName() +","+ book.getBookAuthor() +","+ book.getIduser());
                return book; // Возвращает найденную книгу
            } else {
                // Обработка ситуации, когда результат запроса пуст
                System.out.println("Книга с указанным ID не найдена.");
                return null; // Возвращает null, чтобы указать, что книга не найдена
            }
        } finally {
            if (resSet != null) {
                resSet.close();
            }
        }
    }
    public boolean deleteBookFromUserLibrary(int bookIdFromUser) {
        String updateQuery = "UPDATE " + ConstForBooks.BOOK_TABLE +
                " SET " + ConstForBooks.ID_USER + " = 0 " + ","+
                ConstForBooks.BOOK_LOCATION + " = 'In library'" +
                " WHERE " + ConstForBooks.BOOK_ID + " = ?";

        try (Connection connection = getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setInt(1, bookIdFromUser);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) return true;
            else return false;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Log the exception or handle it appropriately
            return false;
        }
    }



}