package main.java.BuissnessLogic;

import main.java.ORM.SpazioDAO;

public class GestioneSpazi {
    public GestioneSpazi() {}

    public void creaSpazio(int idAmbiente, int nPosizioniMax, int nSpaziMax){
        SpazioDAO spazioDAO = new SpazioDAO();
        spazioDAO.creaSpazio(idAmbiente, nPosizioniMax, nSpaziMax);
    }
    public void rimuoviSpazio(){

    }
}
