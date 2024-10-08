package main.java.ORM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.java.DomainModel.Impianto.Spazio;

public class SpazioDAO {

    private Connection connection;

    public  SpazioDAO(){
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void creaSpazio(String nome, String descrizione, int nAmbientiMax) {
        String query = "INSERT INTO \"Spazio\" (nome, descrizione, nAmbientiMax) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nome);
            statement.setString(2, descrizione);
            statement.setInt(3, nAmbientiMax);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Spazio '"+nome +"' creato con successo.");
            } else {
                System.out.println("Errore durante la creazione dell'spazio.");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento dell'spazio: " + e.getMessage());
        }
    }

    public void rimuoviSpazio(int idSpazio) {
        String query = "DELETE FROM \"Spazio\" WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idSpazio);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Spazio rimosso con successo.");
            } else {
                System.out.println("Errore durante la rimozione dello spazio. Nessuno spazio trovato con il nome specificato.");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la rimozione dello spazio: " + e.getMessage());
        }
    }

    public void visualizzaSpazi() {
        String query = "SELECT * FROM \"Spazio\"";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Lista di tutti gli spazi:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String descrizione = resultSet.getString("descrizione");

                System.out.printf("ID: %d | Nome: %s | Descrizione: %s%n", id, nome, descrizione);
            }

        } catch (SQLException e) {
            System.err.println("Errore durante la visualizzazione degli spazi: " + e.getMessage());
        }
    }

    public void visualizzaSpazio(int idSpazio) {
        String query = "SELECT nAmbientiMax FROM \"Spazio\" WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idSpazio);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String descrizione = resultSet.getString("descrizione");

                System.out.println("Spazio richiesto:");
                System.out.printf("ID: %d | Nome: %s | Descrizione: %s%n", id, nome, descrizione);

            }
        } catch (SQLException e) {
            System.err.println("Errore durante la visualizzazione dello spazio: " + e.getMessage());
        }
    }

    public int getNAmbientiMaxByIdSpazio(int idSpazio) {
        int nAmbientiMax = 0;
        String query = "SELECT nAmbientiMax FROM \"Spazio\" WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idSpazio);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nAmbientiMax = resultSet.getInt("nAmbientiMax");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero di nAmbientiMax: " + e.getMessage());
        }

        return nAmbientiMax;
    }

    public Spazio getSpazio(int idSpazio) {
        String query = "SELECT * FROM \"Spazio\" WHERE id = ?";
        Spazio spazio = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idSpazio);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                spazio = new Spazio(id);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero dello spazio: " + e.getMessage());
        }

        return spazio;
    }

}
