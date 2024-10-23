package main.java.BusinessLogic;

import main.java.DomainModel.Operatore;
import main.java.DomainModel.Pianta;
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

    public boolean piantaOrdine(Ordine ordine, Operatore operatore){
        OperazioneDAO operazioneDAO = new OperazioneDAO();
        OrdineDAO ordineDAO = new OrdineDAO();
        PosizionamentoDAO posizionamentoDAO = new PosizionamentoDAO();
        PosizioneDAO posizioneDAO = new PosizioneDAO();
        PiantaDAO piantaDAO = new PiantaDAO();

        ArrayList<Posizione> libere = posizioneDAO.get(Map.of("assegnata",true));
        if (libere.size() < ordine.getnPiante()) {
            System.out.println(1);
            return false;
        } else {
            ArrayList<Posizionamento> pos = new ArrayList<>();

            for (int i = 0; i < ordine.getnPiante(); i++) {
                pos.add(new Posizionamento(libere.get(i).getId(), ordine.getPiante().get(i).getId(), ordine.getId()));
                piantaDAO.aggiorna(ordine.getPiante().get(i).getId(), Map.of("stato", "sta crescendo"));
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

    public boolean completaOrdine(Ordine ordine, Operatore operatore){
        OperazioneDAO operazioneDAO = new OperazioneDAO();
        OrdineDAO ordineDAO = new OrdineDAO();
        PosizioneDAO posizioneDAO = new PosizioneDAO();
        PiantaDAO piantaDAO = new PiantaDAO();
        PosizionamentoDAO posizionamentoDAO = new PosizionamentoDAO();

        ArrayList<Posizionamento> pos = posizionamentoDAO.get(Map.of("ordine", ordine.getId()));
        if (pos != null && !pos.isEmpty() && posizionamentoDAO.eliminaPosizionamentiByOrdine(ordine.getId())){
            for(Posizionamento posizionamento : pos){
                posizioneDAO.aggiorna(posizionamento.getIdPosizione(), Map.of("occupata", false,"assegnata", false));
                piantaDAO.aggiorna(posizionamento.getIdPianta(), Map.of("stato", "da ritirare"));
            }
            ordineDAO.aggiorna(ordine.getId(), Map.of("stato", "da ritirare"));
            operazioneDAO.registraAzione(operatore, operatore.getLavoro(), LocalDateTime.now().toString());
            return true;
        }
        return false;
    }

    public ArrayList<Pianta> checkupPiante(Operatore operatore, int duration) {
        OperazioneDAO operazioneDAO = new OperazioneDAO();
        PiantaDAO piantaDAO = new PiantaDAO();
        PosizionamentoDAO posizionamentoDAO = new PosizionamentoDAO();
        ArrayList<Posizionamento> posizionamenti = posizionamentoDAO.get(new HashMap<>());
        ArrayList<Pianta> pianteDaCurare = new ArrayList<>();

        for (Posizionamento p : posizionamenti){
            Pianta pianta = piantaDAO.get(Map.of("id", p.getIdPianta())).get(0);
            String stato = pianta.getStato();
            System.out.printf("Stato %s [%d] -> %s\n", pianta.getTipoPianta(), pianta.getId(), stato);
            if(pianta.haBisogno()) {
                pianteDaCurare.add(pianta);
            }
            try {
                Thread.sleep(duration);
            } catch (InterruptedException ignored) {
                return new ArrayList<>();
            }
        }
        operazioneDAO.registraAzione(operatore, operatore.getLavoro(), LocalDateTime.now().toString());
        return pianteDaCurare;
    }

    public void curaPianta(Pianta pianta, Operatore operatore) {
        PiantaDAO piantaDAO = new PiantaDAO();
        OperazioneDAO operazioneDAO = new OperazioneDAO();
        pianta.cura(operatore.getId(), LocalDateTime.now());
        piantaDAO.aggiorna(pianta.getId(), Map.of("stato", pianta.getStato()));
        piantaDAO.aggiornaDescrizione(pianta.getId(), pianta.getDescrizione());
        operazioneDAO.registraAzione(operatore, operatore.getLavoro(), LocalDateTime.now().toString());
    }
}
