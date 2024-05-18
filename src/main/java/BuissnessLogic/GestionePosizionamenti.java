package main;

import main.java.DomainModel.Ordine;
import main.java.ORM.PosizionamentoDAO;


public class GestionePosizionamenti {
    private Ambiente ambiente;

    public GestionePosizionamenti() {

    }
    public void creaPoisizionamento(Ordine c, Operatore operatore){
        PosizionamentoDAO pdao = new PosizionamentoDAO();

        pdao.creaPosizionamento(c);
    }

}
