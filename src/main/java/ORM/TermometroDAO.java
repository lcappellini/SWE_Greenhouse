package main.java.ORM;
import main.java.DomainModel.Impianto.Sensore;
import main.java.DomainModel.Impianto.Termometro;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TermometroDAO extends SensoreDAO{
    private Connection connection;

    public TermometroDAO(){

        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    @Override
    public void registraMisura(Sensore termometro) {
        String query = "INSERT INTO \"Termometro\" (id, temperatura, data) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, termometro.getId());
            pstmt.setFloat(2, termometro.getValore());
            String time = LocalDateTime.now().toString();
            pstmt.setString(3, time);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
