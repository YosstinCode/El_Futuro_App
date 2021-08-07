package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    public static Connection connect(){
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:src/database/reto5db.db");
            System.out.println("success connection!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("CONNECTION| " + e);
        }
        return connection;
    }
}