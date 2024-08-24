package main.java.ORM;

import main.java.DomainModel.Admin;

import java.sql.*;


public class AdminDAO {

    private Connection connection;

    public  AdminDAO(){
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public Admin accedi(String email, String password) {
        String query = "SELECT * FROM \"Admin\" WHERE email = ? AND password = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    // Estrai i dati dell'admin dal result set e costruisci un oggetto Admin
                    Admin admin = new Admin(rs.getInt("id"), rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("password"));
                    return admin;
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'accesso dell'admin: " + e.getMessage());
        }

        return null; // Se non viene trovato alcun admin con le credenziali fornite, restituisci null
    }
}

