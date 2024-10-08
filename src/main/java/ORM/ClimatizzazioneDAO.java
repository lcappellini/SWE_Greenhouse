package main.java.ORM;

import main.java.DomainModel.Impianto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClimatizzazioneDAO extends AttuatoreDAO {
    private Connection connection;

    public ClimatizzazioneDAO(){

        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    /*@Override
    public void registraAzione(Attuatore climatizzatore, String data){
        String query = "UPDATE \"Climatizzazione\"  SET working = ? WHERE id = ?";
        String queryOP = "INSERT INTO \"Operazione\" (tipoAttuatore, idAttuatore, descrizione) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setBoolean(1, climatizzatore.isWorking());
            pstmt.setInt(2, climatizzatore.getId());

            pstmt.executeUpdate();
            // 2. Inserisci l'operazione nella tabella delle operazioni degli attuatori
            String insertOperazioneSQL = "INSERT INTO \"Operazione\" (tipoAttuatore, idAttuatore, descrizione, data) VALUES (?, ?, ?, ?)";
            PreparedStatement psInsert = connection.prepareStatement(insertOperazioneSQL);
            psInsert.setString(1, climatizzatore.tipoAttuatore());
            psInsert.setInt(2, climatizzatore.getId());
            psInsert.setString(3, climatizzatore.getLavoro());
            psInsert.setString(4, data);
            psInsert.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }*/
    public Climatizzazione getById(int id){
        String query = "SELECT * FROM \"Climatizzazione\" WHERE id = ?";
        Climatizzazione attuatore = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Ottieni i dati comuni a tutti gli attuatori
                int attuatoreId = resultSet.getInt("id");
                boolean working = resultSet.getBoolean("working");
                int temperature = resultSet.getInt("temperaturarichiesta");
                attuatore = new Climatizzazione(id, working, temperature);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca dell'attuatore: " + e.getMessage());
        }

        return attuatore;
    }
}
