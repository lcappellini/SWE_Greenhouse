package main.java.BusinessLogic;

import main.java.DomainModel.Cliente;
import main.java.DomainModel.Impianto.Operatore;
import main.java.DomainModel.Ordine;
import main.java.DomainModel.Pianta.Pianta;
import main.java.ORM.*;


import java.sql.ParameterMetaData;
import java.sql.SQLException;
import java.util.*;

public class GestioneOrdini {

    public GestioneOrdini() {}

    public ArrayList<Ordine> get(Map<String, Object> criteri) {
        OrdineDAO ordineDAO = new OrdineDAO();
        return ordineDAO.get(criteri);
    }

    public void ritira(Ordine o) {
        /*
        if(o != null && o.getStato().equals("da ritirare")) {
            try{
                piantaDAO.elimina(o.getId());
                Map<String, Object> mmm = new HashMap<>();
                mmm.put("stato","ritirato");
                aggiorna(o.getId(),mmm);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Ordine ritirato!");
        }else{
            System.out.println("Ritiro non effettuato.");
        }

        if(ordine.getCliente() == cliente.getId() && !operatore.attivo() && ordine.getStato().equals("pronto")){
            //L'operatore esegue 1 ( = eliminazione posizionamento e liberazione delle posizioni, simulato)
            String descrizione = operatore.esegui(1);
            System.out.println(descrizione);
            piantaDAO.ritira(posizionamentoDAO.ritira(ordine.getId())); // vengono eliminate le piante posizionate
            //posizioneDAO.liberaPosizioni(posizionamentoDAO.eliminaPosizionamentiByOrdine(ordine.getId())); // vengono eliminati i posizionamenti e liberate le posizioni

            //Viene registrata l'operazione su DB
            operatoreDAO.registraAzione(operatore, descrizione);
            operatore.esegui(-1); // L'operatore torna libero

            ordineDAO.pagaERitiraOrdine(ordine);
        }else{
            System.out.print("L'ordine non può essere ritirato : ");
            if(!ordine.getStato().equals("pronto")){
                System.out.println("ordine non pronto");
            } else if (operatore.attivo()) {
                System.out.println("operatore occupato");
            } else if (cliente.getId() != ordine.getCliente()) {
                System.out.println("l'ordine non appartiene al cliente");
            }
        }*/
    }

    public void prepara(int idOrdine) {
        /*
        Map<String, Object> m = new HashMap<>();
        m.put("stato", "da ritirare");
        ordineDAO.aggiorna(idOrdine,m);
        */
    }

    public void aggiorna(int idOrdine, Map<String, Object> criteri) {
        OrdineDAO ordineDAO = new OrdineDAO();
        ordineDAO.aggiorna(idOrdine,criteri);
    }

    public Ordine getbyId(int idOrdine) {
        PiantaDAO piantaDAO = new PiantaDAO();
        OrdineDAO ordineDAO = new OrdineDAO();

        Ordine ordine = ordineDAO.getById(idOrdine);

        if (ordine == null){
            System.out.println("Ordine con id = "+idOrdine+" non trovato");
        }
        else {
            ordine.setPiante(piantaDAO.get(Map.of("ordine", idOrdine)));
        }
        return ordine;
    }

    public void posiziona(Ordine ordine) {
        /*
        Map<String, Object> m = new HashMap<>();
        /ém.put("stato", "posizionato");
        ordineDAO.aggiorna(ordine.getId(),m);

         */
    }
}
