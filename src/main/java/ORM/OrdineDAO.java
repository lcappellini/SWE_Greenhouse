package main.java.ORM;

import main.java.DomainModel.Cliente;
import main.java.DomainModel.Ordine;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class OrdineDAO  {

    private Connection connection;

    public OrdineDAO() {

        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    public void inserisciOrdine(Ordine ordine) {
        String sql = "INSERT INTO \"Ordine\" (cliente, dataConsegna, piante, totale, stato) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Set parameters for the prepared statement
            preparedStatement.setInt(1, ordine.getCliente());
            preparedStatement.setString(2, ordine.getStringDataConsegna());  // Assuming you have this method
            preparedStatement.setString(3, ordine.getPianteString());        // Assuming this returns the right format
            preparedStatement.setDouble(4, ordine.getTotale());
            preparedStatement.setString(5, ordine.getStato());

            // Execute the insert operation
            preparedStatement.executeUpdate();

            System.out.println("Ordine inserito correttamente.");
        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento dell'ordine: " + e.getMessage());
        }
    }


    public ArrayList<Ordine> vediOrdini(Cliente cliente) {
        ArrayList<Ordine> ordini = new ArrayList<>();
        String query = "SELECT * FROM \"Ordine\" WHERE cliente = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cliente.getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Ordine ordine = new Ordine(resultSet.getInt("id"), resultSet.getInt("cliente"),
                            resultSet.getString("piante"), resultSet.getString("stato"), resultSet.getString("dataConsegna"));
                    ordini.add(ordine);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero degli ordini: " + e.getMessage());
        }

        return ordini;
    }

    public ArrayList<Ordine> restituisci(Map<String, Object> criteri){
        ArrayList<Ordine> ordini = new ArrayList<>();

        // Costruisci la query SQL dinamica
        StringBuilder query = new StringBuilder("SELECT * FROM \"Ordine\"");

        // Aggiungi le condizioni di ricerca se ci sono criteri
        if (criteri != null && !criteri.isEmpty()) {
            query.append(" WHERE ");
            int count = 0;
            for (String key : criteri.keySet()) {
                if (count > 0) {
                    query.append(" AND ");
                }
                query.append(key).append(" = ?");
                count++;
            }
        }

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            // Imposta i valori per i parametri della query in base ai criteri
            int paramIndex = 1;
            for (Object value : criteri.values()) {
                statement.setObject(paramIndex, value); // Può gestire diversi tipi di dati
                paramIndex++;
            }

            // Esegui la query
            ResultSet resultSet = statement.executeQuery();

            // Estrai i dati dal ResultSet e crea gli oggetti Ordine
            while (resultSet.next()) {


                // Aggiungi l'ordine alla lista da restituire
                //ordini.add(ordine);
            }

        } catch (SQLException e) {
            System.err.println("Errore durante il recupero degli ordini: " + e.getMessage());
        }

        return ordini;
    }


    public void pagaERitiraOrdine(Ordine ordine) {
        String query = "UPDATE \"Ordine\" SET stato = 'ritirato' WHERE (id)= (?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ordine.getId());
            statement.executeUpdate();
            System.out.println("Pagamento effettuato correttemente. Può ritirare l'ordine.");
        } catch (SQLException e) {
            System.err.println("Errore durante il pagamenti dell'ordine: " + e.getMessage());
        }
    }

    public void visualizza(Map<String, Object> criteri) {
        ObjectDAO objectDAO = new ObjectDAO();
        objectDAO.visualizza("Ordine", criteri);
    }



    public ArrayList<Ordine> getOrdiniDaPosizionare() {
        String query = "SELECT * FROM \"Ordine\" WHERE (stato) = (?)";
        ArrayList<Ordine> ordini = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "da posizionare");


            try (ResultSet resultSet = statement.executeQuery()) {
                // Estrai i dati dell'ordine dal result set e costruisci un oggetto Ordine
                while(resultSet.next()){
                    Ordine ordine = new Ordine(resultSet.getInt("id"), resultSet.getInt("cliente"),
                            resultSet.getString("piante"), resultSet.getString("stato"), resultSet.getString("dataConsegna"));
                    ordini.add(ordine);
                };
            }

        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca dell'ordine: " + e.getMessage());
        }
        return ordini;
    }

    public boolean ordiniPronti(Cliente cliente){
        String query = "SELECT * FROM \"Ordine\" WHERE (cliente, stato)= (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cliente.getId());
            statement.setString(2, "pronto");

            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return true;
            }


        } catch (SQLException e) {
            System.err.println("Errore durante il pagamenti dell'ordine: " + e.getMessage());
        }
        return false;
    }


    public void rimuoviUltimoOrdine() {
        // Query per cancellare l'ordine con l'ID più alto
        String query = "DELETE FROM \"Ordine\" WHERE id = (SELECT MAX(id) FROM \"Ordine\")";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int rowsAffected = statement.executeUpdate();  // Esegui la cancellazione
            if (rowsAffected > 0) {
                System.out.println("Ultimo ordine rimosso con successo.");
            } else {
                System.out.println("Nessun ordine da rimuovere.");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la rimozione dell'ultimo ordine: "+e.getMessage());
        }
    }

    public void aggiorna(int id_ordine, Map<String, Object> criterio) {
        ObjectDAO objectDAO = new ObjectDAO();
        objectDAO.aggiorna(id_ordine, "Ordine", criterio);
    }

    public Ordine getById(int idOrdine) {
        String query = "SELECT * FROM \"Ordine\" WHERE id = ?";
        Ordine ordine = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idOrdine);

            ResultSet resultSet= statement.executeQuery();
            if(resultSet.next()){
                ordine = new Ordine(resultSet.getInt("id"), resultSet.getInt("cliente"),
                        resultSet.getString("piante"), resultSet.getString("stato"),
                        resultSet.getString("dataConsegna"));
            }else{
                System.out.println("Nessun ordine trovato con id "+ idOrdine);
            }


        } catch (SQLException e) {
            System.err.println("Errore durante il pagamenti dell'ordine: " + e.getMessage());
        }
        return ordine;
    }

    public void posiziona(Ordine ordine) {
        ObjectDAO objectDAO = new ObjectDAO();
        Map<String, Object> criteri = new HashMap<>();
        criteri.put("stato", "posizionato" );
        objectDAO.aggiorna(ordine.getId(), "Ordine", criteri);
    }

    public void aggiornaOrdine(Ordine ordine) throws SQLException {
        String query = "UPDATE \"Ordine\" " +
                "SET cliente = ?, dataConsegna = ?, piante = ?, descrizione = ?, totale = ?, stato = ? " +
                "WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Assegna i parametri alla query
            statement.setInt(1, ordine.getCliente());
            //TODO utilizzare logica DATE di Sql in Ordine ecc...
            statement.setString(2, ordine.getStringDataConsegna());
            statement.setObject(3, ordine.getPiante());  // Assicurati di usare il tipo giusto, eventualmente serializzando la lista di piante
            statement.setString(4, ordine.getDescrizione());
            statement.setDouble(5, ordine.getTotale());
            statement.setString(6, ordine.getStato());
            statement.setInt(7, ordine.getId());

            int rowsUpdated = statement.executeUpdate();
             /*
            if (rowsUpdated > 0) {
                System.out.println("Ordine aggiornato correttamente. Pagamento effettuato.");
            } else {
                System.out.println("Ordine non trovato o non aggiornato.");
            }*/
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento dell'ordine: " + e.getMessage());
            throw e;  // Rilancia l'eccezione per la gestione a livello superiore
        }
    }

}
