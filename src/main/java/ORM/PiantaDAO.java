package main.java.ORM;

import main.java.DomainModel.Impianto.Posizionamento;
import main.java.DomainModel.Pianta.Pianta;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class PiantaDAO {

    // Istanza privata statica dell'oggetto OrdineDAO

    private Connection connection;

    public PiantaDAO() {
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }


    public Pianta getById(int id) {
        String query = "SELECT * FROM \"Pianta\" WHERE (id) = (?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Estrai i dati dell'ordine dal result set e costruisci un oggetto Ordine
                    Pianta pianta = new Pianta(resultSet.getInt("id"),
                            resultSet.getString("tipo"),
                            resultSet.getString("descrizione"));
                    return pianta;
                }
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca dell'ordine: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Pianta> posaPiante(ArrayList<Pianta> piante, String descrizione) {
        String sql = "INSERT INTO \"Pianta\" (tipo, descrizione, dataInizio, stato, costo) " +
                "VALUES (?, ?, ?, ?, ?)";
        ArrayList<Pianta> piantePosizionate = new ArrayList<>();

        try (PreparedStatement statementInserimento = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            for (Pianta p : piante) {
                statementInserimento.clearParameters();
                statementInserimento.setString(1, p.getTipoPianta());  // Considera di usare l'indice i o un'altra logica appropriata
                statementInserimento.setString(2, descrizione);
                statementInserimento.setString(3, LocalDate.now().toString());
                statementInserimento.setString(4, p.getStato());
                statementInserimento.setDouble(5, p.getCosto());

                int rowsAffected = statementInserimento.executeUpdate();

                // Se l'inserimento ha avuto successo, ottieni l'ID generato
                if (rowsAffected > 0) {
                    try (ResultSet generatedKeys = statementInserimento.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            // Ottieni l'ID generato e aggiorna l'oggetto Pianta
                            int id = generatedKeys.getInt(1);
                            p.setId(id);
                            p.setDescrizione(descrizione);
                            p.setDataInizio(LocalDate.now());
                            piantePosizionate.add(p);  // Aggiungi la pianta con l'ID aggiornato
                        } else {
                            System.err.println("Errore: nessuna chiave generata per la pianta.");
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Errore durante la creazione dei posizionamenti: " + e.getMessage());
        }
        return piantePosizionate;
    }

    public void aggiornaDescrizione(int idPianta , String descrizione) {
        String query = "UPDATE \"Pianta\" SET descrizione = (?) WHERE (id) = (?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, descrizione);
            statement.setInt(2, idPianta);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento della pianta: " + e.getMessage());
        }

    }
    public void ritira(ArrayList<Integer> piante) {
        String query = "DELETE FROM \"Pianta\" WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)){
            for(Integer i : piante){
                statement.setInt(1, i);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean elimina(int id_ordine) throws SQLException {
        /*
        if (pianta == null || pianta.getId() <= 0) {
            System.out.println("Errore: Pianta non valida.");
            return false; // Ritorna false se la pianta è null o ha un ID non valido
        }
*/
        String query = "DELETE FROM \"Pianta\" WHERE ordine = ?";
        int affectedRows = 0; // Variabile per tenere traccia delle righe eliminate
        connection.setAutoCommit(false); // Assicurati che l'autocommit sia disabilitato

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_ordine);
            affectedRows = statement.executeUpdate();
            connection.commit(); // Esegui il commit qui

        } catch (SQLException e) {
            connection.rollback(); // Fai rollback in caso di errore
            System.err.println("Errore durante l'eliminazione della pianta: " + e.getMessage());
            return false; // Ritorna false in caso di errore
        }

        if (affectedRows > 0) {
            return true; // Ritorna true se l'eliminazione ha avuto successo
        } else {
            return false; // Ritorna false se non ci sono righe da eliminare
        }
    }

    public ArrayList<Pianta> inserisci(ArrayList<Pianta> piante, int id_ordine) throws SQLException {
        String queryInserimento = "INSERT INTO \"Pianta\" (tipo, descrizione, datainizio, stato, costo, ordine) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statementInserimento = connection.prepareStatement(queryInserimento, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);  // Inizio della transazione

            for (Pianta p : piante) {
                statementInserimento.clearParameters();
                statementInserimento.setString(1, p.getTipoPianta());
                statementInserimento.setString(2, p.getDescrizione());
                statementInserimento.setString(3, LocalDate.now().toString());  // Data di oggi
                statementInserimento.setString(4, p.getStato());
                statementInserimento.setDouble(5, p.getCosto());
                statementInserimento.setInt(6, id_ordine);


                // Esegui l'aggiornamento
                int affectedRows = statementInserimento.executeUpdate();

                // Verifica che l'inserimento abbia avuto successo
                if (affectedRows == 0) {
                    throw new SQLException("Inserimento della pianta fallito, nessuna riga inserita.");
                }

                // Recupera la chiave generata
                try (ResultSet generatedKeys = statementInserimento.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        p.setId(generatedKeys.getInt(1));  // Imposta l'ID della pianta
                    } else {
                        throw new SQLException("Inserimento della pianta fallito, nessuna chiave generata.");
                    }
                }
            }

            connection.commit();  // Commit della transazione
        } catch (SQLException e) {
            connection.rollback();  // Rollback della transazione in caso di errore
            System.err.println("Errore durante l'inserimento delle piante: " + e.getMessage());
            throw e;  // Rilancia l'eccezione per una gestione successiva
        }
        return piante;
    }

}