package main.java.ORM;

import main.java.DomainModel.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;


public class ClienteDAO {

    private Connection connection;

    public  ClienteDAO(){
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public boolean registra(String nome, String cognome, String email, String password) throws SQLException{
        // Verifica se l'email è già presente nel database
        String insertQuery = "INSERT INTO \"Cliente\" (nome, cognome, email, password) VALUES (?, ?, ?, ?) RETURNING id";
        boolean registrato = false;
        // Inserimento del nuovo cliente
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, nome);
            statement.setString(2, cognome);
            statement.setString(3, email);
            statement.setString(4, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                registrato = true;
            }
        } catch (SQLException e) {
        System.err.println("Errore durante la registrazione del cliente: " + e.getMessage());
        }
        return registrato;
    }

    public Cliente accedi(String email, String password){
        String query = "SELECT * FROM \"Cliente\" WHERE email = ? AND password = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    // Estrai i dati del cliente dal result set e costruisci un oggetto Cliente
                    Cliente cliente = new Cliente(rs.getInt("id"), rs.getString("nome"),
                            rs.getString("cognome"), rs.getString("email"));
                    return cliente;
                }
            }catch (SQLException e) {
                System.err.println("Errore durante l'accesso del cliente: "+e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'accesso del cliente: "+e.getMessage());
        }

        return null; // Se non viene trovato alcun cliente con le credenziali fornite, restituisci null
    }

    public boolean rimuoviClienteById(int idCliente){
        String query = "DELETE FROM \"Cliente\" WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, idCliente);

            statement.executeUpdate();
            return true;
        }catch(SQLException e){
            System.err.println("Errore durante la rimozione del cliente:" + e.getMessage());
        }
        return false;
    }

    public boolean modificaAttributo(int clienteId, String name, String newValue){
        String query = "UPDATE \"Cliente\" SET " + name + " = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newValue);
            statement.setInt(2, clienteId);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento dell'attributo del cliente: " + e.getMessage());
        }
        return false;
    }

    public ArrayList<Cliente> get(Map<String, Object> criteri) {
        StringBuilder query = new StringBuilder("SELECT * FROM \"Cliente\"");

        if (criteri != null && !criteri.isEmpty()) {
            query.append(" WHERE ");
            for (String key : criteri.keySet()) {
                query.append(key).append(" = ? AND ");
            }
            query.setLength(query.length() - 5);  // Rimuove l'ultimo " AND "
        }

        ArrayList<Cliente> clienti = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            if (criteri != null && !criteri.isEmpty()) {
                int paramIndex = 1;
                for (Object value : criteri.values()) {
                    statement.setObject(paramIndex, value);
                    paramIndex++;
                }
            }

            // Esegui la query e gestisci il ResultSet
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    clienti.add(new Cliente(rs.getInt("id"), rs.getString("nome"),
                            rs.getString("cognome"), rs.getString("email")));
                }
            }
        } catch (SQLException e) {
            // Logga l'errore
            System.err.println("Errore durante il recupero delle piante: " + e.getMessage());
            e.printStackTrace(); // Mostra la traccia dello stack per una diagnostica più dettagliata
        }

        return clienti;
    }
}

