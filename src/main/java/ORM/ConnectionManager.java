package main.java.ORM;

import java.sql.*;


public class ConnectionManager {

    private static final String url = "jdbc:postgresql://localhost:5432/Greenhouse";
    private static final String username = "Greenhouse_admin";
    private static final String password = "admin";
    private static Connection connection = null;

    // singleton instance
    private static ConnectionManager instance = null;

    private ConnectionManager(){}

    public static ConnectionManager getInstance() {

        if (instance == null) { instance = new ConnectionManager(); }

        return instance;

    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");

        if (connection == null)
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }

        return connection;

    }

}




