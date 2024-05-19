package main.java.ORM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AmbienteDAO {

    private Connection connection;

    public  AmbienteDAO(){
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void creaAmbiente(String nome, String descrizione) {
        String query = "INSERT INTO Ambiente (nome, descrizione) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nome);
            statement.setString(2, descrizione);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Ambiente '"+nome +"' creato con successo.");
            } else {
                System.out.println("Errore durante la creazione dell'ambiente.");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento dell'ambiente: " + e.getMessage());
        }
    }

    public void rimuoviAmbiente(String nome) {
        String query = "DELETE FROM Ambiente WHERE nome = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nome);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Ambiente rimosso con successo.");
            } else {
                System.out.println("Errore durante la rimozione dell'ambiente. Nessun ambiente trovato con il nome specificato.");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la rimozione dell'ambiente: " + e.getMessage());
        }
    }

    public void visualizzaAmbienti() {
        String query = "SELECT * FROM Ambiente";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Lista di tutti gli ambienti:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String descrizione = resultSet.getString("descrizione");

                System.out.printf("ID: %d | Nome: %s | Descrizione: %s%n", id, nome, descrizione);
            }

        } catch (SQLException e) {
            System.err.println("Errore durante la visualizzazione degli ambienti: " + e.getMessage());
        }
    }
    public int getNSpaziMaxByIdAmbiente(int idAmbiente) {
        int nSpaziMax = 0;
        String query = "SELECT nSpaziMax FROM Ambiente WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idAmbiente);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nSpaziMax = resultSet.getInt("nSpaziMax");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero di nSpaziMax: " + e.getMessage());
        }

        return nSpaziMax;
    }
}
