package main.java.BusinessLogic;

import main.java.DomainModel.Cliente;
import main.java.DomainModel.Ordine;
import main.java.ORM.ClienteDAO;
import main.java.ORM.OrdineDAO;
import main.java.ORM.PiantaDAO;
import main.java.ORM.PosizioneDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClienteController {
    private Cliente cliente;

    public ClienteController(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean aggiornaProfilo(String name, String newValue) {
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.modificaAttributo(cliente.getId(), name, newValue);
    }

    public boolean richiediNuovoOrdine(Ordine o) {
        try {
            PosizioneDAO posizioneDAO = new PosizioneDAO();
            OrdineDAO ordineDAO = new OrdineDAO();
            PiantaDAO piantaDAO = new PiantaDAO();

            if (posizioneDAO.verificaNonAssegnate(o.getnPiante())) {
                int idOrdine = ordineDAO.inserisciOrdine(o);
                if (idOrdine == -1)
                    return false;
                o.setId(idOrdine);
                piantaDAO.inserisci(o.getPiante(), o.getId());
                posizioneDAO.assegna(o.getnPiante());
                return true;
            }
            return false;
        } catch (SQLException ignored) {
            System.out.println("Ordine non accettato.");
        }
        return false;
    }

    public boolean pagaEritiraOrdine(Ordine ordine) {
        OrdineDAO ordineDAO = new OrdineDAO();
        PiantaDAO piantaDAO = new PiantaDAO();
        try{
            ordineDAO.aggiorna(ordine.getId(), Map.of("stato","ritirato"));
            piantaDAO.elimina(ordine.getId());
            return true;
        }catch (SQLException ignored) {

        }
        return false;
    }

    public ArrayList<Ordine> getOrdini(Map<String, Object> criteri) {
        OrdineDAO ordineDAO = new OrdineDAO();
        if(criteri == null){
            criteri = new HashMap<>();
        }
        criteri.put("cliente", cliente.getId());
        return ordineDAO.get(criteri);
    }
}