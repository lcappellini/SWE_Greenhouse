package main.java.ORM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.java.DomainModel.Impianto.Spazio;

public class SpazioDAO {

    private Connection connection;

    public  SpazioDAO(){
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public Spazio getSpazio(int idSpazio) {
        String query = "SELECT * FROM \"Spazio\"";
        Spazio spazio = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idSpazio);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                spazio = new Spazio(id);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero dello spazio: " + e.getMessage());
        }

        return spazio;
    }

}
