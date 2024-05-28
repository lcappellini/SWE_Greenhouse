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

    public Cliente registraCliente(String nome, String cognome, String email, String password) {
        String query = "INSERT INTO \"Cliente\" (nome, cognome, email, password) VALUES (?, ?, ?, ?) RETURNING id";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nome);
            statement.setString(2, cognome);
            statement.setString(3, email);
            statement.setString(4, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {  // Sposta il cursore alla prima riga
                int id = resultSet.getInt("id");
                System.out.println("Nuovo cliente registrato con successo.");
                return new Cliente(id, nome, cognome, email, password);  // Ritorna l'oggetto Cliente appena registrato
            } else {
                System.out.println("Errore durante la registrazione del cliente.");
                return null;  // Potresti voler gestire questo caso in modo diverso
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            // Gestisci il caso in cui l'email è duplicata nel database
            System.err.println("Errore: Email già registrata.");
            return null;
        } catch (SQLException e) {
            System.err.println("Errore durante la registrazione del cliente: " + e.getMessage());
            return null;
        }
    }


    public Cliente accedi(String email, String password) {
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
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'accesso del cliente: " + e.getMessage());
        }

        return null; // Se non viene trovato alcun cliente con le credenziali fornite, restituisci null
    }
}

