package main.java.ORM;

import main.java.DomainModel.Impianto.Sensore;

import main.java.DomainModel.Impianto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.*;

public class SensoreDAO {

    private Connection connection;

    public SensoreDAO() {
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public  void registraMisura(Sensore sensore){};

    // Metodo per recuperare un Termometro dal database usando il suo ID
    public Termometro getTermometroById(int id) throws SQLException {
        String query = "SELECT id FROM \"Termometro\" WHERE id = ?";
        Termometro termometro = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                termometro = new Termometro(rs.getInt("id"));
                // Popolare altre proprietà di Termometro se necessario
            }
        }catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return termometro;
    }

    // Metodo per recuperare un IgrometroAria dal database usando il suo ID
    public IgrometroAria getIgrometroAriaById(int id) throws SQLException {
        String query = "SELECT id FROM \"IgrometroAria\" WHERE id = ?";
        IgrometroAria igrometroAria = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                igrometroAria = new IgrometroAria(rs.getInt("id"));
                // Popolare altre proprietà di IgrometroAria se necessario
            }
        }catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return igrometroAria;
    }

    // Metodo per recuperare un Fotosensore dal database usando il suo ID
    public Fotosensore getFotosensoreById(int id) throws SQLException {
        String query = "SELECT id FROM \"Fotosensore\" WHERE id = ?";
        Fotosensore fotosensore = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                fotosensore = new Fotosensore(rs.getInt("id"));
                // Popolare altre proprietà di Fotosensore se necessario
            }
        }catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return fotosensore;
    }

    // Metodo per aggiornare il valore misurato del sensore
    public void aggiornaValoreSensore(Sensore<?> sensore) throws SQLException {
        //fixme
        String query = "UPDATE \"Sensore\" SET valore = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, sensore.getValore()); // sensore.getValore() potrebbe restituire int, float, ecc.
            stmt.setInt(2, sensore.getId());
            stmt.executeUpdate();
        }
    }

    // Chiusura della connessione quando il DAO non è più necessario
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public void visualizza(int id) {
    }
}
