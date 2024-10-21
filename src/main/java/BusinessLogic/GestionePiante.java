package main.java.BusinessLogic;

import main.java.DomainModel.Pianta.Pianta;
import main.java.ORM.PiantaDAO;

import java.util.ArrayList;
import java.util.Map;

public class GestionePiante {
    public GestionePiante() {}

    public ArrayList<Pianta> get(Map<String, Object> criteri){
        PiantaDAO piantaDAO = new PiantaDAO();
        return piantaDAO.get(criteri);
    }
}
