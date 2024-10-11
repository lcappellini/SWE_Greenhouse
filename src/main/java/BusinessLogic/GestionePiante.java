package main.java.BusinessLogic;
import main.java.DomainModel.Impianto.Operatore;
import main.java.DomainModel.Pianta.Pianta;
import main.java.ORM.ObjectDAO;
import main.java.ORM.PiantaDAO;

import java.util.ArrayList;
import java.util.Map;

import static java.lang.Thread.sleep;


public class GestionePiante {
    PiantaDAO piantaDAO;
    public GestionePiante() {
         piantaDAO = new PiantaDAO();
    }

    public void visualizza(Map<String, Object> criteri){
        ObjectDAO dao = new ObjectDAO();
        dao.visualizza("Pianta", criteri);
    }

    public Pianta restituisciPianta(int id){
        return piantaDAO.restituisciPianta(id);
    }

    public void aggiungi(ArrayList<Pianta> piante){
        ObjectDAO dao = new ObjectDAO();
        for(Pianta p : piante){
            dao.inserisci(p);
        }
    }
    public ArrayList<Pianta> controllaStatoPiante() throws InterruptedException {
        int i = 1;
        Pianta p = piantaDAO.restituisciPianta(i);
        ArrayList<Pianta> pianteDaCurare = new ArrayList<>();
        while(p != null){
            System.out.println(p.generaStato());
            if(p.haBisogno()){ pianteDaCurare.add(p); }
            sleep(1000);
            i++;
            p = piantaDAO.restituisciPianta(i);
        }
        return pianteDaCurare;
    }
    public void aggiornaDescrizione(int idPianta, String descrizione) {
        piantaDAO.aggiornaDescrizione(idPianta, descrizione);
    }

    public void visualizzaTutte() {

    }
}
