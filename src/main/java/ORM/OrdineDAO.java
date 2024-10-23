package main.java.ORM;

import main.java.DomainModel.Ordine;
import main.java.DomainModel.Pianta;


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
            preparedStatement.setString(5, ordine.getStato());

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

        // Aggiungi condizioni se ci sono criteri
        if (criteri != null && !criteri.isEmpty()) {
            query.append(" WHERE ");
            for (String key : criteri.keySet()) {
                query.append(key).append(" = ? AND ");
            }
            query.setLength(query.length() - 5);  // Rimuove l'ultimo " AND "
        }

        ArrayList<Ordine> ordini = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            // Imposta i parametri se ci sono criteri
            if (criteri != null && !criteri.isEmpty()) {
                int paramIndex = 1;
                for (Object value : criteri.values()) {
                    statement.setObject(paramIndex, value);
                    paramIndex++;
                }
            }

            // Esegui la query e gestisci il ResultSet
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idOrdine = resultSet.getInt("id");
                    ArrayList<Pianta> piante = piantaDAO.get(Map.of("ordine", idOrdine));
                    ordini.add(new Ordine(
                            idOrdine,
                            resultSet.getInt("cliente"),
                            piante,
                            resultSet.getString("stato"),
                            resultSet.getString("dataConsegna"),
                            resultSet.getDouble("totale")
                    ));
                }
            }
        } catch (SQLException e) {
            // Logga l'errore
            System.err.println("Errore durante il recupero degli ordini: " + e.getMessage());
            e.printStackTrace(); // Mostra la traccia dello stack per una diagnostica più dettagliata
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
            query.setLength(query.length() - 2);  // Rimuove l'ultimo " AND "
        } else {
            return updated;
        }

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            int paramIndex = 1;
            for (Object value : criterio.values()) {
                statement.setObject(paramIndex, value);
                paramIndex++;
            }
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

    public Ordine getById(int idOrdine) {
        ArrayList<Ordine> ordini = get(Map.of("id", idOrdine));
        if (ordini.isEmpty())
            return null;
        else
            return ordini.get(0);
    }

    public void aggiornaOrdine(Ordine ordine) throws SQLException {
        String query = "UPDATE \"Ordine\" SET cliente = ?, dataConsegna = ?, piante = ?, totale = ?, stato = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Assegna i parametri alla query
            statement.setInt(1, ordine.getCliente());
            //TODO utilizzare logica DATE di Sql in Ordine ecc...
            statement.setString(2, ordine.getStringDataConsegna());
            statement.setObject(3, ordine.getPiante());  // Assicurati di usare il tipo giusto, eventualmente serializzando la lista di piante
            statement.setDouble(4, ordine.getTotale());
            statement.setString(5, ordine.getStato());
            statement.setInt(6, ordine.getId());

            int rowsUpdated = statement.executeUpdate();
             /*
            if (rowsUpdated > 0) {
                System.out.println("Ordine aggiornato correttamente. Pagamento effettuato.");
            } else {
                System.out.println("Ordine non trovato o non aggiornato.");
            }*/
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento dell'ordine: " + e.getMessage());
        }
        return false;
    }

}
