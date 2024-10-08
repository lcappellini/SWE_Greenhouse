package main.java.ORM;

import main.java.DomainModel.Cliente;

import java.sql.*;


public class ClienteDAO {

    private Connection connection;

    public  ClienteDAO(){
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public Cliente registraCliente(String nome, String cognome, String email, String password) throws SQLException{
        // Verifica se l'email è già presente nel database
        String checkQuery = "SELECT id FROM \"Cliente\" WHERE email = ?";
        String insertQuery = "INSERT INTO \"Cliente\" (nome, cognome, email, password) VALUES (?, ?, ?, ?) RETURNING id";

        try {
            // Verifica se l'email esiste già
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, email);
                ResultSet checkResult = checkStatement.executeQuery();
                if (checkResult.next()) {
                    System.out.println("Errore: L'email è già registrata.");
                    return null; // L'email esiste già, non si procede con la registrazione
                }
            }

            // Inserimento del nuovo cliente
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, nome);
                statement.setString(2, cognome);
                statement.setString(3, email);
                statement.setString(4, password);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    System.out.println("Nuovo cliente registrato con successo.");
                    return new Cliente(id, nome, cognome, email, password);
                } else {
                    System.out.println("Errore durante la registrazione del cliente.");
                    return null;
                }
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            // Gestione specifica per la violazione di vincoli, come email duplicata
            System.err.println("Errore: Violazione del vincolo di unicità. Email già registrata.");
            return null;
        } catch (SQLException e) {
            System.err.println("Errore durante la registrazione del cliente: " + e.getMessage());
            return null;
        }
    }



    public Cliente accedi(String email, String password){
        String query = "SELECT * FROM \"Cliente\" WHERE email = ? AND password = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    // Estrai i dati del cliente dal result set e costruisci un oggetto Cliente
                    Cliente cliente = new Cliente(rs.getInt("id"), rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("password"));
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

    public void rimuoviCliente(String email){
        String query = "DELETE FROM \"Cliente\" WHERE email = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, email);
            try{
                statement.executeUpdate();
                System.out.println("Cliente rimosso con successo.");
            }catch (SQLException e){
                System.err.println("Errore durante la rimozione del cliente:"+ e.getMessage());
            }
        }catch(SQLException e){
            System.err.println("Errore durante la rimozione del cliente:" + e.getMessage());
        }
    }
}

