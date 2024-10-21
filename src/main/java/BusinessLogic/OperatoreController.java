package main.java.BusinessLogic;

import main.java.DomainModel.Impianto.Operatore;
import main.java.DomainModel.Pianta.Pianta;
import main.java.ORM.*;
import main.java.DomainModel.Ordine;
import main.java.DomainModel.Impianto.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class OperatoreController {
    Operatore operatore;
    public OperatoreController(Operatore operatore) {
        this.operatore = operatore;
    }

    public boolean posizionaOrdine(Ordine ordine, Operatore operatore){
        OperazioneDAO operazioneDAO = new OperazioneDAO();
        OrdineDAO ordineDAO = new OrdineDAO();
        PosizionamentoDAO posizionamentoDAO = new PosizionamentoDAO();
        PosizioneDAO posizioneDAO = new PosizioneDAO();
        PiantaDAO piantaDAO = new PiantaDAO();

        ArrayList<Posizione> libere = posizioneDAO.get(Map.of("assegnata",true));
        if (libere.size() < ordine.getnPiante()) {
            return false;
        } else {
            ArrayList<Posizionamento> pos = new ArrayList<>();

            for (int i = 0; i < ordine.getnPiante(); i++) {
                pos.add(new Posizionamento(libere.get(i).getId(), ordine.getPiante().get(i).getId(), ordine.getId()));
                piantaDAO.aggiorna(ordine.getPiante().get(i).getId(), Map.of("stato", "piantata"));
                posizioneDAO.aggiorna(libere.get(i).getId(), Map.of("occupata", true));
            }
            ordineDAO.aggiorna(ordine.getId(), Map.of("stato", "posizionato"));
            try{
                posizionamentoDAO.inserisciPosizionamenti(pos);
            }catch (SQLException e){
                e.printStackTrace();
            }
            operazioneDAO.registraAzione(operatore, operatore.getLavoro(), LocalDateTime.now().toString());
            return true;
        }
    }

    public void completaOrdine(Ordine ordine, Operatore operatore){
        OperazioneDAO operazioneDAO = new OperazioneDAO();
        OrdineDAO ordineDAO = new OrdineDAO();
        PosizioneDAO posizioneDAO = new PosizioneDAO();
        PiantaDAO piantaDAO = new PiantaDAO();
        PosizionamentoDAO posizionamentoDAO = new PosizionamentoDAO();

        Map<String, Object> pos_by_ordine  = new HashMap<>();
        pos_by_ordine.put("ordine", ordine.getId());
        ArrayList<Posizionamento> pos = posizionamentoDAO.get(pos_by_ordine);
        if(pos != null && !pos.isEmpty()){
            Map<String, Object> libera_posizione  = Map.of("occupata", false,"assegnata", false);
            Map<String, Object> libera_pianta  = Map.of("stato", "da ritirare");

            for(Posizionamento posizionamento : pos){
                posizioneDAO.aggiorna(posizionamento.getIdPosizione(), libera_posizione);
                piantaDAO.aggiorna(posizionamento.getIdPianta(), libera_pianta);
            }
            ordineDAO.aggiorna(ordine.getId(), libera_pianta);
            posizionamentoDAO.eliminaPosizionamentiByOrdine(ordine.getId());
        }
        operazioneDAO.registraAzione(operatore, operatore.getLavoro(), LocalDateTime.now().toString());
    }

    public ArrayList<Pianta> checkupPiante(Operatore operatore) throws InterruptedException {
        OperazioneDAO operazioneDAO = new OperazioneDAO();
        PiantaDAO piantaDAO = new PiantaDAO();
        PosizionamentoDAO posizionamentoDAO = new PosizionamentoDAO();
        ArrayList<Posizionamento> posizionamenti = posizionamentoDAO.get(new HashMap<>());
        ArrayList<Pianta> pianteDaCurare = new ArrayList<>();
        for (Posizionamento p : posizionamenti){
            Pianta pianta = piantaDAO.get(Map.of("id", p.getIdPianta())).get(0);
            System.out.println(pianta.generaStato());
            if(pianta.haBisogno()) {
                pianteDaCurare.add(pianta);
            }
            Thread.sleep(1000);
        }
        operazioneDAO.registraAzione(operatore, operatore.getLavoro(), LocalDateTime.now().toString());
        return pianteDaCurare;
    }

    public void curaPianta(Pianta pianta, Operatore operatore) {
        PiantaDAO piantaDAO = new PiantaDAO();
        OperazioneDAO operazioneDAO = new OperazioneDAO();
        pianta.cura(operatore.getId(), LocalDateTime.now());
        piantaDAO.aggiornaDescrizione(pianta.getId(), pianta.getDescrizione());
        operazioneDAO.registraAzione(operatore, operatore.getLavoro(), LocalDateTime.now().toString());
    }
}
