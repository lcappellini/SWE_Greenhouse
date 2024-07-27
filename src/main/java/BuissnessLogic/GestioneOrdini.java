package main.java.BuissnessLogic;

import main.java.DomainModel.Cliente;
import main.java.DomainModel.Ordine;
import main.java.ORM.ImpiantoDAO;
import main.java.ORM.ObjectDAO;
import main.java.ORM.OrdineDAO;
import main.java.ORM.PosizioneDAO;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class GestioneOrdini {

    public GestioneOrdini() {}

    public int richiediNuovoOrdine(Ordine ordine) throws SQLException, ClassNotFoundException {
        //FIXME non sarebbe megio controllare a parte?
        ImpiantoDAO impiantoDAO = new ImpiantoDAO();
        PosizioneDAO posizioneDAO = new PosizioneDAO();

        if(posizioneDAO.riserva(ordine.getnPiante())){
            System.out.println("Ordine accettato.");
            OrdineDAO ordineDAO = new OrdineDAO();
            return ordineDAO.addOrdine(ordine);
        }else {
            System.out.println("Ordine non accettato");
            return -1;
        }
    }

    public Ordine getOrdineDaPosizionare(int idOrdine){
        OrdineDAO ordineDAO = new OrdineDAO();
        return ordineDAO.getOrdineDaPosizionare(idOrdine);
    }

    public ArrayList<Ordine> vediOrdini(Cliente cliente){
        OrdineDAO ordineDAO = new OrdineDAO();
        return  ordineDAO.vediOrdini(cliente);
    }

    public void pagaERitiraOrdine(Cliente cliente, int idOrdine) {
        OrdineDAO ordineDAO = new OrdineDAO();
        ordineDAO.pagaERitiraOrdine(cliente,idOrdine);
    }

    public void visualizzaOrdini(Map<String, Object> criteri) {
        ObjectDAO objectDAO = new ObjectDAO();
        objectDAO.visualizza("Ordine", criteri);
    }

}
