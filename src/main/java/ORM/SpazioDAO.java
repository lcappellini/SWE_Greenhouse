package main.java.ORM;

import main.java.DomainModel.Impianto.Spazio;

import java.sql.*;
import java.util.ArrayList;

public class SpazioDAO {

    private Connection connection;

    public  SpazioDAO(){
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }


    public void creaSpazio(int idAmbiente, int nPosizioniMax) {
        String insertSpazioSQL = "INSERT INTO \"Spazio\" (ambiente_id, nPosizioniMax) VALUES (?, ?)";

        try (PreparedStatement insertStatement = connection.prepareStatement(insertSpazioSQL)) {
            // Assegna una posizione. Potrebbe essere calcolata o generata in qualche modo.

            insertStatement.setInt(1, idAmbiente);
            insertStatement.setInt(2, nPosizioniMax);

            insertStatement.executeUpdate();
            System.out.println("Spazio creato correttamente e associato all'ambiente con ID: " + idAmbiente);

        } catch (SQLException e) {
            System.err.println("Errore durante la creazione dello spazio: " + e.getMessage());
        }
    }


    public void visualizzaSpazi(int idAmbiente) {
        //FIXME Qui c'è da far vedere lo Spazio con i suoi attributi e gli ID delle posizioni che ha
        String query = "SELECT * FROM \"Spazio\" WHERE ambiente_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idAmbiente);

            ResultSet resultSet = statement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Stampiamo l'intestazione
            System.out.println("+--------+------------+-------------------+");
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
            System.err.println("Errore durante la visualizzazione degli spazi: " + e.getMessage());
        }
    }


    public void rimuoviSpazio(int idSpazio) {
        String query = "DELETE FROM \"Spazio\" WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idSpazio);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Spazio rimosso con successo.");
            } else {
                System.out.println("Errore durante la rimozione dello spazio. Nessuno spazio trovato con l'ID specificato.");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la rimozione dello spazio: " + e.getMessage());
        }
    }


    public void monitoraSpazio(int idSpazio) {
        //TODO monitoraggio dello Spazio con aggiornamento ogni secondo (1 ora in simulazione) dove verranno
        //TODO [...] mostrate i parametri della temperatura , percentuale di luce e di umidità. Inoltre mostrerà,
        //TODO [...] in caso siano accesi, il valore di temperatura e umidità impostato dal climatizzatore e se
        //TODO [...] la lampada sia accesa
    }



    public ArrayList<Spazio> completaAmbiente(int idAmbiente) {
        String query = "SELECT * FROM \"Spazio\" WHERE ambiente_id = ?";
        ArrayList<Spazio> spazi = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idAmbiente);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int nPosizioniMax = resultSet.getInt("nPosizioniMax");
                    spazi.add(new Spazio(id, nPosizioniMax));
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il completamento dell'ambiente: " + e.getMessage());
        }

        return spazi;
    }


    public Spazio getSpazio(int idSpazio) {
        String query = "SELECT * FROM \"Spazio\" WHERE id = ?";
        Spazio spazio = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idSpazio);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int nPosMax = resultSet.getInt("nPosizioniMax");
                    spazio = new Spazio(idSpazio, nPosMax);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca dello spazio: " + e.getMessage());
        }

        return spazio;
    }


    public void visualizzaSpazio() {
    }
}
