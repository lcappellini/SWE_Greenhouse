package main.java.BuissnessLogic;

import main.java.DomainModel.Impianto.Spazio;
import main.java.ORM.SpazioDAO;

import java.util.ArrayList;

public class GestioneSpazi {
    public GestioneSpazi() {}

    public void creaSpazio(int idAmbiente, int nPosizioniMax,
                           int idTermometro, int idFotosensore, int idIgrometroAria,
                           int idClimatizzazione, int idLampada){
        SpazioDAO spazioDAO = new SpazioDAO();
        spazioDAO.creaSpazio(idAmbiente, nPosizioniMax, idTermometro, idFotosensore,
                idIgrometroAria, idClimatizzazione, idLampada);
    }
    public void rimuoviSpazio(int idSpazio) {
        SpazioDAO spazioDAO = new SpazioDAO();
        spazioDAO.rimuoviSpazio(idSpazio);
    }
    public void visualizzaSpazi(int idAmbiente){
        SpazioDAO spazioDAO = new SpazioDAO();
        spazioDAO.visualizzaSpazi(idAmbiente);
    }


    public void monitoraSpazio(int idSpazio) {
        SpazioDAO spazioDAO = new SpazioDAO();
        spazioDAO.monitoraSpazio(idSpazio);
    }

    public Spazio getSpazio(int idSpazio) {
        SpazioDAO spazioDAO = new SpazioDAO();
        return spazioDAO.getSpazio(idSpazio);
    }

    public ArrayList<Spazio> completaAmbiente(int idAmbiente) {
        SpazioDAO spazioDAO = new SpazioDAO();
        return spazioDAO.completaAmbiente(idAmbiente);
    }
}
