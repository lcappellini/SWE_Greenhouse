package test.java.BusinessLogic;

import main.java.BusinessLogic.GestioneOrdini;
import main.java.DomainModel.Cliente;
import main.java.DomainModel.Ordine;
import main.java.DomainModel.Pianta;
import main.java.DomainModel.Operatore;
import main.java.ORM.OrdineDAO;
import main.java.ORM.PosizioneDAO;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;

class GestioneOrdiniTest {

    private Ordine ordine;
    private Cliente cliente;
    private GestioneOrdini gestioneOrdini;
    private PosizioneDAO mockPosizioneDAO; // Mock per PosizioneDAO
    private OrdineDAO mockOrdineDAO;       // Mock per OrdineDAO

    @BeforeEach
    void setUp() {/*
        gestioneOrdini = new GestioneOrdini();  // Istanza della classe GestioneOrdini

        // Creazione dei mock per i DAO
        mockPosizioneDAO = mock(PosizioneDAO.class);
        mockOrdineDAO = mock(OrdineDAO.class);

        // Imposta i mock come DAO nella classe GestioneOrdini
        gestioneOrdini.setPosizioneDAO(mockPosizioneDAO);
        gestioneOrdini.setOrdineDAO(mockOrdineDAO);

        cliente = new Cliente(2, "Luigi", "Verdi", "luigi@email.it"); */
    }

    @Test
    void testCreazioneOrdine_PosizioniInsufficienti() throws SQLException, ClassNotFoundException {
        // Definisci il comportamento del mock: nessuna posizione disponibile
        when(mockPosizioneDAO.verificaNonAssegnate(4)).thenReturn(false);

        Ordine ordine = new Ordine(cliente.getId(), creaRose(4));
        //gestioneOrdini.creazioneOrdine(ordine); //FIXME REMOVED, SO USE ANOTHED METHOD

        // Verifica che `inserisciOrdine` non venga mai chiamato, perché le posizioni sono insufficienti
        verify(mockOrdineDAO, never()).inserisciOrdine(any(Ordine.class));
        // Verifica che `assegna` non venga mai chiamato
        verify(mockPosizioneDAO, never()).assegna(anyInt());
    }

    @Test
    void testCreazioneOrdine_PosizioniSufficienti() throws SQLException, ClassNotFoundException {
        // Definisci il comportamento del mock: posizioni sufficienti disponibili
        when(mockPosizioneDAO.verificaNonAssegnate(3)).thenReturn(true);

        Ordine ordine = new Ordine(cliente.getId(), creaRose(3));
        //gestioneOrdini.creazioneOrdine(ordine); //FIXME REMOVED, SO USE ANOTHED METHOD

        // Verifica che l'ordine sia stato inserito e che le posizioni siano state assegnate
        verify(mockOrdineDAO, times(1)).inserisciOrdine(ordine);
        verify(mockPosizioneDAO, times(1)).assegna(3);  // Verifica che assegna sia chiamato con il numero corretto
    }

    @Test
    void testPagaERitira_OperatoreOccupato() throws SQLException, ClassNotFoundException {
        Ordine ordine = new Ordine(cliente.getId(), creaRose(3));
        ordine.setStato("pronto");
        Operatore operatore = new Operatore(1, "Alessandro", "Ferrari", "ferrari@email.it", true);  // Operatore occupato

        // Simula l'operazione di pagamento e ritiro
        //gestioneOrdini.pagaERitiraOrdine(cliente, ordine, operatore);

        // Qui puoi verificare che il comportamento corretto sia seguito, ad esempio, verificando che non ci siano aggiornamenti
        verify(mockOrdineDAO, never()).aggiornaOrdine(any(Ordine.class));
    }

    @Test
    void testPagaERitira_OrdineNonDelCliente() throws SQLException, ClassNotFoundException {
        Ordine ordine = new Ordine(1, creaRose(3));  // Ordine di un cliente diverso
        ordine.setStato("pronto");
        Operatore operatore = new Operatore(1, "Alessandro", "Ferrari", "ferrari@email.it", false);  // Operatore disponibile

        //gestioneOrdini.pagaERitiraOrdine(cliente, ordine, operatore);

        // Verifica che l'ordine non venga pagato/ritirato
        verify(mockOrdineDAO, never()).aggiornaOrdine(any(Ordine.class));
    }

    @Test
    void testPagaERitira_OrdineNonPronto() throws SQLException, ClassNotFoundException {
        Ordine ordine = new Ordine(cliente.getId(), creaRose(3));
        Operatore operatore = new Operatore(1, "Alessandro", "Ferrari", "ferrari@email.it", false);  // Operatore disponibile

        // L'ordine non è pronto
        //gestioneOrdini.pagaERitiraOrdine(cliente, ordine, operatore);

        // Verifica che non ci siano aggiornamenti se l'ordine non è pronto
        verify(mockOrdineDAO, never()).aggiornaOrdine(any(Ordine.class));
    }

    @Test
    void testPagaERitira_OrdineCorretto() throws SQLException, ClassNotFoundException {
        Ordine ordine = new Ordine(cliente.getId(), creaRose(3));
        ordine.setStato("pronto");
        Operatore operatore = new Operatore(1, "Alessandro", "Ferrari", "ferrari@email.it", false);  // Operatore disponibile

        //gestioneOrdini.pagaERitiraOrdine(cliente, ordine, operatore);

        // Verifica che l'ordine venga aggiornato correttamente
        verify(mockOrdineDAO, times(1)).aggiornaOrdine(ordine);
    }

    // Helper per creare le rose
    private ArrayList<Pianta> creaRose(int numero) {
        ArrayList<Pianta> piante = new ArrayList<>();
        for (int i = 0; i < numero; i++) {
            piante.add(new Pianta("Rosa"));
        }
        return piante;
    }
}

