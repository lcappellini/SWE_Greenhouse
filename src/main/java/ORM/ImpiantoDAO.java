package main.java.ORM;

import main.java.DomainModel.Impianto.Operatore;
import main.java.DomainModel.Ordine;
import main.java.DomainModel.Pianta.Pianta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ImpiantoDAO {

    private Connection connection;

    public ImpiantoDAO() {

        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    public boolean verificaDisponibilita(int nPiante) {
        String query = "SELECT COUNT(*) FROM Posizioni WHERE assegnata = 0";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                int posizioniNonAssegnate = resultSet.getInt(1);
                return nPiante <= posizioniNonAssegnate;
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la verifica della disponibilità: " + e.getMessage());
        }
        return false; // In caso di errore o se non ci sono risultati
    }

    public void assegnaPosizionamento(Operatore operatore){
        //String query = "SELECT "
    }




}

