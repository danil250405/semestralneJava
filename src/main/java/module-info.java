module com.example.semestralnejava {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.semestralnejava to javafx.fxml;
    exports com.example.semestralnejava;
    exports database;
    opens database to javafx.fxml;
    exports AllClasses;
    opens AllClasses to javafx.fxml;
}