package main.java.BuissnessLogic;

import main.java.ORM.AmbienteDAO;

public class GestioneAmbienti {

    public GestioneAmbienti() {}

    public void creaAmbiente(String nome, String descrizione){
        AmbienteDAO ambienteDAO = new AmbienteDAO();
        ambienteDAO.creaAmbiente(nome, descrizione);
    }

    public void rimuoviAmbiente(String nome){
        AmbienteDAO ambienteDAO = new AmbienteDAO();
        ambienteDAO.rimuoviAmbiente(nome);
    }

    public void visualizzaAmbienti() {
        AmbienteDAO ambienteDAO = new AmbienteDAO();
        ambienteDAO.visualizzaAmbienti();
    }
    public int getNSpaziMaxByIdAmbiente(int idAmbiente) {
        AmbienteDAO ambienteDAO = new AmbienteDAO();
        return ambienteDAO.getNSpaziMaxByIdAmbiente(idAmbiente);
    }
}
