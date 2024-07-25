package main.java.ORM;

import main.java.DomainModel.Impianto.Posizione;
import main.java.DomainModel.Ordine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PosizionamentoDAO {

    private Connection connection;

    public PosizionamentoDAO() {
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    public void creaPosizionamento(Ordine ordine, List<Integer> posizioni, int idOperatore) throws SQLException, ClassNotFoundException {
        try {
                // Aggiungi un posizionamento per ogni pianta nell'ordine
                String queryInserimento = "INSERT INTO \"Posizionamento\" (pianta, posizione, ordine, operatore) VALUES (?, ?, ?)";
                try (PreparedStatement statementInserimento = connection.prepareStatement(queryInserimento)) {
                    int i = 0;
                    for (Integer posizione : posizioni) {
                        statementInserimento.setString(1, ordine.getTipoPianta(0));
                        statementInserimento.setInt(2, posizione);
                        statementInserimento.setInt(3, ordine.getId());
                        statementInserimento.setInt(4, idOperatore);
                        statementInserimento.executeUpdate();
                        i++;
                    }
                    System.out.println("Posizionamenti creati con successo!");
                }

        } catch (SQLException e) {
            System.err.println("Errore durante la creazione dei posizionamenti: " + e.getMessage());
        }
    }


    public List<Integer> liberaPosizionamenti(int idOrdine) {
        List<Integer> posizioniLiberate = new ArrayList<>();

        String selectQuery = "SELECT posizione FROM \"Posizionamento\" WHERE ordine = ?";
        String deleteQuery = "DELETE FROM \"Posizionamento\" WHERE ordine = ?";

        try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
            selectStmt.setInt(1, idOrdine);
            ResultSet rs = selectStmt.executeQuery();

            while (rs.next()) {
                int idPosizione = rs.getInt("posizione");
                posizioniLiberate.add(idPosizione);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
            deleteStmt.setInt(1, idOrdine);
            int affectedRows = deleteStmt.executeUpdate();
            if (affectedRows > 0) {
                //System.out.println("Righe eliminate con successo: " + affectedRows);
            } else {
                System.out.println("Nessuna riga trovata con l'id_Ordine specificato.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posizioniLiberate;
    }

    public void visualizzaPosizionamenti(int idOridne) {
        String query = "SELECT * FROM \"Posizionamento\" WHERE ordine = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idOridne);

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
}
