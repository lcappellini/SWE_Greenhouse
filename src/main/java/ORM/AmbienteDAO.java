package main.java.ORM;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public void rimuoviAmbiente() {
    }
}
