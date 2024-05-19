package main.java.BuissnessLogic;

import main.java.DomainModel.Impianto.Operatore;
import main.java.DomainModel.Ordine;
import main.java.ORM.ImpiantoDAO;

public class GestioneImpianto {

    public boolean veriificaDisponibilita(int nPiante){
        ImpiantoDAO impiantoDAO = new ImpiantoDAO();

        return impiantoDAO.verificaDisponibilita(nPiante);
    }
    public void assegnaPosizionamento(Operatore operatore){

    }
}
