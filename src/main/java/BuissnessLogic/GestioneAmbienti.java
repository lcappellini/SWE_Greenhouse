package main.java.BuissnessLogic;

import main.java.DomainModel.Impianto.Ambiente;
import main.java.ORM.AmbienteDAO;

public class GestioneAmbienti {

    public GestioneAmbienti() {}

    public void creaAmbiente(String nome, String descrizione, int nSpaziMax){
        AmbienteDAO ambienteDAO = new AmbienteDAO();
        ambienteDAO.creaAmbiente(nome, descrizione, nSpaziMax);
    }

    public void rimuoviAmbiente(int idAmbiente){
        AmbienteDAO ambienteDAO = new AmbienteDAO();
        ambienteDAO.rimuoviAmbiente(idAmbiente);
    }

    public void visualizzaAmbienti() {
        AmbienteDAO ambienteDAO = new AmbienteDAO();
        ambienteDAO.visualizzaAmbienti();
    }
    public int getNSpaziMaxByIdAmbiente(int idAmbiente) {
        AmbienteDAO ambienteDAO = new AmbienteDAO();
        return ambienteDAO.getNSpaziMaxByIdAmbiente(idAmbiente);
    }

    public Ambiente getAmbiente(int idAmbiente) {
        AmbienteDAO ambienteDAO = new AmbienteDAO();
        return ambienteDAO.getAmbiente(idAmbiente);
    }

    public void visualizzaAmbiente(int idAmbiente) {
        //TODO
    }
}
