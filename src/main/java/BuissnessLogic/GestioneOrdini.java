package main.java.BuissnessLogic;

import main.java.DomainModel.Ordine;
import main.java.ORM.ImpiantoDAO;
import main.java.ORM.OrdineDAO;


import java.sql.SQLException;
import java.util.ArrayList;

public class GestioneOrdini {

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

}
