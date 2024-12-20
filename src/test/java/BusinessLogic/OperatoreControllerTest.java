package test.java.BusinessLogic;

import main.java.BusinessLogic.*;
import main.java.DomainModel.*;
import main.java.ORM.OperatoreDAO;
import main.java.ORM.OrdineDAO;
import main.java.ORM.PiantaDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class OperatoreControllerTest {

    OperatoreController operatoreController;
    Operatore operatore;
    Ordine ordine;

    @BeforeEach
    public void setUp(){

        AdminExtraController adminExtraController = new AdminExtraController();
        try{
            adminExtraController.resetDatabase();
            adminExtraController.createDatabase();
            adminExtraController.defaultDatabase();
        }catch (SQLException e){
            System.out.println("Errore durante il reset del database");
        }

        OperatoreDAO oDao = new OperatoreDAO();
        operatore = oDao.accedi("ferrari@email.it","123"); //operatore valido
        operatoreController = new OperatoreController();

        //setup: richiesta di un ordine da parte di un cliente (valido)
        LoginClienteController loginClienteController = new LoginClienteController();
        Cliente cliente = loginClienteController.accedi("mario@email.it","123");
        ClienteController clienteController = new ClienteController();
        ArrayList<Pianta> piante = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            piante.add(new Pianta("Geranio", StatoPianta.da_piantare));
        ordine = new Ordine(cliente.getId(), piante);
        clienteController.richiediNuovoOrdine(ordine);
    }

    @Test
    public void completaOrdineTest_Success(){
        System.out.println("--Test Completa Ordine (Success)--");
        System.out.println("-Esegue il completamento di un ordine");
        System.out.println("-Ordine da completare");
        operatoreController.piantaOrdine(ordine, operatore);
        OrdineDAO ordineDAO = new OrdineDAO();
        ordineDAO.aggiorna(ordine.getId(), Map.of("stato", StatoOrdine.da_completare.getId()));
        ordine.setStato(StatoOrdine.da_completare);
        assertTrue(operatoreController.completaOrdine(ordine, operatore));
        System.out.println("-Test superato!");
    }

    @Test
    public void completaOrdineTest_Fail(){
        System.out.println("--Test Completa Ordine (Fail)--");
        System.out.println("-Esegue il completamento di un ordine");
        System.out.println("-Ordine non ancora piantato");
        assertFalse(operatoreController.completaOrdine(ordine, operatore));
        System.out.println("-Test superato!");
    }

    @Test
    public void checkupPianteTest_Success(){
        System.out.println("--Test Check-Up Piante (Success)--");
        System.out.println("-Esegue un Check-Up dello stato delle piante");
        System.out.println("-Alcune piante hanno bisogno di cure");
        operatoreController.piantaOrdine(ordine, operatore);
        ArrayList<Pianta> piante = ordine.getPiante();
        PiantaDAO piantaDAO = new PiantaDAO();
        for (Pianta pianta : piante) {
            pianta.setStatoHaBisogno();
            piantaDAO.aggiorna(pianta.getId(), Map.of("stato", pianta.getStatoId()));
        }
        ArrayList<Pianta> pianteDaCurare = operatoreController.checkupPiante(operatore, 0);
        assertEquals(pianteDaCurare.size(), piante.size());
        System.out.println("-Test superato!");
    }

    @Test
    public void checkupPianteTest_Fail(){
        System.out.println("--Test Check-Up Piante (Fail)--");
        System.out.println("-Esegue un Check-Up dello stato delle piante");
        System.out.println("-Nessuna pianta ha bisogno di cure");
        operatoreController.piantaOrdine(ordine, operatore);
        ArrayList<Pianta> piante = ordine.getPiante();
        PiantaDAO piantaDAO = new PiantaDAO();
        for (Pianta pianta : piante) {
            pianta.setStatoNonHaBisogno();
            piantaDAO.aggiorna(pianta.getId(), Map.of("stato", pianta.getStatoId()));
        }
        ArrayList<Pianta> pianteDaCurare = operatoreController.checkupPiante(operatore, 0);
        assertEquals(pianteDaCurare.size(), 0);
        System.out.println("-Test superato!");
    }

    @Test
    public void curaPiantaTest(){
        System.out.println("--Test Cura Pianta--");
        System.out.println("-Cura una pianta");
        Pianta pianta = ordine.getPiante().get(0);
        PiantaDAO piantaDAO = new PiantaDAO();
        pianta.setStatoHaBisogno();
        piantaDAO.aggiorna(pianta.getId(), Map.of("stato", pianta.getStatoId()));
        operatoreController.curaPianta(pianta, operatore);
        assertEquals(pianta.getStato(), StatoPianta.curata_sta_crescendo);
        System.out.println("-Test superato!");
    }

    @Test
    public void piantaOrdineTest(){
        assertTrue(operatoreController.piantaOrdine(ordine, operatore));
    }
}
