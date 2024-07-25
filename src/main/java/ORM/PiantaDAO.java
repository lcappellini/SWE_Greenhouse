package main.java.ORM;

import main.java.DomainModel.Ordine;
import main.java.DomainModel.Pianta.Pianta;

import java.sql.*;
import java.util.ArrayList;


public class PiantaDAO {

    private Connection connection;

    public PiantaDAO() {
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public Pianta restituisciPianta(int id) {
        String query = "SELECT * FROM \"Pianta\" WHERE (id) = (?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                // Estrai i dati dell'ordine dal result set e costruisci un oggetto Ordine
                Pianta pianta = new Pianta(resultSet.getInt("id"),
                        resultSet.getString("tipo"),
                        resultSet.getString("descrizione"));
                return pianta;
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca dell'ordine: " + e.getMessage());
        }
        return null;
    }
}