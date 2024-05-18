package main.java.ORM;

import main.java.DomainModel.Ordine;
import main.java.DomainModel.Cliente;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;



public class OrdineDAO {

    private Connection connection;

    public OrdineDAO() {

        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    public int addOrdine(Ordine ordine) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO \"Ordine\" (nome, cognome, dataConsegna, tipoPianta, quantità, stato) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, ordine.getCliente().getName());
            preparedStatement.setString(2, ordine.getCliente().getCognome());
            preparedStatement.setString(3, ordine.getDataConsegna());
            preparedStatement.setString(4, ordine.getTipoPiante());
            preparedStatement.setInt(5, ordine.getnPiante());
            preparedStatement.setString(6, "da posizionare");

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserimento dell'ordine non riuscito, nessuna riga aggiornata.");
            }

            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                ordine.setId(id); // Aggiorna l'ID dell'oggetto Ordine con l'ID generato dal database
                return id;
            } else {
                throw new SQLException("Inserimento dell'ordine non riuscito, nessun ID generato.");
            }

            System.out.println("Ordine aggiunto correttamente.");

        } catch (SQLException e) {
            System.err.println("Errore: " + e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (generatedKeys != null) {
                generatedKeys.close();
            }
        }
    }

}
