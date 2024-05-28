package main.java.BuissnessLogic;

import main.java.DomainModel.Impianto.Posizione;
import main.java.ORM.PosizioneDAO;

import java.util.ArrayList;

public class GestionePosizioni {
    public GestionePosizioni(){}

    public ArrayList<Posizione> completaSpazio(int idSpazio){
        PosizioneDAO posizioneDAO = new PosizioneDAO();
        return  posizioneDAO.getPosizioni(idSpazio);
    }

    public void creaPosizione(int idSpazio) {
        PosizioneDAO posizioneDAO = new PosizioneDAO();
        posizioneDAO.creaPosizione(idSpazio);
    }

    public void rimuoviPosizione(int idPosizione) {
        PosizioneDAO posizioneDAO = new PosizioneDAO();
        posizioneDAO.rimuoviPosizione(idPosizione);
    }

    public void visualizzaPosizioni(int idSpazio) {
        PosizioneDAO posizioneDAO = new PosizioneDAO();
        posizioneDAO.visualizzaPosizioni(idSpazio);
    }

    public void monitoraPosizone(int idPosizione) {
        PosizioneDAO posizioneDAO = new PosizioneDAO();
        posizioneDAO.monitoraPosizione(idPosizione);
    }
    public void modificaPosizione(int idPosizione, String query, String valore, String attributo) {
        PosizioneDAO posizioneDAO = new PosizioneDAO();
        posizioneDAO.modificaPosizione(idPosizione, query, valore, attributo);
    }

    public void liberaPosizioni(ArrayList<Posizione> posizioni) {
        PosizioneDAO posizioneDAO = new PosizioneDAO();
        posizioneDAO.liberaPosizioni(posizioni);
    }

    public ArrayList<Posizione> getNPosizioni(int nPosizioni) {
        PosizioneDAO posizioneDAO = new PosizioneDAO();
        return posizioneDAO.getNPosizioni(nPosizioni);
    }
}
