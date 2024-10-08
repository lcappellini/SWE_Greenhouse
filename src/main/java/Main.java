package main.java;

import main.java.BusinessLogic.*;
import main.java.DomainModel.Admin;
import main.java.DomainModel.Cliente;
import main.java.DomainModel.Impianto.*;
import main.java.DomainModel.Ordine;
import main.java.DomainModel.Pianta.Pianta;
import main.java.ORM.OperazioneDAO;
import main.java.ORM.SettoreDAO;

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
                     PAGINA ORDINI CLIENTE
                     1. Richiedi nuovo ordine
                     2. Controlla i miei ordini 
                     3. Paga e ritira ordine
                     4. Indietro
                     5. Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {gestioneOrdini.creazioneOrdine(
                        new Ordine(cliente.getId(), gestioneOrdini.sceltaPiante())
                );}
                case "2" -> {gestioneOrdini.visualizzaOrdiniCliente(cliente);}
                case "3" -> {
                    gestioneOrdini.pagaERitiraOrdine(cliente, gestioneOrdini.getOrdinePronto(cliente),
                             gestioneAttuatori.richiediAttuatoreLibero("Operatore"));
                }
                case "4" -> {return;}
                case "5" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);

    }




    public static void handleAdmin() throws Exception {

        Scanner scanner = new Scanner(System.in);
        GestioneAdmin gestioneAdmin = new GestioneAdmin();
        String input;

        do {

            System.out.println(
                    """
                             \s
                              PAGINA ADMIN
                              1. Accedi
                              2. Indietro
                              3. Esci
                            \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    Scanner scanner1 = new Scanner(System.in);

                    System.out.println("\nEmail: ");
                    String email = scanner1.nextLine();
                    System.out.println("Password: ");
                    String password = scanner1.nextLine();

                    Admin admin = gestioneAdmin.accedi(email, password);

                    if (admin != null)
                        handleAdminAction();
                }
                case "2" -> {
                    return;
                }
                case "3" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);

    }


    public static void handleAdminAction() throws Exception {

        Scanner scanner = new Scanner(System.in);
        String input;

        do {

            System.out.println(
                    """
                    \s
                     PAGINA ADMIN
                     1. Gestione Ordini-Posizionamenti
                     2. Gestione Spazi-Ambienti-Posizioni
                     3. Gestione Piante
                     4. Indietro
                     5. Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> handleOrdini();
                case "2" -> handleSpazi();
                case "3" -> handlePiante();
                case "4" -> {return;}
                case "5" -> System.exit(0);
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
        GestionePiante gestionePiante = new GestionePiante();
        String input;
        do {

            System.out.println(
                    """
                    \s
                     GESTIONE ORDINI & POSIZIONAMENTI
                     1. Visualizza ordini
                     2. Modifica ordine (TBD)
                     3. Posiziona ordine
                     4. Visualizza Posizionamenti
                     5. Indietro
                     6. Esci
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
                    //Ricerca Ordine da posizionare
                    Ordine ordine = gestioneOrdini.getOrdineDaPosizionare();

                    //Ricerca Operatore libero
                    Operatore operatore = gestioneAttuatori.richiediAttuatoreLibero("Operatore");

                    if(operatore != null && ordine != null) {
                        ArrayList<Posizione> posizioni = gestionePosizioni.occupa(ordine.getnPiante());
                        if(posizioni != null){
                            gestionePosizionamenti.creaPosizionamento(ordine, operatore, posizioni);
                        }
                    }
                }
                case "4" -> {
                    //fixme cosa volevi visualizzare?
                    gestionePosizionamenti.visualizza();
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
                     GESTIONE SPAZI
                     1. Visualizza tutti gli Spazi
                     2. Gestisci Settore
                     3. Indietro
                     4. Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {gestioneSpazi.visualizzaSpazi();}
                case "2" -> {
                    gestioneSpazi.visualizzaSpazi();
                    System.out.println("ID Spazio del quale gestire i settori : ");
                    Scanner scanner1 = new Scanner(System.in);
                    int idSpazio = Integer.parseInt(scanner1.nextLine());
                    Spazio spazio = gestioneSpazi.getSpazio(idSpazio);
                    if(spazio != null) {
                        handleSettori(spazio);
                    }
                }
                case "3" -> {return;}
                case "4" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);
    }
    private static void handleSettori(Spazio spazio) {
        Scanner scanner = new Scanner(System.in);
        String input;
        GestioneSettori gestioneSettori = new GestioneSettori();
        do {

            System.out.println(
                    """
                    \s
                     GESTIONE SETTORE
                     1. Visualizza Settori dello Spazio
                     2. Monitora Settore
                     5. Gestisci Posizioni (FIXME)
                     6. Indietro
                     7. Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    gestioneSettori.visualizzaSettori(spazio.getId());
                }
                case "2" -> {
                    Settore settore = gestioneSettori.getSettore(spazio);
                    if(settore != null){
                        gestioneSettori.avviaMonitoraggio(settore);
                        /*Scanner scanner2 = new Scanner(System.in);
                        System.out.println("Premi Invio per interrompere il ciclo di monitoraggio...");
                        LocalDateTime lt = LocalDateTime.now();
                        long i = 0;

                        Thread monitoringThread = new Thread(() -> gestioneSettori.monitoraSettore(settore));

                        monitoringThread.start();

                        scanner2.nextLine(); // Aspetta che l'utente prema Invio
                        monitoringThread.interrupt(); // Interrompe il thread di monitoraggio

                        try {
                            monitoringThread.join(); // Attende la terminazione del thread
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/
                    }else {
                        System.out.println("Settore non trovato");
                    }
                }
                case "3" ->{
                    //FIXME "Errore durante il completamento dello settore:
                    // ERROR: column "ambiente_id" does not exist
                    //  Posizione: 33"
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("ID Ambiente del quale gestire le posizioni: ");
                    int idAmbiente = Integer.parseInt(scanner1.nextLine());
                    Settore settore = gestioneSettori.getById(idAmbiente);
                    if(settore != null) {
                        handlePozioni(settore);
                    }
                }
                case "4" -> {return;}
                case "5" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);
    }

    // FIXME Biomonitoring da revisionare:
    private static void monitorAmbiente(Settore settore, GestioneSettori gestioneSettori, GestioneSensori gestioneSensori, GestioneAttuatori gestioneAttuatori) {
        LocalDateTime lt = LocalDateTime.now();
        long i = 0;
        Map<String, String> descrizioni = new HashMap<>();

        while (!Thread.currentThread().isInterrupted()) {
            try {
                //vengono generati i valori dei sensori
                settore.misura(lt);
                //vengono salvati gli attuatori che sono stati accesi
                descrizioni = settore.aziona();

                // Registra i dati e monitora lo settore
                for(Sensore s : settore.getSensori()){
                    gestioneSensori.registraMisura(s);
                }
                OperazioneDAO opDAO = new OperazioneDAO();
                for(Attuatore a: settore.getAttuatori()){
                    opDAO.registraAzione(a, descrizioni.get(a.tipoAttuatore()), lt.toString());
                }
                //gestioneAttuatori.registraAzione(settore);
                //gestioneAmbienti.monitoraAmbiente(settore.getId());
                Map<String, Boolean> accesi = settore.getAttuatoriAccesi();

                System.out.println("+----------------------+");
                System.out.println("| Parametro            | Valore                |");
                System.out.println("+----------------------+-----------------------+");
                //System.out.printf("| Temperatura          | %-21f|\n", settore.getSensore("Termometro").getValore());
                //System.out.printf("| Percentuale Acqua    | %-21f|\n", settore.getSensore("IgrometroAria").getValore());
                //System.out.printf("| Percentuale Luce     | %-21f|\n", settore.getSensore("Fotosensore").getValore());
                //System.out.printf("| A/C Acceso| %-21s|\n",accesi.get("Climatizzazione") ? "Sì" : "No");
                //System.out.printf("| Lampada Accesa       | %-21s|\n", accesi.get("Lampada") ? "Sì" : "No");
                System.out.println("+----------------------+-----------------------+");

                Thread.sleep(2000); // Pausa di 2 secondi tra i cicli

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Reimposta lo stato di interruzione
            }
            i += 2;
            lt = lt.plusHours(i);
        }
    }

    private static void handlePozioni(Settore settore) {
        Scanner scanner = new Scanner(System.in);
        String input;
        GestionePosizioni gestionePosizioni = new GestionePosizioni();
        settore.setPosizioni(gestionePosizioni.completaAmbiente(settore.getId()));
        do {

            System.out.println(
                    """
                    \s
                     GESTIONE POSIZIONI
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
                    gestionePosizioni.visualizzaPosizioni(settore.getId());
                }
                case "2" -> {
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("Inserire ID della posizione da monitorare: ");
                    int idPosizione = Integer.parseInt(scanner1.nextLine());
                    gestionePosizioni.monitoraPosizone(idPosizione);
                }
                case "3" ->{
                    //FIXME
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("ID Posizione da modificare: ");
                    int idPosizione = Integer.parseInt(scanner1.nextLine());

                    Scanner scanner2 = new Scanner(System.in);
                    StringBuilder queryBuilder = new StringBuilder("UPDATE \"Posizione\" SET ");

                    System.out.println("Quale attributo vuoi modificare? (assegnata, settore, irriatore, igrometroTerreno)");
                    String attributo = scanner2.nextLine();

                    System.out.println("Inserisci il nuovo valore:");
                    String valore = scanner2.nextLine();

                    // Validate and format the value based on attribute type
                    if ("assegnata".equalsIgnoreCase(attributo)) {
                        queryBuilder.append(attributo).append(" = ? ");
                    } else if ("settore".equalsIgnoreCase(attributo) || "irriatore".equalsIgnoreCase(attributo) || "igrometroTerreno".equalsIgnoreCase(attributo)) {
                        queryBuilder.append(attributo).append(" = ? ");
                    } else {
                        System.out.println("Attributo non valido.");
                        return;
                    }

                    queryBuilder.append("WHERE id = ?");

                    String query = queryBuilder.toString();
                    gestionePosizioni.modificaPosizione(idPosizione, query, valore, attributo);
                }
                case "4" -> {return;}
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
                     2. Aggiungi tipo di pianta (TBD)
                     3. Rimuovi tipo di pianta (TBD)
                     4. Indietro
                     5. Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    //FIXME
                    //Ricerca Operatore libero


                    Operatore operatore = gestioneAttuatori.richiediAttuatoreLibero("Operatore");

                    //Ricerca della pianta da controllare
                    Map<String, Object> criteriP = new HashMap<>();
                    gestionePiante.visualizza(criteriP);
                    Scanner scanner3 = new Scanner(System.in);
                    System.out.println("Inserire l'ID della pianta: ");
                    int idPianta = Integer.parseInt(scanner3.nextLine());
                    Pianta pianta =  gestionePiante.restituisciPianta(idPianta);

                    //L'operatore esegue 2 ( =controllo stato pianta)
                    String esecuzione = "";
                    if(pianta.controllaStato()){
                        esecuzione = operatore.esegui(2);
                    }else{
                        esecuzione = operatore.esegui(3);
                    }
                    System.out.println(esecuzione);
                    //FIXME ora pianta.getDesc.. rende StringBuilder
                    System.out.println("Stato Pianta : "+ pianta.getDescrizione());
                    gestionePiante.aggiornaDescrizione(idPianta,esecuzione);

                    //Viene registrata l'operazione su DB
                    OperazioneDAO oDAO = new OperazioneDAO();
                    String data = " "; //FIXME aggiungere logica per le date
                    oDAO.registraAzione(operatore, esecuzione, data);
                    System.out.println(operatore.esegui(-1)); // L'operatore torna libero



                }
                case "2" -> {}
                case "3" -> {return;}
                case "4" -> {return;}
                case "5" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);
    }
}