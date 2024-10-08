package main.java.BusinessLogic;

import main.java.DomainModel.Admin;
import main.java.ORM.AdminDAO;

public class GestioneAdmin {
    public GestioneAdmin() {}

    public Admin accedi(String email, String password) {
        AdminDAO adminDAO = new AdminDAO();
        return adminDAO.accedi(email, password);
    }
}
