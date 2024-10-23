package main.java.ORM;

import main.java.DomainModel.Impianto.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SettoreDAO {

    private Connection connection;
    public SettoreDAO(){
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public Settore getById(int idSettore)  {
        String query = "SELECT * FROM \"Settore\" WHERE id = ?";
        Settore settore = null;
        // Creiamo istanze dei DAO dei sensori e attuatori
        SensoreDAO sensoreDAO = new SensoreDAO();
        AttuatoreDAO attuatoreDAO = new AttuatoreDAO();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, idSettore);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Recuperiamo gli ID dei sensori e attuatori
                Termometro termometro = (Termometro)sensoreDAO.getById(rs.getInt("termometro"));
                IgrometroAria igrometroAria = (IgrometroAria)sensoreDAO.getById(rs.getInt("igrometroaria"));
                Fotosensore fotosensore = (Fotosensore)sensoreDAO.getById(rs.getInt("fotosensore"));

                Climatizzatore climatizzatore = (Climatizzatore)attuatoreDAO.getById(rs.getInt("climatizzatore"));
                Lampada lampada = (Lampada)attuatoreDAO.getById(rs.getInt("lampada"));

                // Creiamo una lista di posizioni (per ora potrebbe essere vuota o popolata successivamente)
                ArrayList<Posizione> posizioni = new ArrayList<>();

                // Costruiamo il settore con i sensori e attuatori
                settore = new Settore(idSettore, posizioni, termometro, igrometroAria, fotosensore, climatizzatore, lampada);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il visualizzane: " + e.getMessage());
        }
        return settore;
    }

    /*public Settore getSettoreBySpazio(int idSpazio, int index) {
        String query = "SELECT * FROM \"Settore\" WHERE spazio_id = ? LIMIT 1 OFFSET ?";
        Settore settore = null;
        SensoreDAO sensoreDAO = new SensoreDAO();
        AttuatoreDAO attuatoreDAO = new AttuatoreDAO();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idSpazio);  // Filtra per idSpazio
            statement.setInt(2, index - 1); // L'indice è usato per l'offset (0-based)

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Termometro termometro = (Termometro)sensoreDAO.getById(rs.getInt("termometro"));
                IgrometroAria igrometroAria = (IgrometroAria)sensoreDAO.getById(rs.getInt("igrometroaria"));
                Fotosensore fotosensore = (Fotosensore)sensoreDAO.getById(rs.getInt("fotosensore"));
                Climatizzatore climatizzatore = (Climatizzatore)attuatoreDAO.getById(rs.getInt("climatizzatore"));
                Lampada lampada = (Lampada)attuatoreDAO.getById(rs.getInt("lampada"));

                int settoreId = rs.getInt("id");

                ArrayList<Posizione> posizioni = new ArrayList<>();

                // Crea l'oggetto Settore
                settore = new Settore(settoreId, posizioni, termometro, igrometroAria,fotosensore, climatizzatore, lampada);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Errore durante la ricerca del settore: " + e.getMessage());
        }

        return settore;
    }*/

    public ArrayList<Settore> get(Map<String, Object> criteri) {
        StringBuilder query = new StringBuilder("SELECT * FROM \"Settore\"");

        // Aggiungi condizioni se ci sono criteri
        if (criteri != null && !criteri.isEmpty()) {
            query.append(" WHERE ");
            for (String key : criteri.keySet()) {
                query.append(key).append(" = ? AND ");
            }
            query.setLength(query.length() - 5);  // Rimuove l'ultimo " AND "
        }

        ArrayList<Settore> settori = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            // Imposta i parametri se ci sono criteri
            if (criteri != null && !criteri.isEmpty()) {
                int paramIndex = 1;
                for (Object value : criteri.values()) {
                    statement.setObject(paramIndex, value);
                    paramIndex++;
                }
            }

            SensoreDAO sensoreDAO = new SensoreDAO();
            AttuatoreDAO attuatoreDAO = new AttuatoreDAO();

            // Esegui la query e gestisci il ResultSet
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    // Recuperiamo gli ID dei sensori e attuatori
                    int idSettore = rs.getInt("id");
                    Termometro termometro = (Termometro)sensoreDAO.getById(rs.getInt("termometro"));
                    IgrometroAria igrometroAria = (IgrometroAria)sensoreDAO.getById(rs.getInt("igrometroaria"));
                    Fotosensore fotosensore = (Fotosensore)sensoreDAO.getById(rs.getInt("fotosensore"));

                    Climatizzatore climatizzatore = (Climatizzatore)attuatoreDAO.getById(rs.getInt("climatizzatore"));
                    Lampada lampada = (Lampada)attuatoreDAO.getById(rs.getInt("lampada"));

                    // Creiamo una lista di posizioni (per ora potrebbe essere vuota o popolata successivamente)
                    ArrayList<Posizione> posizioni = new ArrayList<>();

                    // Costruiamo il settore con i sensori e attuatori
                    Settore settore = new Settore(idSettore, posizioni, termometro, igrometroAria, fotosensore, climatizzatore, lampada);
                    settori.add(settore);
                }
            }
        } catch (SQLException e) {
            // Logga l'errore
            System.err.println("Errore durante il recupero delle piante: " + e.getMessage());
            e.printStackTrace(); // Mostra la traccia dello stack per una diagnostica più dettagliata
        }

        return settori;
    }

}
