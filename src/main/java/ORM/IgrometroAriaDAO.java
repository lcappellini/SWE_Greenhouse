
package main.java.ORM;
import main.java.DomainModel.Impianto.IgrometroAria;
import main.java.DomainModel.Impianto.Sensore;
import main.java.DomainModel.Impianto.Termometro;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class IgrometroAriaDAO extends SensoreDAO{
    private Connection connection;

    public IgrometroAriaDAO(){

        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    @Override
    public void registraMisura(Sensore igrometro) {
        String query = "INSERT INTO \"IgrometroAria\" (id, perc_acqua, data) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, igrometro.getId());
            pstmt.setFloat(2, igrometro.getValore());
            String time = LocalDateTime.now().toString();
            pstmt.setString(3, time);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}