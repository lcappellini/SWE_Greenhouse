package main.java.ORM;

import java.sql.*;

public class SpazioDAO {

    private Connection connection;

    public  SpazioDAO(){
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }


    public void creaSpazio(int idAmbiente, int nPosizioniMax, int nSpaziMax) {
        // Controlla se il numero di spazi associati all'ambiente è inferiore al limite massimo consentito
        if (contaSpazi(idAmbiente) < nPosizioniMax) {
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
        } else {
            System.out.println("Impossibile creare lo spazio. Numero massimo di spazi raggiunto.");
        }
    }

    public int contaSpazi(int idAmbiente) {
        String countSpaziSQL = "SELECT COUNT(*) FROM \"Spazio\" WHERE ambiente_id = ?";
        int count = 0;

        try (PreparedStatement countStatement = connection.prepareStatement(countSpaziSQL)) {
            countStatement.setInt(1, idAmbiente);
            ResultSet resultSet = countStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il conteggio degli spazi: " + e.getMessage());
        }

        return count;
    }
    public void visualizzaSpazi() {
        String query = "SELECT * FROM Spazio";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
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
    public void visualizzaIdSpazi() {
        String query = "SELECT id FROM Spazio";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            System.out.println("ID degli spazi:");
            System.out.println("+--------+");
            while (resultSet.next()) {
                int idSpazio = resultSet.getInt("id");
                System.out.println("| " + idSpazio + " |");
            }
            System.out.println("+--------+");

        } catch (SQLException e) {
            System.err.println("Errore durante la visualizzazione degli ID degli spazi: " + e.getMessage());
        }
    }

}
