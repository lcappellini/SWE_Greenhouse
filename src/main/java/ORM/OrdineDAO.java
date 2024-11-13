package main.java.ORM;

import main.java.DomainModel.Ordine;
import main.java.DomainModel.Pianta;
import main.java.DomainModel.StatoOrdine;


import java.sql.*;
import java.util.ArrayList;
import java.util.Map;


public class OrdineDAO {

    private Connection connection;

    public OrdineDAO() {
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public int inserisciOrdine(Ordine ordine) {
        String sql = "INSERT INTO \"Ordine\" (cliente, dataConsegna, piante, totale, stato) VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Set parameters for the prepared statement
            preparedStatement.setInt(1, ordine.getCliente());
            preparedStatement.setString(2, ordine.getStringDataConsegna());  // Assuming you have this method
            preparedStatement.setString(3, ordine.getPianteforDB());        // Assuming this returns the right format
            preparedStatement.setDouble(4, ordine.getTotale());
            preparedStatement.setInt(5, ordine.getStatoId());

            // Execute the insert operation
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento dell'ordine: " + e.getMessage());
        }
        return -1;
    }

    public ArrayList<Ordine> get(Map<String, Object> criteri) {
        StringBuilder query = new StringBuilder("SELECT * FROM \"Ordine\"");
        if (criteri != null && !criteri.isEmpty()) { // Aggiungi condizioni se ci sono criteri
            query.append(" WHERE ");
            for (String key : criteri.keySet()) {
                query.append(key).append(" = ? AND ");
            }
            query.setLength(query.length() - 5);  // Rimuove l'ultimo AND
        }

        ArrayList<Ordine> ordini = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            // Imposta i parametri se ci sono criteri
            if (criteri != null && !criteri.isEmpty()) {
                int paramIndex = 1;
                for (Object value : criteri.values()) {
                    statement.setObject(paramIndex++, value);
                }
            }
            PiantaDAO piantaDAO = new PiantaDAO();
            // Esegui la query e gestisci il ResultSet
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idOrdine = resultSet.getInt("id");
                    // chiama PiantaDAO per inizializzare le piante
                    ArrayList<Pianta> piante = piantaDAO.get(Map.of("ordine", idOrdine));
                    ordini.add(new Ordine(
                            idOrdine,
                            resultSet.getInt("cliente"),
                            piante,
                            StatoOrdine.values()[resultSet.getInt("stato")],
                            resultSet.getString("dataConsegna"),
                            resultSet.getDouble("totale")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero degli ordini: " + e.getMessage());
        }
        return ordini;
    }

    public boolean aggiorna(int id_ordine, Map<String, Object> criterio) {
        boolean updated = false;
        // Aggiungi condizioni se ci sono criteri
        String str = "UPDATE \"Ordine\" SET ";
        StringBuilder query = new StringBuilder(str);
        if (criterio != null && !criterio.isEmpty()) {
            for (String key : criterio.keySet()) {
                query.append(key).append(" = ?, ");
            }
            query.setLength(query.length() - 2);  // Rimuove l'ultima ", "
        } else {
            return updated;
        }

        query.append(" WHERE id = ?");

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            int paramIndex = 1;
            for (Object value : criterio.values()) {
                statement.setObject(paramIndex, value);
                paramIndex++;
            }
            statement.setInt(paramIndex, id_ordine);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                updated = true;
            } else {
                System.out.println("Ordine non trovato o non aggiornato.");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento dell'ordine: " + e.getMessage());
        }
        return updated;
    }

}
