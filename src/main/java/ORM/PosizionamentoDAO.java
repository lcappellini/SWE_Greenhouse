package main.java.ORM;

import main.java.DomainModel.Impianto.Posizionamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class PosizionamentoDAO {
    private Connection connection;

    public PosizionamentoDAO() {
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void inserisciPosizionamenti(ArrayList<Posizionamento> posizionamenti) throws SQLException {
        String queryInserimento = "INSERT INTO \"Posizionamento\" (pianta, posizione, ordine) VALUES (?, ?, ?)";

        try (PreparedStatement statementInserimento = connection.prepareStatement(queryInserimento)) {
            connection.setAutoCommit(false);  // Inizio della transazione

            for (Posizionamento p : posizionamenti) {
                statementInserimento.clearParameters();
                statementInserimento.setInt(1, p.getIdPianta());  // Considera di usare l'indice i o un'altra logica appropriata
                statementInserimento.setInt(2, p.getIdPosizione());
                statementInserimento.setInt(3, p.getIdOrdine());
                statementInserimento.executeUpdate();
            }

            connection.commit();  // Commit della transazione
        } catch (SQLException e) {
            connection.rollback();  // Rollback della transazione in caso di errore
            System.err.println("Errore durante la creazione dei posizionamenti: " + e.getMessage());
        }
    }

    public boolean eliminaPosizionamentiByOrdine(int idOrdine){
        String deleteQuery = "DELETE FROM \"Posizionamento\" WHERE ordine = ?";

        // Gestisci la transazione manualmente
        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
            connection.setAutoCommit(false);  // Inizio della transazione

            // Imposta il parametro dell'ordine nella query
            deleteStmt.setInt(1, idOrdine);

            // Esegui la query di eliminazione
            int affectedRows = deleteStmt.executeUpdate();

            if (affectedRows > 0) {
                // Elimina eseguita correttamente
                System.out.println("Righe eliminate con successo: " + affectedRows);
                connection.commit();
                return true; // Imposta isDeleted a true se ci sono state righe eliminate
            } else {
                System.out.println("Nessun Posizionamento eliminato.");
                connection.commit();
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'eliminazione dei posizionamenti: " + e.getMessage());
        }
        return false;
    } /*
        public boolean eliminaPosizionamentiByOrdine(int idOrdine) {
            String sql =
                    "WITH deleted_pos AS ( " +
                            "   DELETE FROM \"Posizionamento\" " +
                            "   WHERE ordine = ? " +
                            "   RETURNING posizione_id, pianta_id, ordine_id " +
                            ") " +
                            "-- Aggiornamento della tabella Posizione \n" +
                            "UPDATE \"Posizione\" " +
                            "SET assegnata = false, occupata = false " +
                            "WHERE id = (SELECT posizione_id FROM deleted_pos); " +

                            "-- Aggiornamento della tabella Pianta \n" +
                            "UPDATE \"Pianta\" " +
                            "SET stato = 'pronta' " +
                            "WHERE id = (SELECT pianta_id FROM deleted_pos); " +

                            "-- Aggiornamento della tabella Ordine \n" +
                            "UPDATE \"Ordine\" " +
                            "SET stato = 'da ritirare' " +
                            "WHERE id = (SELECT ordine_id FROM deleted_pos);";

            // Gestisci la transazione manualmente
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                connection.setAutoCommit(false);  // Inizio della transazione

                // Imposta il parametro dell'ordine nella query
                preparedStatement.setInt(1, idOrdine);

                // Esegui la query
                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    // La query ha avuto successo, commit della transazione
                    connection.commit();
                    System.out.println("Posizionamenti eliminati.");
                    return true;
                } else {
                    // Nessun record eliminato, commit comunque per la transazione vuota
                    connection.commit();
                    System.out.println("Nessun Posizionamento da eliminare.");
                    return false;
                }
            } catch (SQLException e) {
                // In caso di errore, fai un rollback della transazione
                try {
                    connection.rollback();
                    System.err.println("Errore durante l'eliminazione e aggiornamento dei posizionamenti: " + e.getMessage());
                } catch (SQLException rollbackEx) {
                    System.err.println("Errore durante il rollback della transazione: " + rollbackEx.getMessage());
                }
            } finally {
                try {
                    connection.setAutoCommit(true);  // Riporta l'auto-commit allo stato originale
                } catch (SQLException e) {
                    System.err.println("Errore nel ripristinare l'auto-commit: " + e.getMessage());
                }
            }
            return false;
        }

*/
    public ArrayList<Posizionamento> get(Map<String, Object> criteria){
        ArrayList<Posizionamento> posizionamenti = new ArrayList<>();

        StringBuilder query = new StringBuilder("SELECT * FROM \"Posizionamento\"");

        if (criteria != null && !criteria.isEmpty()) {
            query.append(" WHERE ");
            for (String key : criteria.keySet()) {
                query.append(key).append(" = ? AND ");
            }
            query.setLength(query.length() - 5);  // Rimuove l'ultimo " AND "
        }

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            if (criteria != null && !criteria.isEmpty()) {
                int paramIndex = 1;
                for (Object value : criteria.values()) {
                    statement.setObject(paramIndex, value);
                    paramIndex++;
                }
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int posizioneId = resultSet.getInt("posizione");
                    int ordineId = resultSet.getInt("ordine");
                    int piantaId = resultSet.getInt("pianta");


                    posizionamenti.add(new Posizionamento(
                            resultSet.getInt("id"),
                            posizioneId,
                            piantaId,
                            ordineId
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return posizionamenti;
    }

}
