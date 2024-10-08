package main.java.ORM;

import main.java.DomainModel.Impianto.Attuatore;
import main.java.DomainModel.Impianto.Operatore;
import main.java.DomainModel.Ordine;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class OperatoreDAO extends AttuatoreDAO{
    private Connection connection;

    public OperatoreDAO(){
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    public void misura(Operatore operatore, String data){
            String query = "UPDATE \"Operatore\"  SET working = ? WHERE id = ?";
            String queryOP = "INSERT INTO \"Operazione\" (tipoAttuatore, idAttuatore, descrizione) VALUES (?, ?, ?)";

                try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setBoolean(1, operatore.isWorking());
                pstmt.setInt(2, operatore.getId());

                pstmt.executeUpdate();
                // 2. Inserisci l'operazione nella tabella delle operazioni degli attuatori
                String insertOperazioneSQL = "INSERT INTO \"Operazione\" (tipoAttuatore, idAttuatore, descrizione, data) VALUES (?, ?, ?, ?)";
                PreparedStatement psInsert = connection.prepareStatement(insertOperazioneSQL);
                psInsert.setString(1, operatore.tipoAttuatore());
                psInsert.setInt(2, operatore.getId());
                psInsert.setString(3, operatore.getLavoro());
                psInsert.setString(4, data);
                psInsert.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
    }

    public ArrayList<Operatore> restituisci(Map<String, Object> criteri) {
        ArrayList<Operatore> operatori = new ArrayList<>();

        // Costruisci la query SQL dinamica
        StringBuilder query = new StringBuilder("SELECT * FROM \"Operatore\"");

        // Aggiungi le condizioni di ricerca se ci sono criteri
        if (criteri != null && !criteri.isEmpty()) {
            query.append(" WHERE ");
            int count = 0;
            for (String key : criteri.keySet()) {
                if (count > 0) {
                    query.append(" AND ");
                }
                query.append(key).append(" = ?");
                count++;
            }
        }

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            // Imposta i valori per i parametri della query in base ai criteri
            int paramIndex = 1;
            for (Object value : criteri.values()) {
                statement.setObject(paramIndex, value); // Può gestire diversi tipi di dati
                paramIndex++;
            }

            // Esegui la query
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                boolean occupato = resultSet.getBoolean("occupato");
                String data = resultSet.getString("data");
                // Supponendo che Ordine abbia un costruttore del tipo Ordine(int id, int clienteId, String data)
                Operatore operatore = new Operatore(id, occupato);

                // Aggiungi l'ordine alla lista da restituire
                operatori.add(operatore);
            }

        } catch (SQLException e) {
            System.err.println("Errore durante il recupero degli ordini: " + e.getMessage());
        }

        return operatori;
    }

    public Operatore getById(int id) {
        String query = "SELECT * FROM \"Operatore\" WHERE id = ?";
        Operatore op = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            ResultSet resultSet= statement.executeQuery();
            if(resultSet.next()){
                op = new Operatore(resultSet.getInt("id"),
                        resultSet.getBoolean("occupato"));
            }


        } catch (SQLException e) {
            System.err.println("Errore durante la creazione dell'operatore: " + e.getMessage());
        }
        return op;
    }
    public void aggiorna(Operatore operatore) {
        String query = "UPDATE \"Operatore\" SET occupato = ?  WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setBoolean(1, operatore.isWorking());
            statement.setInt(2, operatore.getId());
            statement.executeUpdate();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }
    /*
    @Override
    public void registraAzione(Attuatore operatore){
        //FIXME aggiungere la data e Un tipo di OPERAZIONE che ha (descrizione,data)
        String query = "INSERT INTO \"Operazione\" (tipoAttuatore, idAttuatore, descrizione) VALUES (?, ?, ?)";
        String query2 = "UPDATE \"Operatore\" SET working = 'false' WHERE (id)= (?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, operatore.getClass().toString());
            pstmt.setInt(2, operatore.getId());
            pstmt.setString(3,descrizione);
            pstmt.executeUpdate();

            try (PreparedStatement pstmt2 = connection.prepareStatement(query2)) {
                pstmt2.setInt(1, operatore.getId());
                pstmt2.executeUpdate();
            }

        } catch (SQLException e) {
            StringBuilder d = new StringBuilder("Errore: ");
            System.err.println(d.append(e.getMessage()).toString());
        }
    }

    public String esegui(Operatore operatore, int operazione){
        String query = "UPDATE \"Operatore\" SET working = ? WHERE id = ?";
        try(PreparedStatement stm = connection.prepareStatement(query)){
            stm.setBoolean(1,true);
            stm.setInt(2, operatore.getId());
            stm.executeUpdate();
            return operatore.esegui(operazione);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    Attuatore getAttuatoreByID(int id){
        String query = "SELECT * FROM \"Attuatore\" WHERE id = ?";
        Attuatore attu = null;

        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            attu = new Operatore(resultSet.getInt("id"),resultSet.getBoolean("occupato"));
        }catch (SQLException e){
            System.err.println("Operatore non trovato : "+ e.getMessage() );
        }
        return attu;
    }
*/
}
