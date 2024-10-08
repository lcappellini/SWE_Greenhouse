package main.java.ORM;

import main.java.DomainModel.Impianto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IrrigatoreDAO extends AttuatoreDAO{
    private Connection connection;

    public IrrigatoreDAO() {

        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }
    @Override
    public Irrigatore getById(int id){
        String query = "SELECT * FROM \"Irrigatore\" WHERE id = ?";
        Irrigatore attuatore = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Ottieni i dati comuni a tutti gli attuatori
                int attuatoreId = resultSet.getInt("id");
                boolean working = resultSet.getBoolean("working");
                //FIXME creare dei buoni costruttori su questa base

                // Ora creiamo l'oggetto specifico in base al tipoAttuatore
                attuatore = new Irrigatore(attuatoreId, working);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca dell'attuatore: " + e.getMessage());
        }

        return attuatore;
    }
    /*

    @Override
    public void registraAzione(Attuatore lampada, String data){
        String query = "UPDATE \"Lampada\"  SET working = ? WHERE id = ?";
        String queryOP = "INSERT INTO \"Operazione\" (tipoAttuatore, idAttuatore, descrizione) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setBoolean(1, lampada.isWorking());
            pstmt.setInt(2, lampada.getId());

            pstmt.executeUpdate();
            // 2. Inserisci l'operazione nella tabella delle operazioni degli attuatori
            String insertOperazioneSQL = "INSERT INTO \"Operazione\" (tipoAttuatore, idAttuatore, descrizione, data) VALUES (?, ?, ?, ?)";
            PreparedStatement psInsert = connection.prepareStatement(insertOperazioneSQL);
            psInsert.setString(1, lampada.tipoAttuatore());
            psInsert.setInt(2, lampada.getId());
            psInsert.setString(3, lampada.getLavoro());
            psInsert.setString(4, data);
            psInsert.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }*/

}