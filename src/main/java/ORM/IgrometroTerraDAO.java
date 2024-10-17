package main.java.ORM;

import main.java.DomainModel.Impianto.IgrometroTerra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IgrometroTerraDAO {

    private Connection connection;
    public IgrometroTerraDAO() {
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public IgrometroTerra getById(int id) {
        IgrometroTerra igrometroTerra = null;
        String sql = "SELECT * FROM \"IgrometroTerreno\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                igrometroTerra = new IgrometroTerra(id, resultSet.getInt("perc_acqua"),
                        resultSet.getString("data"));
            }
        }catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return igrometroTerra;
    }
}
