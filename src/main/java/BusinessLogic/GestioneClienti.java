package main.java.BusinessLogic;

import main.java.DomainModel.Cliente;
import main.java.ORM.ClienteDAO;

import java.util.ArrayList;
import java.util.Map;

public class GestioneClienti {
    public GestioneClienti() {}

    public ArrayList<Cliente> get(Map<String, Object> criteri) {
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.get(criteri);
    }
}
