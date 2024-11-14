package test.java.ORM;

import main.java.BusinessLogic.AdminExtraController;
import main.java.BusinessLogic.ClienteController;
import main.java.BusinessLogic.LoginClienteController;
import main.java.BusinessLogic.OperatoreController;
import main.java.DomainModel.*;
import main.java.ORM.OperatoreDAO;
import main.java.ORM.OrdineDAO;
import main.java.ORM.PiantaDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OrdineDAOTest {
    Ordine ordineRichiesto;
    ArrayList<Pianta> piante;
    OrdineDAO ordineDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        AdminExtraController adminExtraController = new AdminExtraController();
        try{
            adminExtraController.resetDatabase();
            adminExtraController.createDatabase();
            adminExtraController.defaultDatabase();
        }catch (SQLException e){
            System.out.println("Errore durante il reset del database");
        }
        piante = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            piante.add(new Pianta("Geranio", StatoPianta.da_ritirare));
        }
        for (int i = 0; i < 2; i++) {
            piante.add(new Pianta("Rosa", StatoPianta.da_ritirare));
        }
        for (int i = 0; i < 4; i++) {
            piante.add(new Pianta("Girasole", StatoPianta.da_ritirare));
        }
        ordineRichiesto = new Ordine(1, piante);
        ordineDAO = new OrdineDAO();
        int idOrdineRichiesto = ordineDAO.inserisciOrdine(ordineRichiesto);
        ordineRichiesto.setId(idOrdineRichiesto);
        PiantaDAO piantaDAO = new PiantaDAO();
        piantaDAO.inserisci(ordineRichiesto.getPiante(), ordineRichiesto.getId());
    }

    @Test
    public void testInitPianteOrdineDAO() {
        System.out.println("--Test Inizializzazione Piante con richiesta a OrdineDAO--");
        System.out.println("Verifica che OrdineDAO, tramite PiantaDAO, inizializzi la lista di piante");

        ArrayList<Ordine> ordini = ordineDAO.get(Map.of("id", ordineRichiesto.getId()));
        assertFalse(ordini.isEmpty()); // non deve essere vuoto

        Ordine ordineOttenuto = ordini.get(0);
        assertNotNull(ordineOttenuto); // non deve essere null

        // verifico che la quantità di piante totali sia uguale
        assertEquals(ordineOttenuto.getnPiante(), ordineRichiesto.getnPiante());

        // creo una mappa che conta le piante di ogni tipo RICHIESTE
        Map<String, Integer> counterRichieste = new HashMap<>();
        for (Pianta p : ordineRichiesto.getPiante()) {
            if (counterRichieste.containsKey(p.getTipoPianta()))
                counterRichieste.put(p.getTipoPianta(), counterRichieste.get(p.getTipoPianta()) + 1);
            else
                counterRichieste.put(p.getTipoPianta(), 1);
        }

        // creo una mappa che conta le piante di ogni tipo OTTENUTE
        Map<String, Integer> counterOttenute = new HashMap<>();
        for (Pianta p : ordineOttenuto.getPiante()) {
            if (counterOttenute.containsKey(p.getTipoPianta()))
                counterOttenute.put(p.getTipoPianta(), counterOttenute.get(p.getTipoPianta()) + 1);
            else
                counterOttenute.put(p.getTipoPianta(), 1);
        }

        // verifico che le quantità di piante siano uguali
        for (String key : counterOttenute.keySet()) {
            assertEquals(counterOttenute.get(key), counterRichieste.get(key));
        }
        System.out.println("Test superato!");
    }

}
