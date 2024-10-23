package main.java.BusinessLogic;

import main.java.DomainModel.Pianta;
import main.java.ORM.PiantaDAO;

import java.util.ArrayList;
import java.util.Map;

public class GestionePiante {
    public GestionePiante() {}

    public ArrayList<Pianta> get(Map<String, Object> criteri){
        PiantaDAO piantaDAO = new PiantaDAO();
        return piantaDAO.get(criteri);
    }

    // Funzione ai fini della simulazione
    public void generaStatoPiante(){
        PiantaDAO piantaDAO = new PiantaDAO();
        ArrayList<Pianta> piante = piantaDAO.get(null);
        for (Pianta pianta : piante) {
            pianta.generaStato();
            piantaDAO.aggiorna(pianta.getId(), Map.of("stato", pianta.getStato()));
        }
    }

    public void setStatoPiantaNonHaBisgono(Pianta pianta){
        PiantaDAO piantaDAO = new PiantaDAO();
        pianta.setStatoNonHaBisogno();
        piantaDAO.aggiorna(pianta.getId(), Map.of("stato", pianta.getStato()));
    }

    public void setStatoPiantaHaBisgono(Pianta pianta){
        PiantaDAO piantaDAO = new PiantaDAO();
        pianta.setStatoHaBisogno();
        piantaDAO.aggiorna(pianta.getId(), Map.of("stato", pianta.getStato()));
    }
}
