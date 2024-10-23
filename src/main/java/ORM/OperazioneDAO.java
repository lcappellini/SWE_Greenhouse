package main.java.ORM;
import main.java.DomainModel.Impianto.Attuatore;

import java.sql.*;



public class OperazioneDAO {

    private Connection connection;

    public OperazioneDAO() {

        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    public void registraAzione(Attuatore attuatore, String descrizione, String data) {
        String query = "INSERT INTO \"Operazione\" (tipoAttuatore, idAttuatore, descrizione, data) VALUES (?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, attuatore.getTipoAttuatore());
            statement.setInt(2, attuatore.getId());
            statement.setString(3, descrizione);
            statement.setString(4, data);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore durante la registrazione dell'operazione: " + e.getMessage());
        }
    }
}