package main.java.BusinessLogic;

import main.java.ORM.ImpiantoDAO;

public class GestioneImpianto {

    public boolean verificaDisponibilita(int nPiante){
        ImpiantoDAO impiantoDAO = new ImpiantoDAO();

        return impiantoDAO.verificaDisponibilita(nPiante);
    }
}
