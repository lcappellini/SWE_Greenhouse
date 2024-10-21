package main.java.ORM;

import main.java.DomainModel.Impianto.IgrometroTerra;
import main.java.DomainModel.Impianto.Irrigatore;
import main.java.DomainModel.Impianto.Posizione;
import main.java.DomainModel.Ordine;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PosizioneDAO {

    // Istanza privata statica dell'oggetto OrdineDAO
    private static PosizioneDAO instance;

    private Connection connection;

    public PosizioneDAO(){
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static PosizioneDAO getInstance() {
        if (instance == null) {
            instance = new PosizioneDAO(); // Istanza creata solo la prima volta
        }
        return instance;
    }

    public ArrayList<Posizione> getPosizioniBySettore(int idAmbiente) {
        ArrayList<Posizione> posizioni = new ArrayList<>();
        String query = "SELECT * FROM \"Posizione\" WHERE settore = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idAmbiente);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    posizioni.add(new Posizione(id, new Irrigatore(resultSet.getInt("irrigatore")),
                            new IgrometroTerra(resultSet.getInt("igrometroterreno")),
                            resultSet.getBoolean("assegnata"), resultSet.getBoolean("occupata")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il completamento dello ambiente: " + e.getMessage());
        }

        return posizioni;
    }

    public void monitoraPosizione(int idPosizione) {
        ///TODO un sacco di robba su
    }

    public void modificaPosizione(int idPosizione, String query, String valore, int index_attr) {
        //FIXMe dai che attributo non ha molto senso...
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            if (index_attr == 1) {
                pstmt.setBoolean(1, Boolean.parseBoolean(valore));
            } else {
                pstmt.setInt(1, Integer.parseInt(valore));
            }
            pstmt.setInt(2, idPosizione);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Record aggiornato con successo.");
            } else {
                System.out.println("Nessun record trovato con l'id specificato.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean verificaNonAssegnate(int i) {
        String query = "SELECT COUNT(*) FROM \"Posizione\" WHERE assegnata = false";
        boolean flag = false;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();  // Esegui la query e ottieni il ResultSet
            if (resultSet.next()) {  // Sposta il cursore alla prima riga
                int count = resultSet.getInt(1);  // Ottieni il risultato della colonna COUNT(*)
                flag = count >= i;  // Verifica se il numero di posizioni non assegnate è almeno i
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la visualizzazione delle posizioni: " + e.getMessage());
        }

        return flag;  // Ritorna true se ci sono abbastanza posizioni non assegnate, altrimenti false
    }

    public ArrayList<Posizione> occupa(int nPiante) {
        String selectQuery = "SELECT * FROM \"Posizione\" WHERE assegnata = ? AND occupata = ?";
        String updateQuery = "UPDATE \"Posizione\" SET occupata = ? WHERE id = ?";
        ArrayList<Posizione> posizioni = new ArrayList<>();

        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

            // Imposta i parametri per la query di selezione
            selectStatement.setBoolean(1, true);
            selectStatement.setBoolean(2, false);

            // Esegui la query di selezione
            ResultSet resultSet = selectStatement.executeQuery();

            // Raccogli le posizioni disponibili e limitale a nPiante
            while (resultSet.next() && posizioni.size() < nPiante) {
                posizioni.add(new Posizione(resultSet.getInt("id"), new Irrigatore(resultSet.getInt("irrigatore")),
                        new IgrometroTerra(resultSet.getInt("igrometroTerreno")), resultSet.getBoolean("assegnata"),
                        true));
                updateStatement.setBoolean(1, true);  // Imposta occupata a true
                updateStatement.setInt(2, resultSet.getInt("id"));        // Aggiorna la posizione con l'id specifico
                updateStatement.executeUpdate();      // Esegui l'aggiornamento
            }


        } catch (SQLException e) {
            System.err.println("Errore durante la sistemazione delle posizioni: " + e.getMessage());
        }

        return posizioni;  // Ritorna la lista degli ID delle posizioni aggiornate
    }


    public void assegna(int nPiante) {
        String query = "UPDATE \"Posizione\" "
                + "SET assegnata = true "
                + "WHERE id IN (SELECT id FROM \"Posizione\" WHERE (assegnata, occupata) = (false, false) LIMIT ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, nPiante); // Imposta il limite di posizioni da aggiornare
            int rowsUpdated = statement.executeUpdate(); // Esegui l'update
            System.out.println("Assegnate " + rowsUpdated + " posizioni.");
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Errore durante la sistemazione delle posizioni: " + e.getMessage());
        }
    }

    public Posizione getById(int posizioneId) {
        Map<String, Object> m = new HashMap<>();
        m.put("id", posizioneId);
        Posizione p = get(m).get(0);
        return p;
    }

    public ArrayList<Posizione> get(Map<String, Object> criteri) {
        StringBuilder query = new StringBuilder("SELECT * FROM \"Posizione\"");

        if(criteri != null && !criteri.isEmpty()) {
            query.append(" WHERE ");
            for(String key : criteri.keySet()) {
                query.append(key).append(" = ? AND ");
            }
            query.setLength(query.length() - 5);  // Rimuove l'ultimo " AND "

        }

        IgrometroTerraDAO igrometroTerraDAO = new IgrometroTerraDAO();
        IrrigatoreDAO irrigatoreDAO = new IrrigatoreDAO();

        ArrayList<Posizione> posizioni = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(query.toString())){
            if(criteri != null && !criteri.isEmpty()) {
                int paramIndex = 1;
                for (Object value : criteri.values()) {
                    statement.setObject(paramIndex, value);
                    paramIndex++;
                }
            }
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                Irrigatore irrigatore = irrigatoreDAO.getById(resultSet.getInt("irrigatore"));
                IgrometroTerra igrometroTerra = igrometroTerraDAO.getById(resultSet.getInt("igrometroterreno"));
                boolean assegnata = resultSet.getBoolean("assegnata");
                boolean occupata = resultSet.getBoolean("occupata");
                posizioni.add(new Posizione(id, irrigatore, igrometroTerra, assegnata, occupata));
            }
        }catch (SQLException ignored){
        }
        return posizioni;
    }

    public void aggiorna(int id, Map<String, Object> m) {
        if (m == null || m.isEmpty()) {
            System.out.println("Nessun dato da aggiornare.");
            return; // Non ci sono dati da aggiornare
        }

        StringBuilder query = new StringBuilder("UPDATE \"Posizione\" SET ");

        // Costruzione della query di aggiornamento
        for (String key : m.keySet()) {
            query.append(key).append(" = ?, ");
        }
        query.setLength(query.length() - 2); // Rimuove l'ultima virgola e spazio
        query.append(" WHERE id = ?"); // Aggiungi la condizione per l'ID

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            int paramIndex = 1;

            // Imposta i valori dalla mappa
            for (Object value : m.values()) {
                statement.setObject(paramIndex++, value);
            }

            // Imposta l'ID dell'elemento da aggiornare come ultimo parametro
            statement.setInt(paramIndex, id);

            // Esegui l'aggiornamento
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Posizione[" + id+"] aggiornata.");
            } else {
                System.out.println("Nessun aggiornamento effettuato per ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento della posizione: " + e.getMessage());
        }
    }

}
