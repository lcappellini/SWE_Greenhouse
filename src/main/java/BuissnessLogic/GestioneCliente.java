package main.java.BuissnessLogic;

import main.java.DomainModel.Cliente;
import main.java.ORM.ClienteDAO;

import java.sql.SQLException;


public class GestioneCliente {

    public GestioneCliente() {}

    public Cliente registraCliente(String nome, String cognome, String email, String password) {
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.registraCliente(nome, cognome, email, password);
    }

    public Cliente accedi(String email, String password) throws SQLException, ClassNotFoundException {
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.accedi(email, password);
    }


}
