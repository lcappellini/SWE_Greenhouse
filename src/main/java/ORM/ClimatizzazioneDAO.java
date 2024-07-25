package main.java.ORM;

import main.java.DomainModel.Impianto.Attuatore;
import main.java.DomainModel.Impianto.Climatizzazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClimatizzazioneDAO extends AttuatoreDAO {
    private Connection connection;

    public ClimatizzazioneDAO(){

        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    @Override
    public void registraAzione(Attuatore climatizzazione){
        String query = "INSERT INTO \"Climatizzazione\" (id, acceso) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, climatizzazione.getId());
            pstmt.setBoolean(2, climatizzazione.attivo());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
