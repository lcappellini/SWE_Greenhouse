package main.java.ORM;

import main.java.DomainModel.Impianto.Posizionamento;

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

    public void inserisciPosizionamenti(ArrayList<Posizionamento> posizionamenti) throws SQLException {
        String queryInserimento = "INSERT INTO \"Posizionamento\" (pianta, posizione, ordine, operatore) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statementInserimento = connection.prepareStatement(queryInserimento)) {
            connection.setAutoCommit(false);  // Inizio della transazione

            for (Posizionamento p : posizionamenti) {
                statementInserimento.clearParameters();
                statementInserimento.setInt(1, p.getPianta().getId());  // Considera di usare l'indice i o un'altra logica appropriata
                statementInserimento.setInt(2, p.getPosizione().getId());
                statementInserimento.setInt(3, p.getOrdine().getId());
                statementInserimento.setInt(4, p.getOperatore().getId());
                statementInserimento.executeUpdate();
            }

            connection.commit();  // Commit della transazione
        } catch (SQLException e) {
            connection.rollback();  // Rollback della transazione in caso di errore
            System.err.println("Errore durante la creazione dei posizionamenti: " + e.getMessage());
        }
    }



    public ArrayList<Integer> liberaPosizionamenti(int idOrdine) {
        ArrayList<Integer> liberati = new ArrayList<>();

        String selectQuery = "SELECT * FROM \"Posizionamento\" WHERE ordine = ?";
        String deleteQuery = "DELETE FROM \"Posizionamento\" WHERE ordine = ?";

        try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
            selectStmt.setInt(1, idOrdine);
            ResultSet rs = selectStmt.executeQuery();
            while (rs.next()) {liberati.add(rs.getInt("posizione"));}
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

        return liberati;
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

    public ArrayList<Integer> ritira(int id) {
        ArrayList<Integer> id_piante = new ArrayList<>();
        String selectQuery = "SELECT pianta FROM \"Posizionamento\" WHERE ordine = ?";

        try(PreparedStatement s = connection.prepareStatement(selectQuery)){
            ResultSet rs = s.executeQuery();
            while (rs.next()) {id_piante.add(rs.getInt("pianta"));}
        }catch(SQLException e){
            e.printStackTrace();
        }
        return id_piante;
    }
}
