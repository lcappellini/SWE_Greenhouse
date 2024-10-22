package main.java.BusinessLogic;

import main.java.DomainModel.Cliente;
import main.java.DomainModel.Ordine;
import main.java.DomainModel.Pianta.Pianta;
import main.java.ORM.ClienteDAO;
import main.java.ORM.OrdineDAO;
import main.java.ORM.PiantaDAO;
import main.java.ORM.PosizioneDAO;

import java.sql.SQLException;
import java.util.Map;

public class ClienteController {
    private Cliente cliente;

    public ClienteController(Cliente c) {
        this.cliente = c;
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
                o.setId(ordineDAO.inserisciOrdine(o));
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

    public void pagaEritiraOrdine(Ordine ordine) {
        OrdineDAO ordineDAO = new OrdineDAO();
        PiantaDAO piantaDAO = new PiantaDAO();

        try{
            ordineDAO.aggiorna(ordine.getId(), Map.of("stato","ritirato"));
            piantaDAO.elimina(ordine.getId());
        }catch (SQLException ignored) {

        }
    }
}