package main.java.BusinessLogic;

import main.java.DomainModel.Impianto.Posizione;
import main.java.ORM.*;

import java.util.ArrayList;
import java.util.Map;

public class GestionePosizioni {

    public GestionePosizioni(){}

    public ArrayList<Posizione> get(Map<String, Object> criteri) {
        PosizioneDAO posizioneDAO = new PosizioneDAO();
        return posizioneDAO.get(criteri);
    }
}
