package main.java.BuissnessLogic;

import main.java.DomainModel.Impianto.*;
import main.java.ORM.FotosensoreDAO;
import main.java.ORM.IgrometroAriaDAO;
import main.java.ORM.SensoreDAO;
import main.java.ORM.TermometroDAO;

public class GestioneSensori {
    public GestioneSensori() {}

    public void registraMisura(Spazio spazio){
        for(Sensore s : spazio.getSensori()){
            SensoreDAO sensoreDAO = null;

            switch (s.tipoSensore()) {
                case "Termometro":
                    sensoreDAO = new TermometroDAO();
                    break;
                case "IgrometroAria":
                    sensoreDAO = new IgrometroAriaDAO();
                    break;
                case "Fotosensore":
                    sensoreDAO = new FotosensoreDAO();
                    break;
                default:
                    System.out.println("Tipo di sensore non supportato: " + s.tipoSensore());
                    continue;
            }

            try {
                sensoreDAO.registraMisura(s);
            } catch (Exception e) {
                System.err.println("Errore durante la misura del sensore " + s.tipoSensore() + ": " + e.getMessage());
                e.printStackTrace();
            }


        }
    }
    public void misura(Posizione posizione){
        //...
    }
}
