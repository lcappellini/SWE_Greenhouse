package main.java.BuissnessLogic;

import main.java.DomainModel.Impianto.Ambiente;
import main.java.ORM.AmbienteDAO;

import java.util.ArrayList;

public class GestioneAmbienti {
    public GestioneAmbienti() {}

    public void creaAmbiente(int idSpazio, int nPosizioniMax,
                           int idTermometro, int idFotosensore, int idIgrometroAria,
                           int idClimatizzazione, int idLampada){
        AmbienteDAO ambienteDAO = new AmbienteDAO();
        ambienteDAO.creaAmbiente(idSpazio, nPosizioniMax, idTermometro, idFotosensore,
                idIgrometroAria, idClimatizzazione, idLampada);
    }
    public void rimuoviAmbiente(int idAmbiente) {
        AmbienteDAO ambienteDAO = new AmbienteDAO();
        ambienteDAO.rimuoviAmbiente(idAmbiente);
    }
    public void visualizzaAmbienti(int idSpazio){
        AmbienteDAO ambienteDAO = new AmbienteDAO();
        ambienteDAO.visualizzaAmbienti(idSpazio);
    }

    public void monitoraAmbiente(int idAmbiente) {
        AmbienteDAO ambienteDAO = new AmbienteDAO();
        ambienteDAO.monitoraAmbiente(idAmbiente);
    }

    public Ambiente getAmbiente(int idAmbiente) {
        AmbienteDAO ambienteDAO = new AmbienteDAO();
        return ambienteDAO.getAmbiente(idAmbiente);
    }

    public ArrayList<Ambiente> completaSpazio(int idSpazio) {
        AmbienteDAO ambienteDAO = new AmbienteDAO();
        return ambienteDAO.completaSpazio(idSpazio);
    }
}
