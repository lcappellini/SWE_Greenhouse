package main.java.BuissnessLogic;

import main.java.DomainModel.Impianto.Spazio;
import main.java.ORM.SpazioDAO;

public class GestioneSpazi {

    public GestioneSpazi() {}

    public void creaSpazio(String nome, String descrizione, int nAmbientiMax){
        SpazioDAO spazioDAO = new SpazioDAO();
        spazioDAO.creaSpazio(nome, descrizione, nAmbientiMax);
    }

    public void rimuoviSpazio(int idSpazio){
        SpazioDAO spazioDAO = new SpazioDAO();
        spazioDAO.rimuoviSpazio(idSpazio);
    }

    public void visualizzaSpazi() {
        SpazioDAO spazioDAO = new SpazioDAO();
        spazioDAO.visualizzaSpazi();
    }
    public int getNAmbientiMaxByIdSpazio(int idSpazio) {
        SpazioDAO spazioDAO = new SpazioDAO();
        return spazioDAO.getNAmbientiMaxByIdSpazio(idSpazio);
    }

    public Spazio getSpazio(int idSpazio) {
        SpazioDAO spazioDAO = new SpazioDAO();
        return spazioDAO.getSpazio(idSpazio);
    }

    public void visualizzaSpazio(int idSpazio) {
        //DONE
        SpazioDAO spazioDAO = new SpazioDAO();
        spazioDAO.visualizzaSpazio(idSpazio);
    }
}
