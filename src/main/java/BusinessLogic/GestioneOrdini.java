package main.java.BusinessLogic;

import main.java.DomainModel.Ordine;
import main.java.ORM.*;
import java.util.*;

public class GestioneOrdini {

    public GestioneOrdini() {}

    public ArrayList<Ordine> get(Map<String, Object> criteri) {
        OrdineDAO ordineDAO = new OrdineDAO();
        return ordineDAO.get(criteri);
    }

    public Ordine getById(int idOrdine) {
        ArrayList<Ordine> ordini = get(Map.of("id",idOrdine));
        if (ordini.isEmpty())
            return null;
        else
            return ordini.get(0);
    }

    public void posiziona(Ordine ordine) {
        /*
        Map<String, Object> m = new HashMap<>();
        /ém.put("stato", "posizionato");
        ordineDAO.aggiorna(ordine.getId(),m);

         */
    }
}
