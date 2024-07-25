package main.java.BuissnessLogic;

import main.java.DomainModel.Impianto.Posizione;
import main.java.ORM.ObjectDAO;
import main.java.ORM.PosizioneDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GestionePosizioni {
    public GestionePosizioni(){}

    public ArrayList<Posizione> completaAmbiente(int idAmbiente){
        PosizioneDAO posizioneDAO = new PosizioneDAO();
        return  posizioneDAO.getPosizioni(idAmbiente);
    }

    public void creaPosizione(int idAmbiente) {
        PosizioneDAO posizioneDAO = new PosizioneDAO();
        posizioneDAO.creaPosizione(idAmbiente);
    }

    public void rimuoviPosizione(int idPosizione) {
        PosizioneDAO posizioneDAO = new PosizioneDAO();
        posizioneDAO.rimuoviPosizione(idPosizione);
    }

    public void visualizzaPosizioni(int idAmbiente) {
        PosizioneDAO posizioneDAO = new PosizioneDAO();
        posizioneDAO.visualizzaPosizioni(idAmbiente);
    }

    public void monitoraPosizone(int idPosizione) {
        PosizioneDAO posizioneDAO = new PosizioneDAO();
        posizioneDAO.monitoraPosizione(idPosizione);
    }
    public void modificaPosizione(int idPosizione, String query, String valore, String attributo) {
        PosizioneDAO posizioneDAO = new PosizioneDAO();
        posizioneDAO.modificaPosizione(idPosizione, query, valore, attributo);
    }

    public void liberaPosizioni(List<Integer> posizioni) {
        PosizioneDAO posizioneDAO = new PosizioneDAO();
        posizioneDAO.liberaPosizioni(posizioni);
    }

    public ArrayList<Posizione> getNPosizioni(int nPosizioni) {
        PosizioneDAO posizioneDAO = new PosizioneDAO();
        return posizioneDAO.getNPosizioni(nPosizioni);
    }
    public List<Integer> restituisci(Map<String, Object> criteri){
        ObjectDAO objectDAO = new ObjectDAO();
        return objectDAO.restituisci("Posizione", criteri);
    }

    public List<Integer> occupa(int nPiante) {
        PosizioneDAO posizioneDAO = new PosizioneDAO();
        return  posizioneDAO.occupa(nPiante);
    }
}
