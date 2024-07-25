package main.java.ORM;

import main.java.DomainModel.Impianto.Attuatore;
import main.java.DomainModel.Impianto.Climatizzatore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClimatizzatoreDAO extends AttuatoreDAO {
    private Connection connection;

    public ClimatizzatoreDAO(){

        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    @Override
    public void registraAzione(Attuatore climatizzatore){
        String query = "INSERT INTO \"Climatizzatore\" (id, acceso) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, climatizzatore.getId());
            pstmt.setBoolean(2, climatizzatore.attivo());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
