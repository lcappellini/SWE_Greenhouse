package main.java.BuissnessLogic;

import main.java.DomainModel.Impianto.Attuatore;
import main.java.DomainModel.Impianto.Sensore;
import main.java.DomainModel.Impianto.Spazio;
import main.java.ORM.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GestioneAttuatori {
    public GestioneAttuatori() {}
    private String[] tipiAttuatori = {"Climatizzazione", "Lampada", "Operatore", "Irrigatore"};


    public void registraOperazione(Attuatore a, String descrizione, String data){
        OperazioneDAO dao = new OperazioneDAO();
        dao.registraAzione(a, descrizione, data);
    }

    public void visualizza(String tipoAttuatore, Map<String, Object> criteri) {
        ObjectDAO odao = new ObjectDAO();
        if(Arrays.asList(tipiAttuatori).contains(tipoAttuatore)){
            odao.visualizza(tipoAttuatore, criteri);
        }else{
            System.out.println("Attuatore non trovato");
        }
    }

    public List<Integer> restituisci(String nomeTabella, Map<String, Object> criteri) {
        ObjectDAO odao = new ObjectDAO();
        return restituisci(nomeTabella,criteri);
    }



}
