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


    public void creaSettore(int idSpazio, int nPosizioniMax,
                            int idTermometro, int idFotosensore, int idIgrometroAria,
                            int idClimatizzazione, int idLampada) {
        String insertAmbienteSQL = "INSERT INTO \"Settore\" (spazio_id, termometro, " +
                "fotosensore, igrometroAria, climatizzazione, lampada) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement insertStatement = connection.prepareStatement(insertAmbienteSQL)) {
            // Assegna una posizione. Potrebbe essere calcolata o generata in qualche modo.

            insertStatement.setInt(1, idSpazio);
            insertStatement.setInt(2, idTermometro);
            insertStatement.setInt(3, idFotosensore);
            insertStatement.setInt(4, idIgrometroAria);
            insertStatement.setInt(5, idClimatizzazione);
            insertStatement.setInt(6, idLampada);

            insertStatement.executeUpdate();
            System.out.println("Ambiente creato correttamente e associato all'spazio con ID: " + idSpazio);

        } catch (SQLException e) {
            System.err.println("Errore durante la creazione dello ambiente: " + e.getMessage());
        }
    }


    public void visualizzaSettori(int idSpazio) {
        //FIXME Qui c'è da far vedere lo Ambiente con i suoi attributi e gli ID delle posizioni che ha
        String query = "SELECT * FROM \"Settore\" WHERE spazio_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idSpazio);

            ResultSet resultSet = statement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Stampiamo l'intestazione
            System.out.println("+--------+--ms1s----------+-------------------+");
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
            System.err.println("Errore durante la visualizzazione degli ambienti: " + e.getMessage());
        }
    }


    public void rimuoviSettore(int idSettore) {
        String query = "DELETE FROM \"Settore\" WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idSettore);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Ambiente rimosso con successo.");
            } else {
                System.out.println("Errore durante la rimozione dello ambiente. Nessuno ambiente trovato con l'ID specificato.");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la rimozione dello ambiente: " + e.getMessage());
        }
    }


    public void visualizza(int id_spazio){
        ObjectDAO dao = new ObjectDAO();
        Map<String, Integer> m= new HashMap<>();
        m.put("id_spazio", id_spazio);
        //dao.visualizza("Settore", m);
    }


    public Settore getById(int idSettore)  {
        String query = "SELECT * FROM \"Settore\" WHERE id = ?";
        Settore settore = null;
        // Creiamo istanze dei DAO dei sensori e attuatori
        SensoreDAO sensoreDAO = new SensoreDAO();
        //AttuatoreDAO attuatoreDAO = new AttuatoreDAO();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, idSettore);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Recuperiamo gli ID dei sensori e attuatori
                int termometroId = rs.getInt("termometro");
                int fotosensoreId = rs.getInt("fotosensore");
                int climatizzazioneId = rs.getInt("climatizzazione");
                int lampadaId = rs.getInt("lampada");
                int igrometroAriaId = rs.getInt("igrometroaria");

                // Recuperiamo gli oggetti sensori e attuatori usando i DAO
                Termometro termometro = new Termometro(termometroId);
                IgrometroAria igrometroAria = new IgrometroAria(igrometroAriaId);
                Fotosensore fotosensore = new Fotosensore(fotosensoreId);

                Climatizzazione climatizzazione = new Climatizzazione(climatizzazioneId);
                Lampada lampada = new Lampada(lampadaId);

                // Creiamo una lista di posizioni (per ora potrebbe essere vuota o popolata successivamente)
                ArrayList<Posizione> posizioni = new ArrayList<>();

                // Costruiamo il settore con i sensori e attuatori
                settore = new Settore(idSettore, posizioni, termometro, igrometroAria, fotosensore, climatizzazione, lampada);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il visualizzane: " + e.getMessage());
        }
        return settore;
    }
    public Settore getSettoreBySpazio(int idSpazio, int index) {
        String query = "SELECT * FROM \"Settore\" WHERE spazio_id = ? LIMIT 1 OFFSET ?";
        Settore settore = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idSpazio);  // Filtra per idSpazio
            statement.setInt(2, index - 1); // L'indice è usato per l'offset (0-based)

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Estrai i dati dal ResultSet
                int settoreId = resultSet.getInt("id");
                int termometroId = resultSet.getInt("termometro");
                int fotosensoreId = resultSet.getInt("fotosensore");
                int climatizzazioneId = resultSet.getInt("climatizzazione");
                int lampadaId = resultSet.getInt("lampada");
                int igrometroAriaId = resultSet.getInt("igrometroAria");

                // Ora dobbiamo creare il settore con i dati estratti
                Termometro termometro = new Termometro(termometroId);
                IgrometroAria igrometroAria = new IgrometroAria(igrometroAriaId);
                Fotosensore fotosensore = new Fotosensore(fotosensoreId);

                Climatizzazione climatizzazione = new Climatizzazione(climatizzazioneId);
                Lampada lampada = new Lampada(lampadaId);

                ArrayList<Posizione> posizioni = new ArrayList<>();

                // Crea l'oggetto Settore
                settore = new Settore(settoreId, posizioni, termometro, igrometroAria,fotosensore, climatizzazione, lampada);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Errore durante la ricerca del settore: " + e.getMessage());
        }

        return settore;
    }



    public void visualizzaAmbiente() {
    }
}
