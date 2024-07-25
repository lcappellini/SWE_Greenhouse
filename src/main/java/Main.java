package main.java;

import main.java.BuissnessLogic.*;
import main.java.DomainModel.Cliente;
import main.java.DomainModel.Impianto.*;
import main.java.DomainModel.Ordine;
import main.java.DomainModel.Pianta.Pianta;
import main.java.ORM.AttuatoreDAO;
import main.java.ORM.OperazioneDAO;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        handleAction();
    }

    public static void handleAction() throws Exception {

        Scanner scanner = new Scanner(System.in);
        String input;

        do {

            System.out.println(
                    """
                    \s
                     GREENHOUSE
                     1. Cliente
                     2. Admin
                     3. Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> handleClient();
                case "2" -> handleAdmin();
                case "3" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);

    }



    public static void handleClient() throws SQLException, ClassNotFoundException {

        Scanner scanner = new Scanner(System.in);
        GestioneCliente gestioneCliente = new GestioneCliente();
        String input;

        do {

            System.out.println(
                    """
                    \s
                     PAGINA CLIENTE
                     1. Registrati
                     2. Accedi
                     3. Indietro
                     4. Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("\nNome: ");
                    String nome = scanner1.nextLine();
                    System.out.println("Cognome: ");
                    String cognome = scanner1.nextLine();
                    System.out.println("Email: ");
                    String email = scanner1.nextLine();
                    System.out.println("Password: ");
                    String password = scanner1.nextLine();

                    Cliente cliente = gestioneCliente.registraCliente(nome, cognome, email, password);

                    if (cliente != null)
                        handleClientAction(cliente);
                }
                case "2" -> {
                    Scanner scanner1 = new Scanner(System.in);

                    System.out.println("\nEmail: ");
                    String email = scanner1.nextLine();
                    System.out.println("Password: ");
                    String password = scanner1.nextLine();

                    Cliente cliente = gestioneCliente.accedi(email, password);

                    if (cliente != null)
                        handleClientAction(cliente);
                }
                case "3" -> {return;}
                case "4" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);

    }
    public static void handleClientAction(Cliente cliente) throws SQLException, ClassNotFoundException {

        Scanner scanner = new Scanner(System.in);
        GestioneOrdini gestioneOrdini = new GestioneOrdini();
        GestionePosizionamenti gestionePosizionamenti = new GestionePosizionamenti();
        GestionePosizioni gestionePosizioni = new GestionePosizioni();
        GestioneAttuatori gestioneAttuatori = new GestioneAttuatori();
        String input;

        do {

            System.out.println(
                    """
                    \s
                     PAGINA ORDINI
                     1. Richiedi nuovo ordine
                     2. Controlla i miei ordini
                     3. Paga e ritira ordine
                     4. Indietro
                     5. Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {

                    Ordine ordine = creaOrdine(cliente);
                    if (ordine != null){
                        int idOrdine = gestioneOrdini.richiediNuovoOrdine(ordine);
                        System.out.println("Il codice del tuo ordine e':" + idOrdine);
                    }
                }
                case "2" -> {

                    ArrayList<Ordine> ordini = gestioneOrdini.vediOrdini(cliente);
                    if (ordini != null){
                        System.out.println("+--------+----------------------+----------------+----------------------+----------+------------+------------+");

                        System.out.printf("| %-6s | %-20s | %-15s | %-20s | %-8s | %-10s | %-10s |\n",
                                "ID", "Cliente", "Data Consegna", "Tipo Pianta", "Quantità", "Stato", "Totale");
                        System.out.println("+--------+----------------------+----------------+----------------------+----------+------------+------------+");
                        for (Ordine ordine : ordini) {
                            System.out.printf("| %-6d | %-20s | %-15s | %-20s | %-8d | %-10s | %-10f |\n",
                                    ordine.getId(), ordine.getCliente().getEmail(), ordine.getDataConsegna(),
                                    ordine.getTipoPiante(), ordine.getnPiante(), ordine.getStato(), ordine.getPrezzo());
                        }
                        System.out.println("+--------+----------------------+----------------+----------------------+----------+------------+------------+");


                    }else {
                        System.out.println("Nessun ordine trovato");
                    }

                }
                case "3" -> {
                    //Ricerca dell'ordine da pagare
                    Scanner scanner1 = new Scanner(System.in);
                    Map<String, Object> criteria = new HashMap<>();
                    criteria.put("cliente",cliente.getId());
                    criteria.put("stato","pronto");
                    gestioneOrdini.visualizzaOrdini(criteria);
                    System.out.println("Inserire ID dell'ordine da pagare: ");
                    int idOrdine = Integer.parseInt(scanner1.nextLine());

                    //Ricerca del primo operatore libero
                    Map<String, Object> criterio = new HashMap<>();
                    criterio.put("occupato", false);
                    int idOperatore = gestioneAttuatori.restituisci("Operatore", criterio).get(0);
                    Operatore operatore = new Operatore(idOperatore);

                    //L'operatore esegue 1 ( = liberazione del posizionamento)
                    String descrizione = operatore.esegui(1);
                    System.out.println(descrizione);

                    //Il posizionamento viene eliminato e restituisce una lista di posizioni da liberare
                    List<Integer> posizioni = gestionePosizionamenti.liberaPosizionamenti(idOrdine);
                    gestionePosizioni.liberaPosizioni(posizioni);

                    //Viene registrata l'operazione su DB
                    OperazioneDAO oDAO = new OperazioneDAO();
                    String data = " "; //FIXME aggiungere logica per le date
                    oDAO.registraAzione(operatore, descrizione, data);
                    System.out.println(operatore.esegui(-1)); // L'operatore torna libero

                    //L'ordine viene segnato come ritirato e viene concluso
                    gestioneOrdini.pagaERitiraOrdine(cliente,idOrdine);
                }
                case "4" -> {return;}
                case "5" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);

    }
    public static Ordine creaOrdine(Cliente cliente) {
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println(
                    """
                    \s
                     Scegli il tipo di pianta:
                     1. Basilico
                     2. Geranio
                     3. Rosa
                     4. Girasole
                     5. Indietro
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    return completaOrdine(cliente, "Basilico");
                }
                case "2" -> {
                    return completaOrdine(cliente, "Geranio");
                }
                case "3" -> {
                    return completaOrdine(cliente, "Rosa");
                }
                case "4" -> {
                    return completaOrdine(cliente, "Girasole");
                }
                case "5" -> {return null;}
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);

    }
    public static Ordine completaOrdine(Cliente cliente,String tipoPianta){
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("\nQuantità: ");
        int nPiante = Integer.parseInt(scanner1.nextLine());
        System.out.println("Per quando? (gg-mm-aa): ");
        String dataConsegna = scanner1.nextLine();
        /*
        System.out.println("Vuoi aggiungere altre piante? (S/N)");
        String risposta = scanner1.nextLine();*/
        return new Ordine(cliente, tipoPianta, nPiante, dataConsegna);
    }

    public static void handleAdmin() throws Exception {

        Scanner scanner = new Scanner(System.in);
        String input;

        do {

            System.out.println(
                    """
                    \s
                     PAGINA ADMIN
                     1. Gestione Ordini-Posizionamenti
                     2. Gestione Spazi-Ambienti-Posizioni
                     4. Gestione Piante
                     5. Indietro
                     6. Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> handleOrdini();
                case "2" -> handleSpazi();
                case "3" -> handlePosizionamenti();
                case "4" -> handlePiante();
                case "5" -> {return;}
                case "6" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);

    }
    public static void handleOrdini() throws Exception {
        Scanner scanner = new Scanner(System.in);
        GestioneOrdini gestioneOrdini = new GestioneOrdini();
        GestionePosizionamenti gestionePosizionamenti = new GestionePosizionamenti();
        GestionePosizioni gestionePosizioni = new GestionePosizioni();
        GestioneAttuatori gestioneAttuatori = new GestioneAttuatori();
        String input;
        //FIXME PLz
        do {

            System.out.println(
                    """
                    \s
                     GESTIONE ORDINI & POSIZIONAMENTI
                     1. Visualizza ordini
                     2. Modifica ordine
                     3. Posiziona ordine
                     4. Visualizza Posizionamenti
                     4. Indietro
                     5. Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    gestioneOrdini.visualizzaOrdini(new HashMap<>());
                }
                case "2" -> {
                    //TODO modifica dei parametri dell'ordine
                }
                case "3" -> {
                    //FIXME da implementare : l'operatore deve mettere le piante dell'ordine in posizioni libere

                    //Ricerca Ordine da posizionare
                    Scanner scanner1 = new Scanner(System.in);
                    Map<String, Object> criteri = new HashMap<>();
                    criteri.put("stato", "da posizionare");
                    gestioneOrdini.visualizzaOrdini(criteri);
                    System.out.println("Inserire ID dell'ordine da posizionare: ");
                    int idOrdine = Integer.parseInt(scanner1.nextLine());
                    Ordine ordine = gestioneOrdini.getOrdineDaPosizionare(idOrdine);

                    //Ricerca Operatore libero
                    Scanner scanner2 = new Scanner(System.in);
                    Map<String, Object> criteri2 = new HashMap<>();
                    criteri2.put("occupato", false);
                    gestioneAttuatori.visualizza("Operatore", criteri2);
                    System.out.println("Indicare un operatore libero: (ID) ");
                    int idOperatore = Integer.parseInt(scanner2.nextLine());
                    Operatore operatore = new Operatore(idOperatore);

                    //Occupa Posizioni libere
                    Map<String, Object> criter3 = new HashMap<>();
                    criter3.put("assegnata", false);
                    List<Integer> posizioniOccupate = gestionePosizioni.occupa(ordine.getnPiante());

                    //L'operatore esegue la procedura di posizionamento (0)
                    String descrizione = operatore.esegui(0);
                    System.out.println(descrizione);

                    //Creazione posizionamento
                    gestionePosizionamenti.creaPosizionamento(ordine, posizioniOccupate, idOperatore);

                    //Operatore Registra il Posizionamento
                    OperazioneDAO oDAO = new OperazioneDAO();
                    String data = " "; //FIXME aggiungere logica per le date
                    oDAO.registraAzione(operatore, descrizione, data);
                    System.out.println(operatore.esegui(-1)); // L'operatore torna libero


                }
                case "4" -> {
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("Inserire ID dell'ordine delle posizioni da visualizzare: ");
                    int idOridne = Integer.parseInt(scanner1.nextLine());
                    gestionePosizionamenti.visualizzaPosizionamenti(idOridne);
                }
                case "5" -> {return;}
                case "6" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);
    }


    private static void handleSpazi() {
        Scanner scanner = new Scanner(System.in);
        String input;
        GestioneSpazi gestioneSpazi = new GestioneSpazi();
        do {

            System.out.println(
                    """
                    \s
                     GESTIONE AMBIENTI
                     1. Registra Spazio
                     2. Elimina Spazio
                     3. Visualizza tutti gli Spazi
                     4. Visualizza Spazio 
                     5. Gestisci Ambienti
                     6. Indietro
                     7. Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("\nNome Spazio: ");
                    String nome = scanner1.nextLine();
                    System.out.println("Descrizione Spazio: ");
                    String descrizione = scanner1.nextLine();
                    System.out.println("Numero di Ambienti inseribili: ");
                    int nAmbientiMax = Integer.parseInt(scanner1.nextLine());
                    gestioneSpazi.creaSpazio(nome, descrizione, nAmbientiMax);
                }
                case "2" -> {
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("\nID Spazio da eliminare: ");
                    int idSpazio = Integer.parseInt(scanner1.nextLine());
                    gestioneSpazi.rimuoviSpazio(idSpazio);
                }
                case "3" -> {
                    gestioneSpazi.visualizzaSpazi();
                }
                case "4" ->{
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("\nID Spazio da visualizzare: ");
                    int idSpazio = Integer.parseInt(scanner1.nextLine());
                    gestioneSpazi.visualizzaSpazio(idSpazio);
                }
                case "5" ->{
                    gestioneSpazi.visualizzaSpazi();
                    System.out.println("ID Spazio del quale gestire gli ambienti: ");
                    Scanner scanner1 = new Scanner(System.in);
                    int idSpazio = Integer.parseInt(scanner1.nextLine());
                    Spazio spazio = gestioneSpazi.getSpazio(idSpazio);
                    if(spazio != null) {
                        handleAmbienti(spazio);
                    }
                }
                case "6" -> {return;}
                case "7" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);
    }
    private static void handleAmbienti(Spazio spazio) {
        Scanner scanner = new Scanner(System.in);
        String input;
        GestioneAmbienti gestioneAmbienti = new GestioneAmbienti();
        spazio.setAmbienti(gestioneAmbienti.completaSpazio(spazio.getId()));
        do {

            System.out.println(
                    """
                    \s
                     GESTIONE SPAZI
                     1. Registra Ambiente
                     2. Elimina Ambiente
                     3. Visualizza Ambienti dell'Spazio
                     4. Monitora Ambiente
                     5. Gestisci Posizioni
                     6. Indietro
                     7. Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    if(spazio.getNAmbientiMax() > spazio.getAmbienti().size() ){
                        Scanner scanner1 = new Scanner(System.in);
                        System.out.println("Quante posizioni contiene al più?");
                        int nPosizioniMax = Integer.parseInt(scanner1.nextLine());
                        System.out.println("Id Termometro: ");
                        int idTermometro = Integer.parseInt(scanner1.nextLine());
                        System.out.println("Id Fotosensore: ");
                        int idFotosensore = Integer.parseInt(scanner1.nextLine());
                        System.out.println("Id IgrometroAria: ");
                        int idIgrometroAria = Integer.parseInt(scanner1.nextLine());
                        System.out.println("Id Climatizzazione: ");
                        int idClimatizzazione = Integer.parseInt(scanner1.nextLine());
                        System.out.println("Id Lampada: ");
                        int idLampada = Integer.parseInt(scanner1.nextLine());
                        gestioneAmbienti.creaAmbiente(spazio.getId(), nPosizioniMax,
                                idTermometro, idFotosensore, idIgrometroAria,
                                idClimatizzazione, idLampada);
                    }else{
                        System.out.println("Impossibile aggiungere ambienti: Spazio pieno!");
                    }

                }
                case "2" -> {
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("\nID Ambiente da eliminare: ");
                    int idAmbiente = Integer.parseInt(scanner1.nextLine());
                    gestioneAmbienti.rimuoviAmbiente(idAmbiente);
                }
                case "3" -> {
                    gestioneAmbienti.visualizzaAmbienti(spazio.getId());
                }
                case "4" -> {
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("Inserire ID dello ambiente da monitorare: ");
                    int idAmbiente = Integer.parseInt(scanner1.nextLine());
                    Ambiente ambiente = gestioneAmbienti.getAmbiente(idAmbiente);
                    if(ambiente != null) {
                        GestioneSensori gestioneSensori = new GestioneSensori();
                        GestioneAttuatori gestioneAttuatori = new GestioneAttuatori();
                        Scanner scanner2 = new Scanner(System.in);
                        System.out.println("Premi Invio per interrompere il ciclo di monitoraggio...");
                        LocalDateTime lt = LocalDateTime.now();
                        long i = 0;
                        Map<String, Boolean> accesi = new HashMap<>();
                        accesi.put("Climatizzazione", false);
                        accesi.put("Lampada", false);

                        Thread monitoringThread = new Thread(() -> monitorAmbiente(ambiente, gestioneAmbienti, gestioneSensori, gestioneAttuatori, accesi));


                        monitoringThread.start();

                        scanner2.nextLine(); // Aspetta che l'utente prema Invio
                        monitoringThread.interrupt(); // Interrompe il thread di monitoraggio

                        try {
                            monitoringThread.join(); // Attende la terminazione del thread
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
                case "5" ->{
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("ID Ambiente del quale gestire le posizioni: ");
                    int idAmbiente = Integer.parseInt(scanner1.nextLine());
                    Ambiente ambiente = gestioneAmbienti.getAmbiente(idAmbiente);
                    if(ambiente != null) {
                        handlePozioni(ambiente);
                    }
                }
                case "6" -> {return;}
                case "7" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);
    }

    // FIXME Biomonitoring da revisionare:
    private static void monitorAmbiente(Ambiente ambiente,GestioneAmbienti gestioneAmbienti, GestioneSensori gestioneSensori, GestioneAttuatori gestioneAttuatori, Map<String, Boolean> accesi) {
        LocalDateTime lt = LocalDateTime.now();
        long i = 0;
        Map<String, String> descrizioni = new HashMap<>();

        while (!Thread.currentThread().isInterrupted()) {
            try {
                //vengono generati i valori dei sensori
                ambiente.misura(lt, accesi);
                //vengono salvati gli attuatori che sono stati accesi
                descrizioni = ambiente.aziona();

                // Registra i dati e monitora lo ambiente
                for(Sensore s : ambiente.getSensori()){
                    gestioneSensori.registraMisura(s);
                }
                OperazioneDAO opDAO = new OperazioneDAO();
                for(Attuatore a: ambiente.getAttuatori()){
                    opDAO.registraAzione(a, descrizioni.get(a.tipoAttuatore()), lt.toString());
                }
                //gestioneAttuatori.registraAzione(ambiente);
                //gestioneAmbienti.monitoraAmbiente(ambiente.getId());

                System.out.println("+----------------------+");
                System.out.println("| Parametro            | Valore                |");
                System.out.println("+----------------------+-----------------------+");
                System.out.printf("| Temperatura          | %-21f|\n", ambiente.getSensore("Termometro").getValore());
                System.out.printf("| Percentuale Acqua    | %-21f|\n", ambiente.getSensore("IgrometroAria").getValore());
                System.out.printf("| Percentuale Luce     | %-21f|\n", ambiente.getSensore("Fotosensore").getValore());
                System.out.printf("| Climatizzazione Acceso| %-21s|\n", accesi.get("Climatizzazione") ? "Sì" : "No");
                System.out.printf("| Lampada Accesa       | %-21s|\n", accesi.get("Lampada") ? "Sì" : "No");
                System.out.println("+----------------------+-----------------------+");

                Thread.sleep(2000); // Pausa di 2 secondi tra i cicli

                accesi = ambiente.getAttuatoriAccesi();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Reimposta lo stato di interruzione
            }
            i += 2;
            lt = lt.plusHours(i);
        }
    }

    private static void handlePozioni(Ambiente ambiente) {
        Scanner scanner = new Scanner(System.in);
        String input;
        GestionePosizioni gestionePosizioni = new GestionePosizioni();
        ambiente.setPosizioni(gestionePosizioni.completaAmbiente(ambiente.getId()));
        do {

            System.out.println(
                    """
                    \s
                     GESTIONE POSIZIONI
                     1. Registra Posizone
                     2. Elimina Posizone
                     3. Visualizza Posizioni
                     4. Monitora Posizone
                     5. Modifica Posizione
                     6. Indietro
                     7. Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    if(ambiente.getnPosizioniMax() > ambiente.getPosizioni().size() ){
                        gestionePosizioni.creaPosizione(ambiente.getId());
                    }else{
                        System.out.println("Impossibile aggiungere posizioni: Ambiente pieno!");
                    }
                }
                case "2" -> {
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("\nID Posizione da eliminare: ");
                    int idPosizione = Integer.parseInt(scanner1.nextLine());
                    gestionePosizioni.rimuoviPosizione(idPosizione);
                }
                case "3" -> {
                    gestionePosizioni.visualizzaPosizioni(ambiente.getId());
                }
                case "4" -> {
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("Inserire ID della posizione da monitorare: ");
                    int idPosizione = Integer.parseInt(scanner1.nextLine());
                    gestionePosizioni.monitoraPosizone(idPosizione);
                }
                case "5" ->{
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("ID Posizione da modificare: ");
                    int idPosizione = Integer.parseInt(scanner1.nextLine());

                    Scanner scanner2 = new Scanner(System.in);
                    StringBuilder queryBuilder = new StringBuilder("UPDATE \"Posizione\" SET ");

                    System.out.println("Quale attributo vuoi modificare? (assegnata, ambiente, irriatore, igrometroTerreno)");
                    String attributo = scanner2.nextLine();

                    System.out.println("Inserisci il nuovo valore:");
                    String valore = scanner2.nextLine();

                    // Validate and format the value based on attribute type
                    if ("assegnata".equalsIgnoreCase(attributo)) {
                        queryBuilder.append(attributo).append(" = ? ");
                    } else if ("ambiente".equalsIgnoreCase(attributo) || "irriatore".equalsIgnoreCase(attributo) || "igrometroTerreno".equalsIgnoreCase(attributo)) {
                        queryBuilder.append(attributo).append(" = ? ");
                    } else {
                        System.out.println("Attributo non valido.");
                        return;
                    }

                    queryBuilder.append("WHERE id = ?");

                    String query = queryBuilder.toString();
                    gestionePosizioni.modificaPosizione(idPosizione, query, valore, attributo);
                }
                case "6" -> {return;}
                case "7" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);
    }
    public static void handlePosizionamenti() {
        Scanner scanner = new Scanner(System.in);
        GestionePosizionamenti gestionePosizionamenti = new GestionePosizionamenti();
        String input;

        do {

            System.out.println(
                    """
                             \s
                              GESTIONE POSIZIONAMENTI    
                              1. Assegna un posizionamento
                              2. Elimina un posizionamento
                              3. Visualizza posizionamenti
                              4. Indietro
                              5. Esci
                            \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    Scanner scanner1 = new Scanner(System.in);

                }
                case "2" ->{

                }
                case "3" -> handlePosizionamenti();
                case "4" -> {
                    return;
                }
                case "5" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);
    }

    public static void handlePiante() throws Exception {
        Scanner scanner = new Scanner(System.in);
        String input;
        GestioneAttuatori gestioneAttuatori = new GestioneAttuatori();
        GestionePiante gestionePiante = new GestionePiante();

        do {

            System.out.println(
                    """
                    \s
                     GESTIONE PIANTE   
                     1. Controlla stato delle piante 
                     2. Aggiungi tipo di pianta
                     3. Rimuovi tipo di pianta
                     4. Indietro
                     5. Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    //FIXME
                    //Ricerca Operatore libero
                    Scanner scanner2 = new Scanner(System.in);
                    Map<String, Object> criteri2 = new HashMap<>();
                    criteri2.put("occupato", false);
                    gestioneAttuatori.visualizza("Operatore", criteri2);
                    System.out.println("Indicare un operatore libero: (ID) ");
                    int idOperatore = Integer.parseInt(scanner2.nextLine());
                    Operatore operatore = new Operatore(idOperatore);

                    //Ricerca della pianta da controllare
                    Map<String, Object> criteriP = new HashMap<>();
                    gestionePiante.visualizza(criteriP);
                    Scanner scanner3 = new Scanner(System.in);
                    System.out.println("Inserire l'ID della pianta: ");
                    int idPianta = Integer.parseInt(scanner3.nextLine());
                    Pianta pianta =  gestionePiante.restituisciPianta(idPianta);

                    //L'operatore esegue 2 ( =controllo stato pianta)
                    String descrizione = "";
                    if(pianta.controllaStato()){
                        descrizione = operatore.esegui(2);
                    }else{
                        descrizione = operatore.esegui(3);
                    }
                    System.out.println(descrizione);

                    //Viene registrata l'operazione su DB
                    OperazioneDAO oDAO = new OperazioneDAO();
                    String data = " "; //FIXME aggiungere logica per le date
                    oDAO.registraAzione(operatore, descrizione, data);
                    System.out.println(operatore.esegui(-1)); // L'operatore torna libero



                }
                case "2" -> {}
                case "3" -> handlePosizionamenti();
                case "4" -> {return;}
                case "5" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);
    }
}