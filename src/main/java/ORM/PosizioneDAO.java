package main.java.ORM;

import main.java.DomainModel.Impianto.Posizione;
import main.java.DomainModel.Impianto.Spazio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PosizioneDAO {

    private Connection connection;

    public  PosizioneDAO(){
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public ArrayList<Posizione> getPosizioni(int idSpazio) {
        ArrayList<Posizione> posizioni = new ArrayList<>();
        String query = "SELECT * FROM \"Posizione\" WHERE spazio_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idSpazio);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    posizioni.add(new Posizione(id));
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il completamento dello spazio: " + e.getMessage());
        }

        return posizioni;
    }


    public void creaPosizione(int idSpazio) {
        String query = "INSERT INTO \"Posizione\" (assegnata, spazio) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, false);
            statement.setInt(2, idSpazio);
            statement.executeUpdate();
            System.out.println("Posizione creata correttamente");
        } catch (SQLException e) {
            System.err.println("Errore durante la creazione della posizione: " + e.getMessage());
        }
    }

    public void rimuoviPosizione(int idPosizione) {
        String query = "DELETE FROM \"Posizione\" WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPosizione);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Posizione rimosso con successo.");
            } else {
                System.out.println("Errore durante la rimozione della posizione. Nessuna posizione trovato con l'ID specificato.");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la rimozione della posizione: " + e.getMessage());
        }
    }

    public void visualizzaPosizioni(int idSpazio) {
        String query = "SELECT * FROM \"Posizione\" WHERE spazio = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idSpazio);

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
            System.err.println("Errore durante la visualizzazione delle posizioni: " + e.getMessage());
        }
    }

    public void monitoraPosizione(int idPosizione) {
        ///TODO un sacco di robba su
    }
    public void modificaPosizione(int idPosizione, String query, String valore, String attributo) {
        //FIXMe dai che attributo non ha molto senso...

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            if ("assegnata".equalsIgnoreCase(attributo)) {
                pstmt.setBoolean(1, Boolean.parseBoolean(valore));
            } else {
                pstmt.setInt(1, Integer.parseInt(valore));
            }
            pstmt.setInt(2, idPosizione);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Record aggiornato con successo.");
            } else {
                System.out.println("Nessun record trovato con l'id specificato.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void liberaPosizioni(List<Integer> posizioni) {
        String updateQuery = "UPDATE \"Posizione\" SET assegnata = false WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            for (Integer posizione : posizioni) {
                pstmt.setInt(1, posizione);
                pstmt.executeUpdate();
            }
            //System.out.println("Posizioni aggiornate con successo.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Posizione> getNPosizioni(int nPosizioni) {
        String query = "SELECT * FROM \"Posizione\" WHERE assegnata = false";
        ArrayList<Posizione> posizioni = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            int i = 0;
            //FIXME WTF i non viene incrementato
            while(resultSet.next() & i<nPosizioni) {
                int id = resultSet.getInt("id");
                posizioni.add(new Posizione(id));
            }

        } catch (SQLException e) {
            System.err.println("Errore durante la visualizzazione delle posizioni: " + e.getMessage());
        }
        return posizioni;
    }


    public boolean riserva(int i) {
        String query = "SELECT COUNT(*) FROM \"Posizione\" WHERE (assegnata) = false";
        boolean posizioni = false;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            posizioni = statement.execute();
        } catch (SQLException e) {
            System.err.println("Errore durante la visualizzazione delle posizioni: " + e.getMessage());
        }
        return posizioni;
    }

    public List<Integer> occupa(int nPiante) {
        String query = "SELECT * FROM \"Posizione\" WHERE assegnata = false";
        List<Integer> posizioni = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next() & posizioni.size()<nPiante) {
                int id = resultSet.getInt("id");
                posizioni.add(id);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la sistemazione delle posizioni: " + e.getMessage());
        }
        return posizioni;
    }
}
