package main.java.ORM;

import main.java.DomainModel.Impianto.Posizionamento;
import main.java.DomainModel.Impianto.Posizione;
import main.java.DomainModel.Ordine;
import main.java.DomainModel.Pianta.Pianta;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
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

    public boolean eliminaPosizionamentiByOrdine(int idOrdine) throws SQLException {
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
            connection.rollback();  // Rollback in caso di errore
            System.err.println("Errore durante l'eliminazione dei posizionamenti: " + e.getMessage());
            throw e;  // Rilancia l'eccezione per gestione successiva
        }
    }

    public ArrayList<Posizionamento> get(Map<String, Object> criteria) throws SQLException {
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

                    // Usa i DAO per recuperare i dati necessari
                    PosizioneDAO posizioneDAO = new PosizioneDAO();
                    PiantaDAO piantaDAO = new PiantaDAO();
                    OrdineDAO ordineDAO = new OrdineDAO();

                    Posizione posizione = posizioneDAO.getById(posizioneId);
                    Ordine ordine = ordineDAO.getById(ordineId);
                    Pianta pianta = piantaDAO.getById(piantaId);

                    posizionamenti.add(new Posizionamento(
                            resultSet.getInt("id"),
                            posizione.getId(),
                            ordine.getId(),
                            pianta.getId()
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return posizionamenti;
    }

}
