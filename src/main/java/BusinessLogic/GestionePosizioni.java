package main.java.BusinessLogic;

import main.java.DomainModel.Impianto.Posizione;
import main.java.ORM.ObjectDAO;
import main.java.ORM.PosizioneDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GestionePosizioni {
    PosizioneDAO  posizioneDAO;
    public GestionePosizioni(){
        posizioneDAO = new PosizioneDAO();
    }

    public ArrayList<Posizione> completaAmbiente(int idAmbiente){
        return  posizioneDAO.getPosizioni(idAmbiente);
    }

    public void creaPosizione(int idAmbiente) {
        posizioneDAO.creaPosizione(idAmbiente);
    }

    public void rimuoviPosizione(int idPosizione) {
        posizioneDAO.rimuoviPosizione(idPosizione);
    }

    public void visualizzaPosizioni(int idAmbiente) {
        posizioneDAO.visualizzaPosizioni(idAmbiente);
    }

    public void monitoraPosizone(int idPosizione) {
        posizioneDAO.monitoraPosizione(idPosizione);
    }
    public void modificaPosizione(int idPosizione, String query, String valore, String attributo) {
        posizioneDAO.modificaPosizione(idPosizione, query, valore, attributo);
    }

    public void liberaPosizioni(List<Integer> posizioni) {
        posizioneDAO.liberaPosizioni(posizioni);
    }



    public boolean verificaNonAssegnate(int n){
        return posizioneDAO.verificaNonAssegnate(n);
    }

    public void liberaUltime(int i) {
        posizioneDAO.liberaUltime(i);
    }

    public ArrayList<Posizione> occupa(int i) {
        //Posizioni da occupare
        ArrayList<Posizione> posizioniLibere = posizioneDAO.occupa(i);
        if(posizioniLibere.isEmpty()){
            System.out.println("Non ci sono posizioni libere!");
            return null;
        } else if (posizioniLibere.size() != i) {
            System.out.println("Posizioni libere insufficienti");
            return null;
        }
        return posizioniLibere;
    }
}
