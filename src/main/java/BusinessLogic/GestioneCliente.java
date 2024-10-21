package main.java.BusinessLogic;

import main.java.DomainModel.Cliente;
import main.java.ORM.ClienteDAO;

import java.sql.SQLException;


public class GestioneCliente {
    ClienteDAO clienteDAO;

    public GestioneCliente() {
        clienteDAO = new ClienteDAO();
    }

    public boolean registraCliente(String nome, String cognome, String email, String password) throws SQLException, ClassNotFoundException {
        return clienteDAO.registra(nome, cognome, email, password);
    }

    public Cliente accedi(String email, String password) throws SQLException, ClassNotFoundException {
        return clienteDAO.accedi(email, password);
    }

    public void rimuoviCliente(String email) throws SQLException, ClassNotFoundException {
        clienteDAO.rimuoviCliente(email);
    }


}
