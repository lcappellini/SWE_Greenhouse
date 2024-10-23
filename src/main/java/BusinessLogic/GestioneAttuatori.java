package main.java.BusinessLogic;

import main.java.DomainModel.Impianto.*;
import main.java.ORM.*;

public class GestioneAttuatori {
    public GestioneAttuatori() {}

    public void registraOperazione(Attuatore a, String descrizione, String data){
        OperazioneDAO dao = new OperazioneDAO();
        dao.registraAzione(a, descrizione, data);
    }

}
