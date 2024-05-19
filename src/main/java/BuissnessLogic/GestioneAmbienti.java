package main.java.BuissnessLogic;

import main.java.ORM.AmbienteDAO;

public class GestioneAmbienti {

    public GestioneAmbienti() {}

    public void creaAmbiente(String nome, String descrizione){
        AmbienteDAO ambienteDAO = new AmbienteDAO();
        ambienteDAO.creaAmbiente(nome, descrizione);
    }

    public void rimuoviAmbiente(){
        AmbienteDAO ambienteDAO = new AmbienteDAO();
        ambienteDAO.rimuoviAmbiente();
    }
}
