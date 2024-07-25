package main.java.ORM;

import main.java.DomainModel.Impianto.Attuatore;

import java.sql.*;
import java.util.Map;

public class OperatoreDAO extends AttuatoreDAO{
    private Connection connection;

    public OperatoreDAO(){

        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    @Override
    public void registraAzione(Attuatore operatore){
        //FIXME aggiungere la data e Un tipo di OPERAZIONE che ha (descrizione,data)
        String query = "INSERT INTO \"Operazione\" (tipoAttuatore, idAttuatore, descrizione) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, operatore.getClass().toString());
            pstmt.setInt(2, operatore.getId());
            pstmt.setString(3,"TBD");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
