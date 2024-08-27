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
                if (resultSet.next()) {
                    // Estrai i dati dell'ordine dal result set e costruisci un oggetto Ordine
                    Pianta pianta = new Pianta(resultSet.getInt("id"),
                            resultSet.getString("tipo"),
                            resultSet.getString("descrizione"));
                    return pianta;
                }
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca dell'ordine: " + e.getMessage());
        }
        return null;
    }

    public void aggiungi(ArrayList<Pianta> piante) {
        String query = "INSERT INTO \"Pianta\" (tipo) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);  // Inizio della transazione
            for (Pianta pianta : piante) {
                statement.clearParameters();
                statement.setString(1, pianta.getTipoPianta());
                statement.executeUpdate();
            }
            connection.commit();  // Commit della transazione
        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento delle piante: " + e.getMessage());
        }

    }
    public void aggiornaDescrizione(int idPianta , String descrizione) {
        String query = "UPDATE \"Pianta\" SET descrizione = (?) WHERE (id) = (?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, descrizione);
            statement.setInt(2, idPianta);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento della pianta: " + e.getMessage());
        }

    }
}