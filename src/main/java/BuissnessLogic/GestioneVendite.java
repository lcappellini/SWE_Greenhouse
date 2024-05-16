package main.java.BuissnessLogic;

import main.java.DomainModel.Ordine;

public class GestioneVendite {

    public boolean richiediCommissione(Ordine c){
        if(GestioneImpianto.richiediOrdine(o)){
            System.out.println("Ordine accettato correttamente");
            return true;
        }else {
            System.out.println("Ordine non accettato");
            return false;
        }
    }

}
