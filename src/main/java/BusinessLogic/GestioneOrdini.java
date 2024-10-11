package main.java.BusinessLogic;

import main.java.DomainModel.Cliente;
import main.java.DomainModel.Impianto.Operatore;
import main.java.DomainModel.Ordine;
import main.java.DomainModel.Pianta.Pianta;
import main.java.ORM.*;


import java.sql.SQLException;
import java.util.*;

public class GestioneOrdini {
    private OrdineDAO ordineDAO;
    private PosizioneDAO posizioneDAO;
    private ObjectDAO objectDAO;
    private PosizionamentoDAO posizionamentoDAO;
    private PiantaDAO piantaDAO;
    private OperatoreDAO operatoreDAO;


    public GestioneOrdini() {
        ordineDAO = new OrdineDAO();
        posizioneDAO = new PosizioneDAO();
        objectDAO = new ObjectDAO();
        posizionamentoDAO = new PosizionamentoDAO();
        piantaDAO = new PiantaDAO();

    }
    public void setPosizioneDAO(PosizioneDAO posizioneDAO) {
        this.posizioneDAO = posizioneDAO;
    }
    public void setOrdineDAO(OrdineDAO ordineDAO) {
        this.ordineDAO = ordineDAO;
    }

    public void creazioneOrdine(Ordine ordine){

            if (posizioneDAO.verificaNonAssegnate(ordine.getnPiante())) {

                ordineDAO.inserisciOrdine(ordine);
                posizioneDAO.assegna(ordine.getnPiante());
            } else {
                System.out.println("Posizioni insufficienti per questo ordine.");
            }

    }


    public Ordine getOrdineDaPosizionare() {
        Ordine o = null;
        Scanner scanner1 = new Scanner(System.in);
        Map<String, Object> criteria = new HashMap<>();;
        criteria.put("stato", "da posizionare");
        ordineDAO.visualizza(criteria);
        System.out.println("Inserire ID dell'ordine da posizionare: ");
        int idOrdine = Integer.parseInt(scanner1.nextLine());
        o = ordineDAO.getById(idOrdine);
        if(o.getStato().equals("da posizionare")){
            return o;
        }
        return null;
    }

    public ArrayList<Ordine> vediOrdini(Cliente cliente){
        return  ordineDAO.vediOrdini(cliente);
    }

    public void pagaERitiraOrdine(Cliente cliente, Ordine ordine, Operatore operatore) {
        if(ordine.getCliente() == cliente.getId() && !operatore.attivo() && ordine.getStato().equals("pronto")){
            //L'operatore esegue 1 ( = eliminazione posizionamento e liberazione delle posizioni, simulato)
            String descrizione = operatore.esegui(1);
            System.out.println(descrizione);
            piantaDAO.ritira(posizionamentoDAO.ritira(ordine.getId())); // vengono eliminate le piante posizionate
            posizioneDAO.liberaPosizioni(posizionamentoDAO.liberaPosizionamenti(ordine.getId())); // vengono eliminati i posizionamenti e liberate le posizioni

            //Viene registrata l'operazione su DB
            operatoreDAO.registraAzione(operatore, descrizione);
            operatore.esegui(-1); // L'operatore torna libero

            ordineDAO.pagaERitiraOrdine(ordine);
        }else{
            System.out.print("L'ordine non può essere ritirato : ");
            if(!ordine.getStato().equals("pronto")){
                System.out.println("ordine non pronto");
            } else if (operatore.attivo()) {
                System.out.println("operatore occupato");
            } else if (cliente.getId() != ordine.getCliente()) {
                System.out.println("l'ordine non appartiene al cliente");
            }
        }
    }

    public void visualizzaOrdini(Map<String, Object> criteri) {
        ObjectDAO objectDAO = new ObjectDAO();
        objectDAO.visualizza("Ordine", criteri);
    }

    public void visualizzaOrdiniCliente(Cliente cliente) {
        Map<String, Object> criteri = new HashMap<>();
        criteri.put("cliente", cliente.getId());
        objectDAO.visualizza("Ordine", criteri);
    }

    public boolean ordiniPronti(Cliente cliente) {
        return ordineDAO.ordiniPronti(cliente);
    }


    public ArrayList<Pianta> sceltaPiante(){
        Scanner scanner = new Scanner(System.in);
        String input;
        String tipoPianta = "";

        System.out.println(
                """
                \s
                 Scegli il tipo di pianta:
                 1. Basilico
                 2. Geranio
                 3. Rosa
                 4. Girasole
               \s"""
        );

        input = scanner.nextLine();

        switch (input) {
            case "1" -> {tipoPianta = "Basilico";}
            case "2" -> {tipoPianta = "Geranio";}
            case "3" -> {tipoPianta = "Rosa";}
            case "4" -> {tipoPianta = "Girasole";}
            default -> System.out.println("Input invalido, si prega di riprovare.");
        }

        Scanner scanner2 = new Scanner(System.in);
        System.out.println("\nQuantità: ");
        int nPiante = Integer.parseInt(scanner2.nextLine());

        ArrayList<Pianta> piante = new ArrayList<>();
        for(int i = 0; i < nPiante; i++){
            piante.add(new Pianta(tipoPianta));
        }

        Scanner scanner3 = new Scanner(System.in);
        System.out.println("Vuoi aggiungere altre piante? (s/n)");
        String input2 = scanner3.nextLine();
        switch (input2) {
            case "s" -> {piante.addAll(sceltaPiante());}
            case "n" -> {break;}
            default -> System.out.println("Input invalido, si prega di riprovare.");
        }
        return piante;
    }

    public void rimuoviUltimoOrdine() {
        ordineDAO.rimuoviUltimoOrdine();
    }

    public void visualizzaOrdiniDaPosizionare() {
        ObjectDAO objectDAO = new ObjectDAO();
        Map<String, Object> criteri = new HashMap<>();
        criteri.put("stato", "da posizionare");
        objectDAO.visualizza("Ordine", criteri);
    }

    public Ordine getOrdinePronto(Cliente cliente) {
        Ordine o = null;
        Scanner scanner1 = new Scanner(System.in);
        Map<String, Object> criteria = new HashMap<>();
        criteria.put("cliente",cliente.getId());
        criteria.put("stato","pronto");
        ordineDAO.visualizza(criteria);
        System.out.println("Inserire ID dell'ordine da pagare: ");
        int idOrdine = Integer.parseInt(scanner1.nextLine());
        o = ordineDAO.getById(idOrdine);
        if(o.getCliente() == cliente.getId() && o.getStato().equals("pronto")){
            return o;
        }
        return null;
    }

    public void aggiornaOrdine(Ordine ordine) throws SQLException {
        ordineDAO.aggiornaOrdine(ordine);  // Chiama OrdineDAO per aggiornare l'ordine nel database
    }
}
