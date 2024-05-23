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
        String query = "SELECT * FROM \"Ordine\" WHERE cliente = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cliente.getId());

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

    public void paga_e_ritira_Ordine(Cliente cliente, int idOrdine) {
        String query = "UPDATE \"Ordine\" SET stato = 'venduto e ritirato' WHERE (id, ordine )= (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idOrdine);
            statement.setInt(2, cliente.getId());

            statement.executeUpdate();

            System.out.println("Pagamento effettuato correttemente. Può ritirare l'ordine.");

        } catch (SQLException e) {
            System.err.println("Errore durante il pagamenti dell'ordine: " + e.getMessage());
        }
    }

    public void visualizzaOrdini() {
        String query = "SELECT * FROM \"Ordine\" ";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Stampiamo l'intestazione
            System.out.println("+--------+------------+-------------------+");
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("| %-10s ", metaData.getColumnName(i));
            }
            System.out.println("|");
            System.out.println("+--------+------------+-------------------+");

            // Stampiamo le righe
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("| %-10s ", resultSet.getString(i));
                }
                System.out.println("|");
            }
            System.out.println("+--------+------------+-------------------+");

        } catch (SQLException e) {
            System.err.println("Errore durante la visualizzazione degli ordini: " + e.getMessage());
        }

    }


    public void ritiraOrdine(Cliente cliente, int idOrdine) {


    }

    public Ordine getOrdineDaPosizionare(int idOrdine) {
        String query = "SELECT * FROM \"Ordine\" WHERE (stato, id) = (?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "da posizionare");
            statement.setInt(2, idOrdine);


            try (ResultSet resultSet = statement.executeQuery()) {
                // Estrai i dati dell'ordine dal result set e costruisci un oggetto Ordine
                Ordine ordine = new Ordine();
                ordine.setId(resultSet.getInt("id"));
                ordine.setCliente(resultSet.getInt("cliente"));
                ordine.setPianteDalTipo(resultSet.getString("tipoPianta"),
                        resultSet.getInt("quantità"));
                ordine.setStato(resultSet.getString("stato"));
                ordine.setDataConsegna(resultSet.getString("dataConsegna"));
                return ordine;
            }

        } catch (SQLException e) {
            System.err.println("Errore durante il pagamenti dell'ordine: " + e.getMessage());
        }
        return null;
    }
}
