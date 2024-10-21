package main.java.BusinessLogic;

import main.java.DomainModel.Admin;
import main.java.DomainModel.Impianto.Operatore;
import main.java.ORM.AdminDAO;
import main.java.ORM.OperatoreDAO;

public class LoginPersonaleController {
    public Admin loginAdmin(String email, String password) {
        AdminDAO adminDAO = new AdminDAO();
        return adminDAO.accedi(email, password);
    }
    public Operatore loginOperatore(String email, String password){
        OperatoreDAO operatorDAO = new OperatoreDAO();
        return operatorDAO.accedi(email, password);
    }

}
