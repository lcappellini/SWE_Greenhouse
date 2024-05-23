package main.java.BuissnessLogic;

import main.java.DomainModel.Impianto.Ambiente;
import main.java.DomainModel.Impianto.Operatore;
import main.java.DomainModel.Impianto.Posizione;
import main.java.DomainModel.Ordine;
import main.java.ORM.PosizionamentoDAO;

import java.util.ArrayList;


public class GestionePosizionamenti {
    private Ambiente ambiente;

    public GestionePosizionamenti() {

    }
    public void creaPoisizionamento(Ordine ordine, ArrayList<Posizione> posizioni) throws Exception {
        PosizionamentoDAO posizionamentoDAO = new PosizionamentoDAO();
        posizionamentoDAO.creaPosizionamento(ordine, posizioni);
    }

    public ArrayList<Posizione> liberaPosizionamenti(int idOrdine) {
        PosizionamentoDAO posizionamentoDAO = new PosizionamentoDAO();
        return posizionamentoDAO.liberaPosizionamenti(idOrdine);
    }


    public void visualizzaPosizionamenti(int idOridne) {
        PosizionamentoDAO posizionamentoDAO = new PosizionamentoDAO();
        posizionamentoDAO.visualizzaPosizionamenti(idOridne);
    }
}
