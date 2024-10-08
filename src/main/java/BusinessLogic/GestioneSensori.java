package main.java.BusinessLogic;

import main.java.DomainModel.Impianto.*;
import main.java.ORM.*;

public class GestioneSensori {
    public GestioneSensori() {}

    public void registraMisura(Sensore s){
        ObjectDAO dao = new ObjectDAO();
        try {
            /*sensoreDAO.registraMisura(s);*/
            dao.aggiorna(s);
        } catch (Exception e) {
            System.err.println("Errore durante la misura del sensore " + s.tipoSensore() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void misura(Posizione posizione){
        //...
    }
}
