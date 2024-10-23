package main.java.ORM;

import main.java.DomainModel.Impianto.Sensore;

import main.java.DomainModel.Impianto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.*;

public class SensoreDAO {

    private Connection connection;

    public SensoreDAO() {
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void aggiorna(Sensore sensore) {
        String query = "UPDATE \"Sensore\" SET valore = ?, data = ?, tipo = ?  WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setDouble(1, sensore.getValore());
            pstmt.setString(2, sensore.getData().toString());
            pstmt.setString(3, sensore.getTipoSensore());
            pstmt.setInt(4, sensore.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public Sensore getById(int id) {
        String query = "SELECT * FROM \"Sensore\" WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String tipoSensore = rs.getString("tipo");
                if (tipoSensore.equals("Fotosensore"))
                    return new Fotosensore(rs.getInt("id"), rs.getString("data"), rs.getFloat("valore"));
                else if (tipoSensore.equals("IgrometroTerra"))
                    return new IgrometroTerra(rs.getInt("id"), rs.getString("data"), rs.getFloat("valore"));
                else if (tipoSensore.equals("IgrometroAria"))
                    return new IgrometroAria(rs.getInt("id"), rs.getString("data"), rs.getFloat("valore"));
                else if (tipoSensore.equals("Termometro"))
                    return new Termometro(rs.getInt("id"), rs.getString("data"), rs.getFloat("valore"));
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
