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
        String query = "UPDATE \"Fotosensore\" SET perc_luce = ?, data = ?  WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, (int) fotosensore.getValore());
            pstmt.setString(2, fotosensore.getData().toString());
            pstmt.setInt(3, fotosensore.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}