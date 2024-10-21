package main.java.BusinessLogic;

import main.java.DomainModel.Cliente;
import main.java.ORM.ClienteDAO;

import java.sql.SQLException;

public class LoginClienteController {

    public Cliente accedi(String email, String password){
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.accedi(email, password);
    }

    public boolean registrati(String nome, String cognome, String email, String password){
        ClienteDAO clienteDAO = new ClienteDAO();
        boolean registrato = false;
        if(clienteDAO.accedi(email, password) == null){
            try{
                if (clienteDAO.registra(nome, cognome, email, password)){
                    registrato = true;
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return registrato;
    }


}
