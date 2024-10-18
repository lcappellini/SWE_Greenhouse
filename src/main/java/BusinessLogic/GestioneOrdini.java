package main.java.BusinessLogic;

import main.java.DomainModel.Cliente;
import main.java.DomainModel.Impianto.Operatore;
import main.java.DomainModel.Ordine;
import main.java.ORM.*;


import java.sql.SQLException;
import java.util.*;

public class GestioneOrdini {
    private OrdineDAO ordineDAO;
    private PosizioneDAO posizioneDAO;
    private PiantaDAO piantaDAO;

    public GestioneOrdini() {
        ordineDAO = new OrdineDAO();
        posizioneDAO = new PosizioneDAO();
        piantaDAO = new PiantaDAO();
    }

    public void setPosizioneDAO(PosizioneDAO posizioneDAO) {
        this.posizioneDAO = posizioneDAO;
    }
    public void setOrdineDAO(OrdineDAO ordineDAO) {
        this.ordineDAO = ordineDAO;
    }

    public boolean creazioneOrdine(Ordine ordine){
        if (posizioneDAO.verificaNonAssegnate(ordine.getnPiante())) {
            ordineDAO.inserisciOrdine(ordine);
            posizioneDAO.assegna(ordine.getnPiante());
            return true;
        } else {
            System.out.println("Posizioni insufficienti per questo ordine.");
        }
        return false;
    }

    public void visualizzaOrdiniDaPosizionare(){
        Map<String, Object> criteria = new HashMap<>();;
        criteria.put("stato", "da posizionare");
       // ordineDAO.visualizza(criteria);
    }
    public boolean visualizzaOrdini(Map<String, Object> criteri) {
        ArrayList<Ordine> ordini = ordineDAO.get(criteri);
        if (ordini.isEmpty()) {
            System.out.println("Non sono stati trovati ordini");
            return false;
        }

        System.out.println("+------------------------------------------------------------------------------------+");
        System.out.println("|   ID   | Cliente | Consegna   | Totale | Stato          | Piante                   |");
        System.out.println("|--------|---------|------------|--------|----------------|--------------------------|");

        for (Ordine o : ordini) {
            // Ottieni la stringa delle piante
            String piante = o.getPianteString();

            // Suddividi la stringa in righe di massimo 14 caratteri
            List<String> pianteLinee = spezzaStringa(piante, 24);

            // Stampa la prima riga con i dati principali
            System.out.printf("| %-6d | %-7d | %-10s | %-6s | %-14s | %-24s |\n",
                    o.getId(), o.getCliente(), o.getStringDataConsegna(), o.getTotale(), o.getStato(), pianteLinee.get(0)
            );

            // Stampa le righe successive con le piante spezzate, mantenendo gli altri campi vuoti
            for (int i = 1; i < pianteLinee.size(); i++) {
                System.out.printf("| %-6s | %-7s | %-10s | %-6s | %-14s | %-24s |\n", "", "", "", "", "", pianteLinee.get(i));
            }
        }

        System.out.println("+------------------------------------------------------------------------------------+");
        return true;
    }

    // Metodo per spezzare una stringa in linee di lunghezza massima
    private List<String> spezzaStringa(String s, int maxLunghezza) {
        List<String> linee = new ArrayList<>();
        int lunghezza = s.length();

        for (int i = 0; i < lunghezza; i += maxLunghezza) {
            linee.add(s.substring(i, Math.min(lunghezza, i + maxLunghezza)));
        }

        return linee;
    }

    public void ritira(Ordine o) {

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

        /*if(ordine.getCliente() == cliente.getId() && !operatore.attivo() && ordine.getStato().equals("pronto")){
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

    public void prepara(Ordine ordine) {
        Map<String, Object> m = new HashMap<>();
        m.put("stato", "da ritirare");
        ordineDAO.aggiorna(ordine.getId(),m);
    }

    public void aggiorna(int idOrdine, Map<String, Object> criteri) {
        ordineDAO.aggiorna(idOrdine,criteri);
    }

    public Ordine getbyId(int idOrdine) {
        Ordine o = null;
        Map<String, Object> c = new HashMap<>();
        c.put("id", idOrdine);
        o = ordineDAO.get(c).get(0);
        if(o == null){
            System.out.println("Ordine con id = "+idOrdine+" non trovato");
        }
        return o;
    }

    public void posiziona(Ordine ordine) {
        Map<String, Object> m = new HashMap<>();
        m.put("stato", "posizionato");
        ordineDAO.aggiorna(ordine.getId(),m);
    }
}
