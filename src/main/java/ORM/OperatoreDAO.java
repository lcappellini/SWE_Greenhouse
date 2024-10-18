package main.java.ORM;

import main.java.DomainModel.Impianto.Operatore;

import java.sql.*;

public class OperatoreDAO extends AttuatoreDAO{
    private Connection connection;

    public OperatoreDAO(){
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    public Operatore getById(int id) {
        String query = "SELECT * FROM \"Operatore\" WHERE id = ?";
        Operatore op = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            ResultSet resultSet= statement.executeQuery();
            if(resultSet.next()){
                op = new Operatore(resultSet.getInt("id"),
                        resultSet.getBoolean("working"));
            }


        } catch (SQLException e) {
            System.err.println("Errore durante la creazione dell'operatore: " + e.getMessage());
        }
        return op;
    }

}
