package main.java.ORM;
import main.java.DomainModel.Impianto.Sensore;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class FotosensoreDAO extends SensoreDAO{
    private Connection connection;

    public FotosensoreDAO(){

        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    @Override
    public void registraMisura(Sensore fotosensore) {
        String query = "INSERT INTO \"Fotosensore\" (id, perc_luce, data) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, fotosensore.getId());
            pstmt.setFloat(2, fotosensore.getValore());
            String time = LocalDateTime.now().toString();
            pstmt.setString(3, time);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}