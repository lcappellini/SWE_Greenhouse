package main.java.ORM;

import main.java.DomainModel.Admin;
import main.java.DomainModel.Impianto.Operatore;

import java.sql.*;

public class OperatoreDAO extends AttuatoreDAO{
    private Connection connection;

    public OperatoreDAO(){
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public Operatore getById(int id) {
        String query = "SELECT * FROM \"Operatore\" WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            ResultSet rs= statement.executeQuery();
            if(rs.next()){
                return new Operatore(rs.getInt("id"), rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getBoolean("working"));
            }


        } catch (SQLException e) {
            System.err.println("Errore durante la creazione dell'operatore: " + e.getMessage());
        }
        return null;
    }

    public Operatore accedi(String email, String password) {
        String query = "SELECT * FROM \"Operatore\" WHERE email = ? AND password = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    // Estrai i dati dell'admin dal result set e costruisci un oggetto Operatore
                    return new Operatore(rs.getInt("id"), rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), false);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'accesso dell'operatore: " + e.getMessage());
        }

        return null; // Se non viene trovato alcun operatore con le credenziali fornite, restituisci null
    }
}
