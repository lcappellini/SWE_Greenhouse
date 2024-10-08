
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
    public void registraMisura(Sensore igrometroAria) {
        String query = "UPDATE \"IgrometroAria\" SET perc_acqua = ?, data = ?  WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, (int) igrometroAria.getValore());
            pstmt.setString(2, igrometroAria.getData().toString());
            pstmt.setInt(3, igrometroAria.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}