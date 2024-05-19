package main.java.BuissnessLogic;

import main.java.DomainModel.Impianto.Ambiente;
import main.java.DomainModel.Impianto.Operatore;
import main.java.DomainModel.Impianto.Posizionamento;
import main.java.DomainModel.Ordine;
import main.java.ORM.PosizionamentoDAO;


public class GestionePosizionamenti {
    private Ambiente ambiente;

    public GestionePosizionamenti() {

    }
    public void creaPoisizionamento(){

    }

    public void liberaPosizionamentoId(int idPosizionamento){
        PosizionamentoDAO posizionamentoDAO = new PosizionamentoDAO();
        posizionamentoDAO.rimuoviPosizionamentoId(idPosizionamento);
    }

    public void liberaPosizionamentoPosizione(int idPosizione){
        PosizionamentoDAO posizionamentoDAO = new PosizionamentoDAO();
        posizionamentoDAO.rimuoviPosizionamentoPosizione(idPosizione);
    }

}
