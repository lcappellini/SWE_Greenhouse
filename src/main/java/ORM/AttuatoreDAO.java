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
            String updateAttuatoreSQL = "UPDATE \"" + attuatore.tipoAttuatore() + "\" SET working = ? WHERE id = ?";
            PreparedStatement psUpdate = connection.prepareStatement(updateAttuatoreSQL);
            psUpdate.setBoolean(1, attuatore.isWorking());
            psUpdate.setInt(2, attuatore.getId());
            psUpdate.executeUpdate();

            // 2. Inserisci l'operazione nella tabella delle operazioni degli attuatori
            String insertOperazioneSQL = "INSERT INTO \"Operazione\" (tipoAttuatore, idAttuatore, descrizione, data) VALUES (?, ?, ?, ?)";
            PreparedStatement psInsert = connection.prepareStatement(insertOperazioneSQL);
            psInsert.setString(1, attuatore.tipoAttuatore());
            psInsert.setInt(2, attuatore.getId());
            psInsert.setString(3, attuatore.getLavoro());
            psInsert.setString(4, data);
            psInsert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

void getAttuatoreById(int id){

};

public void visualizzaLiberi(String tipoAttuatore) {
    StringBuilder query = new StringBuilder("SELECT id FROM \"");
    query.append(tipoAttuatore);
    query.append("\" WHERE working = ?");

    try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {
        // Imposta il valore per il parametro occupato a false (posizioni libere)
        stmt.setBoolean(1, false);

        // Esegui la query
        ResultSet resultSet = stmt.executeQuery();
        StringBuilder nd = new StringBuilder(tipoAttuatore).append(" selezionabili:");
        // Stampa l'intestazione della lista
        System.out.println(nd.toString());
        System.out.println("------------------------------------");

        // Itera sui risultati e stampa solo gli ID
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            System.out.println(id);  // Stampa solo l'ID
        }
    } catch (SQLException e) {
        System.err.println("Errore durante la visualizzazione dei record liberi: " + e.getMessage());
    }
}


public Attuatore getById(int id){ return null; } ;

// Metodo per recuperare un oggetto Climatizzazione dal database usando il suo ID
public Climatizzazione getClimatizzazioneById(int id) throws SQLException {
    String query = "SELECT * FROM \"Climatizzazione\" WHERE id = ?";
    Climatizzazione climatizzazione = null;

    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            climatizzazione = new Climatizzazione(rs.getInt("id"),rs.getBoolean("working"));
            // Popolare altre proprietà di Climatizzazione se necessario
        }
    }catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return climatizzazione;
}

// Metodo per recuperare un oggetto Lampada dal database usando il suo ID
public Lampada getLampadaById(int id) throws SQLException {
    String query = "SELECT * FROM \"Lampada\" WHERE id = ?";
    Lampada lampada = null;

    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            lampada = new Lampada(rs.getInt("id"), rs.getBoolean("working"), true);
            // Popolare altre proprietà di Lampada se necessario
        }
    }
    return lampada;
}

// Metodo per aggiornare lo stato (working) dell'attuatore
public void aggiornaStatoAttuatore(Attuatore attuatore) throws SQLException {
    String query = "UPDATE Attuatore SET working = ? WHERE id = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setBoolean(1, attuatore.isWorking());
        stmt.setInt(2, attuatore.getId());
        stmt.executeUpdate();
    }
}

// Chiusura della connessione quando il DAO non è più necessario
public void closeConnection() throws SQLException {
    if (connection != null && !connection.isClosed()) {
        connection.close();
    }
}
}



