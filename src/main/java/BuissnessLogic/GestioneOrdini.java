package main.java.BuissnessLogic;

import main.java.DomainModel.Cliente;
import main.java.DomainModel.Ordine;
import main.java.ORM.ImpiantoDAO;
import main.java.ORM.OrdineDAO;


import java.sql.SQLException;
import java.util.ArrayList;

public class GestioneOrdini {

    public GestioneOrdini() {}

    public int richiediNuovoOrdine(Ordine ordine) throws SQLException, ClassNotFoundException {
        ImpiantoDAO impiantoDAO = new ImpiantoDAO();

        if(impiantoDAO.verificaDisponibilita(ordine.getnPiante())){
            System.out.println("Ordine accettato.");
            OrdineDAO ordineDAO = new OrdineDAO();
            return ordineDAO.addOrdine(ordine);
            //impiantoDAO.creaPosizionamento(ordine);
        }else {
            System.out.println("Ordine non accettato");
            return -1;
        }
    }

    public ArrayList<Ordine> vediOrdini(Cliente cliente){
        OrdineDAO ordineDAO = new OrdineDAO();
        return ordineDAO.ottieniOrdini(cliente);
    }

    public void completaOrdine(Cliente cliente) {
        OrdineDAO ordineDAO = new OrdineDAO();
        ordineDAO.completaOrdine(cliente);
    }
}
