package main.java.ORM;

import main.java.DomainModel.Impianto.IgrometroTerra;
import main.java.DomainModel.Impianto.Irrigatore;
import main.java.DomainModel.Impianto.Posizione;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
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

    public boolean verificaNonAssegnate(int i) {
        String query = "SELECT COUNT(*) FROM \"Posizione\" WHERE assegnata = false";
        boolean flag = false;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();  // Esegui la query e ottieni il ResultSet
            if (resultSet.next()) {  // Sposta il cursore alla prima riga
                int count = resultSet.getInt(1);  // Ottieni il risultato della colonna COUNT(*)
                return count >= i;  // Verifica se il numero di posizioni non assegnate è almeno i
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la verifica delle posizioni: " + e.getMessage());
        }

        return false;  // Ritorna true se ci sono abbastanza posizioni non assegnate, altrimenti false
    }

    public int getNNonAssegnate() {
        String query = "SELECT COUNT(*) FROM \"Posizione\" WHERE assegnata = false";
        boolean flag = false;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count;
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il conteggio delle posizioni: " + e.getMessage());
        }

        return -1;
    }

    public int assegna(int nPiante) {
        String query = "UPDATE \"Posizione\" "
                + "SET assegnata = true "
                + "WHERE id IN (SELECT id FROM \"Posizione\" WHERE (assegnata, occupata) = (false, false) LIMIT ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, nPiante); // Imposta il limite di posizioni da aggiornare
            int rowsUpdated = statement.executeUpdate(); // Esegui l'update
            connection.commit();
            return rowsUpdated;
        } catch (SQLException e) {
            System.err.println("Errore durante la sistemazione delle posizioni: " + e.getMessage());
        }
        return 0;
    }

    public Posizione getById(int posizioneId) {
        Map<String, Object> m = new HashMap<>();
        m.put("id", posizioneId);
        return get(m).get(0);
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
                boolean assegnata = resultSet.getBoolean("assegnata");
                boolean occupata = resultSet.getBoolean("occupata");
                Irrigatore irrigatore = (Irrigatore)attuatoreDAO.getById(resultSet.getInt("irrigatore"));
                IgrometroTerra igrometroTerra = (IgrometroTerra)sensoreDAO.getById(resultSet.getInt("igrometroterra"));
                posizioni.add(new Posizione(id, assegnata, occupata, igrometroTerra, irrigatore));
            }
        }catch (SQLException ignored){
        }
        return posizioni;
    }

    public int aggiorna(int id, Map<String, Object> m) {
        if (m == null || m.isEmpty()) {
            return 0;
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
            return statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento della posizione: " + e.getMessage());
        }
        return -1;
    }

}
