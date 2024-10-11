package main.java.ORM;

import main.java.DomainModel.Impianto.IgrometroTerra;
import main.java.DomainModel.Impianto.Irrigatore;
import main.java.DomainModel.Impianto.Posizione;

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

    public ArrayList<Posizione> getPosizioniBySettore(int idAmbiente) {
        ArrayList<Posizione> posizioni = new ArrayList<>();
        String query = "SELECT * FROM \"Posizione\" WHERE settore = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idAmbiente);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    posizioni.add(new Posizione(id, new Irrigatore(resultSet.getInt("irrigatore")),
                            new IgrometroTerra(resultSet.getInt("igrometroterreno")),
                            resultSet.getBoolean("assegnata"), resultSet.getBoolean("occupata")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il completamento dello ambiente: " + e.getMessage());
        }

        return posizioni;
    }


    public void creaPosizione(int idAmbiente) {
        String query = "INSERT INTO \"Posizione\" (assegnata, ambiente) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, false);
            statement.setInt(2, idAmbiente);
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

    public void visualizzaPosizioni(int idAmbiente) {
        String query = "SELECT * FROM \"Posizione\" WHERE ambiente = ?";

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

    public ArrayList<Posizione> getNPosizioniNonAssegnate(int nPosizioni) {
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


    public boolean verificaNonAssegnate(int i) {
        String query = "SELECT COUNT(*) FROM \"Posizione\" WHERE assegnata = false";
        boolean flag = false;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();  // Esegui la query e ottieni il ResultSet
            if (resultSet.next()) {  // Sposta il cursore alla prima riga
                int count = resultSet.getInt(1);  // Ottieni il risultato della colonna COUNT(*)
                flag = count >= i;  // Verifica se il numero di posizioni non assegnate è almeno i
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la visualizzazione delle posizioni: " + e.getMessage());
        }

        return flag;  // Ritorna true se ci sono abbastanza posizioni non assegnate, altrimenti false
    }


    public ArrayList<Posizione> occupa(int nPiante) {
        String selectQuery = "SELECT * FROM \"Posizione\" WHERE assegnata = ? AND occupata = ?";
        String updateQuery = "UPDATE \"Posizione\" SET occupata = ? WHERE id = ?";
        ArrayList<Posizione> posizioni = new ArrayList<>();

        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

            // Imposta i parametri per la query di selezione
            selectStatement.setBoolean(1, true);
            selectStatement.setBoolean(2, false);

            // Esegui la query di selezione
            ResultSet resultSet = selectStatement.executeQuery();

            // Raccogli le posizioni disponibili e limitale a nPiante
            while (resultSet.next() && posizioni.size() < nPiante) {
                posizioni.add(new Posizione(resultSet.getInt("id"), new Irrigatore(resultSet.getInt("irrigatore")),
                        new IgrometroTerra(resultSet.getInt("igrometroTerreno")), resultSet.getBoolean("assegnata"),
                        true));
                updateStatement.setBoolean(1, true);  // Imposta occupata a true
                updateStatement.setInt(2, resultSet.getInt("id"));        // Aggiorna la posizione con l'id specifico
                updateStatement.executeUpdate();      // Esegui l'aggiornamento
            }


        } catch (SQLException e) {
            System.err.println("Errore durante la sistemazione delle posizioni: " + e.getMessage());
        }

        return posizioni;  // Ritorna la lista degli ID delle posizioni aggiornate
    }




    public void assegna(int nPiante) {
        String query = "UPDATE \"Posizione\" "
                + "SET assegnata = true "
                + "WHERE id IN (SELECT id FROM \"Posizione\" WHERE (assegnata, occupata) = (false, false) LIMIT ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, nPiante); // Imposta il limite di posizioni da aggiornare
            int rowsUpdated = statement.executeUpdate(); // Esegui l'update
            System.out.println("Aggiornate " + rowsUpdated + " posizioni.");
        } catch (SQLException e) {
            System.err.println("Errore durante la sistemazione delle posizioni: " + e.getMessage());
        }
    }

    public void liberaUltime(int i) {
        String query = "UPDATE \"Posizione\" SET assegnata = false "
                + "WHERE id IN (SELECT id FROM \"Posizione\" WHERE assegnata = true "
                + "ORDER BY id DESC LIMIT ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, i); // Imposta il numero di posizioni da liberare
            int rowsAffected = statement.executeUpdate(); // Esegui l'update
            if (rowsAffected > 0) {
                System.out.println("Liberate " + rowsAffected + " posizioni.");
            } else {
                System.out.println("Nessuna posizione da liberare.");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la liberazione delle posizioni: " + e.getMessage());
        }
    }

}
