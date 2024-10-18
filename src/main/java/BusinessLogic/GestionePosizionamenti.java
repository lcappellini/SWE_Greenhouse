package main.java.BusinessLogic;

import main.java.DomainModel.Impianto.Posizionamento;
import main.java.DomainModel.Impianto.Spazio;
import main.java.Main;
import main.java.ORM.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class GestionePosizionamenti {
    private Spazio spazio;
    private PosizionamentoDAO posizionamentoDAO;

    public GestionePosizionamenti() {
        posizionamentoDAO = new PosizionamentoDAO();
    }

    //FIXME!!
    public void creaPosizionamento(ArrayList<Posizionamento> posizionamenti) throws SQLException {

        //inserimentoPosizionamenti
        posizionamentoDAO.inserisciPosizionamenti(posizionamenti);

    }

    public void visualizza() {
       // objectDAO.visualizza("Posizionamento", new HashMap<>());
    }

    public ArrayList<Posizionamento> eliminaPosizionamentiByOrdine(int idOrdine) throws SQLException {
        Map<String, Object> m  = new HashMap<>();
        m.put("ordine", idOrdine);
        ArrayList<Posizionamento> posizionamenti =  posizionamentoDAO.get(m);
        if(posizionamentoDAO.eliminaPosizionamentiByOrdine(idOrdine)){
            return posizionamenti;
        }
        return null;
    }
}
