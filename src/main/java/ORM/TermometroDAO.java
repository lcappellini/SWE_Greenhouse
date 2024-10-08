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
        String query = "UPDATE \"Termometro\" SET perc_luce = ?, data = ?  WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, (int) termometro.getValore());
            pstmt.setString(2, termometro.getData().toString());
            pstmt.setInt(3, termometro.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
