package main.java.ORM;

import main.java.DomainModel.Cliente;
import main.java.DomainModel.Ordine;


import java.sql.*;
import java.util.ArrayList;


public class OrdineDAO {

    private Connection connection;

    public OrdineDAO() {

        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    public int addOrdine(Ordine ordine) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO \"Ordine\" (nome, cognome, dataConsegna, tipoPianta, quantità, stato) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        int id = -1; // Inizializza l'ID con un valore di default

        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, ordine.getCliente().getEmail());
            preparedStatement.setString(2, ordine.getCliente().getPassword());
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
                id = generatedKeys.getInt(1);
                ordine.setId(id); // Aggiorna l'ID dell'oggetto Ordine con l'ID generato dal database
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
        return id; // Restituisci l'ID dell'ordine appena inserito
    }

    public ArrayList<Ordine> vediOrdini(Cliente cliente) {
        ArrayList<Ordine> ordini = new ArrayList<>();
        String query = "SELECT * FROM Ordine WHERE cliente = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cliente.getEmail());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Estrai i dati dell'ordine dal result set e costruisci un oggetto Ordine
                    Ordine ordine = new Ordine();
                    ordine.setId(resultSet.getInt("id"));
                    ordine.setCliente(cliente);
                    ordine.setPianteDalTipo(resultSet.getString("tipoPianta"),
                            resultSet.getInt("quantità"));
                    ordine.setStato(resultSet.getString("stato"));
                    ordine.setDataConsegna(resultSet.getString("dataConsegna"));

                    ordini.add(ordine);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero degli ordini: " + e.getMessage());
        }

        return ordini;
    }

    public void completaOrdine(Cliente cliente) {
        String query = "UPDATE Ordine SET stato = 'venduto' WHERE cliente = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cliente.getEmail());

            int rowsUpdated = statement.executeUpdate();

            System.out.println(rowsUpdated + " ordini completati per il cliente " + cliente.getEmail());
        } catch (SQLException e) {
            System.err.println("Errore durante il completamento degli ordini: " + e.getMessage());
        }
    }

}
