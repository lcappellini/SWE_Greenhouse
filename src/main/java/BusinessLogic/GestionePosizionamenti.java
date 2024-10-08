package main.java.BusinessLogic;

import main.java.DomainModel.Cliente;
import main.java.DomainModel.Impianto.Operatore;
import main.java.DomainModel.Impianto.Posizionamento;
import main.java.DomainModel.Impianto.Posizione;
import main.java.DomainModel.Impianto.Spazio;
import main.java.DomainModel.Ordine;
import main.java.DomainModel.Pianta.Pianta;
import main.java.ORM.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GestionePosizionamenti {
    private Spazio spazio;
    private PosizionamentoDAO posizionamentoDAO;
    private PosizioneDAO posizioneDAO;
    private OrdineDAO ordineDAO;
    private OperatoreDAO operatoreDAO;
    private PiantaDAO piantaDAO;
    private ObjectDAO objectDAO;

    public GestionePosizionamenti() {
        posizionamentoDAO = new PosizionamentoDAO();
        posizioneDAO = new PosizioneDAO();
        ordineDAO = new OrdineDAO();
        operatoreDAO = new OperatoreDAO();
        piantaDAO = new PiantaDAO();
        objectDAO = new ObjectDAO();
    }
    public void creaPosizionamento(Ordine ordine, Operatore operatore, ArrayList<Posizione> posizioniLibere) throws SQLException {
        operatore.esegui(0);

        //Inserimento piante //TODO aggiornare la descrizione con quella generata dall'operatore
        String d = operatore.esegui(0);
        operatoreDAO.registraAzione(operatore, LocalDateTime.now().toString());
        ArrayList<Pianta> piantePosizionate = piantaDAO.posaPiante(ordine.getPiante(), d);
        System.out.println(d);

        ArrayList<Posizionamento> posizionamenti = new ArrayList<>();
        for(int i = 0; i < posizioniLibere.size(); i++){
            posizionamenti.add(new Posizionamento(posizioniLibere.get(i), piantePosizionate.get(i),
                    operatore, ordine));
        }
        if(posizionamenti.isEmpty()){
            System.out.println("Posizionamenti non creati.");
            return;
        }

        //inserimentoPosizionamenti
        posizionamentoDAO.inserisciPosizionamenti(posizionamenti);
        //aggiorna il DB
        ordineDAO.posiziona(ordine);
        operatore.esegui(-1);
        //Operatore Registra il Posizionamento e torna libero
        operatoreDAO.aggiorna(operatore);
        System.out.println("Ordine posizionato con successo!");
    }



    public List<Integer> eliminaPosizionamenti(int idOrdine) {
        PosizionamentoDAO posizionamentoDAO = new PosizionamentoDAO();
        return posizionamentoDAO.liberaPosizionamenti(idOrdine);
    }


    public void visualizza() {
        objectDAO.visualizza("Posizionamento", new HashMap<>());
    }


}
