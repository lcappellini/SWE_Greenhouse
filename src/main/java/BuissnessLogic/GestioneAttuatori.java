package main.java.BuissnessLogic;

import main.java.DomainModel.Impianto.Attuatore;
import main.java.DomainModel.Impianto.Sensore;
import main.java.DomainModel.Impianto.Spazio;
import main.java.ORM.AttuatoreDAO;
import main.java.ORM.ClimatizzazioneDAO;
import main.java.ORM.LampadaDAO;

public class GestioneAttuatori {
    public GestioneAttuatori() {}


    public void registraAzione(Spazio spazio) {
        for (Attuatore a : spazio.getAttuatori()) {
            AttuatoreDAO adao = null;
            switch (a.tipoAttuatore()){
                case "Climatizzazione" ->{
                    adao = new ClimatizzazioneDAO();
                }
                case "Lampada" -> {
                    adao = new LampadaDAO();
                }
            }
            if(adao != null) {
                adao.registraAzione(a);
            }else {
                System.out.println("Attuatore non trovato");
                break;
            }
        }
    }



}
