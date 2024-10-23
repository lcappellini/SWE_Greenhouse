package main.java.ORM;

import main.java.DomainModel.Impianto.*;

import java.sql.*;

public class AttuatoreDAO {

    protected Connection connection;

    public AttuatoreDAO(){
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void registraAzione(Attuatore attuatore, String data) {
        try {
            // 1. Aggiorna lo stato dell'attuatore nel database
            String updateAttuatoreSQL = "UPDATE \"Attuatore\" SET working = ? WHERE id = ?";
            PreparedStatement psUpdate = connection.prepareStatement(updateAttuatoreSQL);
            psUpdate.setBoolean(1, attuatore.isWorking());
            psUpdate.setInt(2, attuatore.getId());
            psUpdate.executeUpdate();

            // 2. Inserisci l'operazione nella tabella delle operazioni degli attuatori
            String insertOperazioneSQL = "INSERT INTO \"Operazione\" (tipoAttuatore, idAttuatore, descrizione, data) VALUES (?, ?, ?, ?)";
            PreparedStatement psInsert = connection.prepareStatement(insertOperazioneSQL);
            psInsert.setString(1, attuatore.getTipoAttuatore());
            psInsert.setInt(2, attuatore.getId());
            psInsert.setString(3, attuatore.getLavoro());
            psInsert.setString(4, data);
            psInsert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metodo per recuperare un oggetto Climatizzatore dal database usando il suo ID
    public Attuatore getById(int id) {
        String query = "SELECT * FROM \"Attuatore\" WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String tipoAttuatore = rs.getString("tipo");
                if (tipoAttuatore.equals("Lampada"))
                    return new Lampada(rs.getInt("id"), rs.getBoolean("working"));
                else if (tipoAttuatore.equals("Climatizzatore"))
                    return new Climatizzatore(rs.getInt("id"), rs.getBoolean("working"));
                else if (tipoAttuatore.equals("Irrigatore"))
                    return new Irrigatore(rs.getInt("id"), rs.getBoolean("working"));
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}



