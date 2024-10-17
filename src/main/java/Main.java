package main.java;

import main.java.BusinessLogic.*;
import main.java.DomainModel.Admin;
import main.java.DomainModel.Cliente;
import main.java.DomainModel.Impianto.*;
import main.java.DomainModel.Ordine;
import main.java.DomainModel.Pianta.Pianta;
import main.java.ORM.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Thread.sleep;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){
        try {
            handleAction();
        } catch (Exception e) {
            System.err.println("Errore: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static String getMainMenu() {
        return """
                    \s
                              GREENHOUSE
                    \s
                    [1]  Cliente
                    [2]  Admin
                    [3]  Esci
                   \s""";
    }

    public static void handleAction() throws Exception {
        String input;
        do {
            System.out.println(getMainMenu());
            input = scanner.nextLine();

            switch (input) {
                case "1" -> handleClient();
                case "2" -> handleAdmin();
                case "3" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }
        } while (true);

    }

    private static String getClientMenu() {
        return """
                \s
                                 LOG IN / SIGN UP CLIENTE
                      \s
                      [1]  Registrati
                      [2]  Accedi
                      [3]  Indietro
                      [4]  Esci
                """;
    }

    public static void handleClient() throws SQLException, ClassNotFoundException {
        GestioneCliente gestioneCliente = new GestioneCliente();
        String input;

        do {
            System.out.println(getClientMenu());
            input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    System.out.print("\nNome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Cognome: ");
                    String cognome = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    Cliente cliente = gestioneCliente.registraCliente(nome, cognome, email, password);

                    if (cliente != null) {
                        handleClientAction(cliente);
                    } else {
                        System.out.println("Registrazione fallita.");
                    }
                }
                case "2" -> {
                    System.out.print("\nEmail: ");
                    String email = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    Cliente cliente = gestioneCliente.accedi(email, password);

                    if (cliente != null) {
                        handleClientAction(cliente);
                    } else {
                        System.out.println("Login non riuscito.");
                    }
                }
                case "3" -> {return;}
                case "4" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);

    }
    public static ArrayList<Pianta> handleSceltaPiante(){
        String input, tipoPianta = "";

        System.out.println(
                """
                \s
                 Scegli il tipo di pianta:
                    [1]  Basilico
                    [2]  Geranio
                    [3]  Rosa
                    [4]  Girasole
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

        System.out.println("\nQuantità: ");
        int nPiante = Integer.parseInt(scanner.nextLine());

        ArrayList<Pianta> piante = new ArrayList<>();
        for(int i = 0; i < nPiante; i++){
            piante.add(new Pianta(tipoPianta));
        }

        System.out.print("Vuoi aggiungere altre piante? (s/n) ");
        String input2 = scanner.nextLine();
        switch (input2) {
            case "s" -> {piante.addAll(handleSceltaPiante());}
            case "n" -> {break;}
            default -> System.out.println("Input invalido, si prega di riprovare.");
        }
        return piante;
    }

    public static void handleClientAction(Cliente cliente) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        GestioneOrdini gestioneOrdini = new GestioneOrdini();
        GestionePosizioni gestionePosizioni = new GestionePosizioni();
        GestioneAttuatori gestioneAttuatori = new GestioneAttuatori();
        String input;

        do {

            System.out.println(
                    """
                    \s
                                MAIN
                                \s
                    [1]  ORDINI
                    [2]  PROFILO
                    [3]  Indietro
                    [4]  Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {handleClientOrders(cliente);}
                case "2" -> {handleClienteProfile(cliente);}
                case "3" -> {return;}
                case "4" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);

    }

    public static void handleClientOrders(Cliente cliente) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        GestioneOrdini gestioneOrdini = new GestioneOrdini();
        GestionePosizioni gestionePosizioni = new GestionePosizioni();
        GestioneAttuatori gestioneAttuatori = new GestioneAttuatori();
        GestionePiante gestionePiante = new GestionePiante();
        String input;

        do {

            System.out.println(
                    """
                    \s
                                ORDINI
                                \s
                    [1]  Richiedi nuovo ordine
                    [2]  Controlla i miei ordini
                    [3]  Paga e ritira ordine
                    [4]  Indietro
                    [5]  Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    if(gestioneOrdini.creazioneOrdine(new Ordine(cliente.getId(), handleSceltaPiante()))){
                        System.out.println("Ordine accettato!");
                    }else {
                        System.out.println("Ordine non accettato!");
                    };
                }
                case "2" -> {
                    Map<String, Object> c = new HashMap<>();
                    c.put("cliente", cliente.getId());
                    gestioneOrdini.visualizzaOrdini(c);}
                case "3" -> {
                    //FIXME aggiorna la funzione in base ai cambiamenti fatti...
                    Map<String, Object> cc = new HashMap<>();
                    cc.put("stato", "da ritirare");
                    if(gestioneOrdini.visualizzaOrdini(cc)){
                        System.out.print("\nID Ordine da ritirare: ");
                        int idOrdine = -1; // Valore predefinito per l'ID ordine
                        boolean inputValido = false; // Flag per verificare se l'input è valido
                        while (!inputValido) { // Continua a richiedere l'input finché non è valido
                            try {
                                idOrdine = Integer.parseInt(scanner.nextLine()); // Prova a convertire l'input in un intero
                                inputValido = true; // L'input è valido, esci dal ciclo
                            } catch (NumberFormatException e) {
                                // Se l'input non è un numero, mostra un messaggio di errore
                                System.out.println("Input errato. Per favore inserisci un numero intero.");
                                System.out.print("ID Ordine da posizionare: "); // Richiedi di nuovo l'input
                            }
                        }
                        Ordine o = gestioneOrdini.getbyId(idOrdine);
                        gestioneOrdini.ritira(o);
                    }else{return;}
                    //gestioneOrdini.pagaERitiraOrdine(cliente, gestioneOrdini.getOrdinePronto(cliente),gestioneAttuatori.richiediAttuatoreLibero("Operatore"));
                }
                case "4" -> {return;}
                case "5" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);

    }

    public static void handleClienteProfile(Cliente cliente){

        Scanner scanner = new Scanner(System.in);
        GestioneOrdini gestioneOrdini = new GestioneOrdini();
        GestionePosizioni gestionePosizioni = new GestionePosizioni();
        GestioneAttuatori gestioneAttuatori = new GestioneAttuatori();
        String input;

        do {

            System.out.println(
                    """
                    \s
                                PROFILO
                                \s
                    [1]  Modifica Profilo (TBD)
                    [2]  Visualizza Profilo (TBD)
                    [3]  Indietro
                    [5]  Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {}
                case "2" -> {}
                case "3" -> {return;}
                case "4" -> System.exit(0);
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
                                        LOG IN PERSONALE LAVORATIVO
                                \s
                              [1]  Accedi
                              [2]  Indietro
                              [3]  Esci
                            \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    Scanner scanner1 = new Scanner(System.in);

                    System.out.print("\nEmail: ");
                    String email = scanner1.nextLine();
                    System.out.print("Password: ");
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
                                        MAIN
                                        \s
                    [1]  ORIDINI & POSIZIONAMENTI
                    [2]  IMPIANTO
                    [3]  PIANTE
                    [4]  Indietro
                    [5]  Esci
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

        // Passa i DAO alle classi di gestione
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
                                    ORDINI & POSIZIONAMENTI
                                    \s
                     [1]  VISUALIZZA
                     [2]  Posiziona ordine
                     [3]  Sistema ordini pronti
                     [4]  Modifica ordine
                     [5]  Indietro
                     [6]  Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {visualizzaOrdini_Posizionamenti();}
                case "2" -> {
                    //Ricerca Ordine da posizionare
                    //Ordine ordine = gestioneOrdini.getOrdineDaPosizionare();
                    //Scelta ordine da preparare
                    Map<String, Object> cc = new HashMap<>();
                    cc.put("stato", "da posizionare");
                    if(gestioneOrdini.visualizzaOrdini(cc)){
                        System.out.print("\nID Ordine da posizionare: ");
                        int idOrdine = -1; // Valore predefinito per l'ID ordine
                        boolean inputValido = false; // Flag per verificare se l'input è valido
                        while (!inputValido) { // Continua a richiedere l'input finché non è valido
                            try {
                                idOrdine = Integer.parseInt(scanner.nextLine()); // Prova a convertire l'input in un intero
                                inputValido = true; // L'input è valido, esci dal ciclo
                            } catch (NumberFormatException e) {
                                // Se l'input non è un numero, mostra un messaggio di errore
                                System.out.println("Input errato. Per favore inserisci un numero intero.");
                                System.out.print("ID Ordine da posizionare: "); // Richiedi di nuovo l'input
                            }
                        }
                        Ordine o = gestioneOrdini.getbyId(idOrdine);

                        //Ricerca Operatore libero
                        Operatore operatore = gestioneAttuatori.richiediAttuatoreLibero("Operatore");

                        if(operatore != null && o != null) {
                            List<Posizione> posizioni = gestionePosizioni.getPosizioniLibere(o.getnPiante());
                            if (posizioni != null) {
                                ArrayList<Posizionamento> posizionamenti = new ArrayList<>();

                                operatore.esegui(1);

                                gestionePosizioni.occupa(posizioni);
                                ArrayList<Pianta> piante = gestionePiante.aggiungi(o.getPiante(), o.getId());
                                gestioneOrdini.posiziona(o);
                                for (int i = 0; i < posizioni.size(); i++) {
                                    posizionamenti.add(new Posizionamento(posizioni.get(i), piante.get(i), o));
                                }
                                gestionePosizionamenti.creaPosizionamento(posizionamenti);

                                operatore.esegui(-1);
                            }
                        }
                    }else{return;}
                }
                case "3" -> {
                    //FIXMe le piante non vengono eliminate correttamente
                    //Scelta ordine da preparare
                    Map<String, Object> c1 = new HashMap<>();
                    c1.put("stato", "da preparare");
                    gestioneOrdini.visualizzaOrdini(c1);
                    System.out.print("\nID Ordine da preparare: ");
                    int idOrdine = Integer.parseInt(scanner.nextLine());

                    //Ricerca Operatore libero
                    Operatore operatore = gestioneAttuatori.richiediAttuatoreLibero("Operatore");

                    //Eliminazione Posizionamenti ( l'operatore prepara ordine... )
                    System.out.println(operatore.esegui(3));
                    ArrayList<Posizionamento> posEliminate = gestionePosizionamenti.eliminaPosizionamentiByOrdine(idOrdine);
                    if(posEliminate != null) {
                        System.out.println("Posizionamenti eliminati");
                        //Aggiornare Posizioni e Ordine e Piante
                        for(Posizionamento p : posEliminate) {
                            gestionePosizioni.libera(p.getPosizione());
                            //gestionePiante.elimina(p.getPianta());
                        }
                        gestioneOrdini.prepara(posEliminate.get(0).getOrdine());
                    }else{
                        System.out.println("Posizionamenti non trovati");
                    }



                    //L'operatore registra l'operazione
                    System.out.println(operatore.esegui(-1));

                    System.out.println("L'ordine è pronto per essere ritirato dal cliente. ");
                }
                case "4" ->{
                    gestioneOrdini.visualizzaOrdini(new HashMap<>());
                    System.out.print("\nID Ordine da modificare: ");
                    int idOrdine = Integer.parseInt(scanner.nextLine());
                    System.out.println(
                            """
                            \s
                             Scegli quale parametro modificare:
                             [1]  Cliente
                             [2]  Data Consegna
                             [3]  Piante
                             [4]  Totale
                             [5]  Stato
                             [6]  Indietro
                             [7]  Esci
                           \s"""
                    );
                    String in = scanner.nextLine();
                    switch (in){
                        case "1" -> {}
                        case "2" -> {}
                        case "3" -> {}
                        case "4" -> {}
                        case "5" -> {
                            System.out.println(
                                    """
                                    \s
                                     Quale stato impostare:
                                     [1]  da posizionare
                                     [2]  posizionato
                                     [3]  da preparare
                                     [4]  da ritirare
                                     [5]  ritirato
                                     [6]  Indietro
                                     [7]  Esci
                                   \s"""
                            );
                            String in2 = scanner.nextLine();
                            Map<String, Object> mm = new HashMap<>();
                            switch (in2) {
                                case "1" -> {
                                    mm.put("stato", "da posizionare");
                                    gestioneOrdini.aggiorna(idOrdine, mm);
                                }
                                case "2" -> {
                                    mm.put("stato", "posizionato");
                                    gestioneOrdini.aggiorna(idOrdine, mm);
                                }
                                case "3" -> {
                                    mm.put("stato", "da preparare");
                                    gestioneOrdini.aggiorna(idOrdine, mm);
                                }
                                case "4" -> {
                                    mm.put("stato", "da ritirare");
                                    gestioneOrdini.aggiorna(idOrdine, mm);
                                }
                                case "5" -> {
                                    mm.put("stato", "ritirato");
                                    gestioneOrdini.aggiorna(idOrdine, mm);
                                }
                                case "6" -> {
                                    return;
                                }
                                case "7" -> {
                                    System.exit(0);
                                }
                            }
                        }
                        case "6" -> {return;}
                        case "7" -> {System.exit(0);}
                    }
                }
                case "5" -> {return;}
                case "6" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }
        } while (true);
    }

    public static void visualizzaOrdini_Posizionamenti(){
        GestioneOrdini gestioneOrdini = new GestioneOrdini();
        GestionePosizionamenti gestionePosizionamenti = new GestionePosizionamenti();
        String input;
        do {

            System.out.println(
                    """
                    \s
                                    VISUALIZZA
                                    \s
                     [1]  Ordini
                     [2]  Posizionamenti
                     [3]  Indietro
                     [4]  Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {gestioneOrdini.visualizzaOrdini(new HashMap<>());}
                case "2" -> {gestionePosizionamenti.visualizza();}
                case "3" -> {return;}
                case "4" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }
        } while (true);
    }



    private static void handleSpazi() {
        String input;
        GestioneSpazi gestioneSpazi = new GestioneSpazi();
        do {

            System.out.println(
                    """
                    \s
                                    SPAZI
                                    \s
                    [1]  Visualizza
                    [2]  Gestisci Settori di uno Spazio
                    [3]  Indietro
                    [4]  Esci
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
        String input;
        GestioneSettori gestioneSettori = new GestioneSettori();
        do {

            System.out.println(
                    """
                    \s
                                        SETTORI
                                        \s
                    [1]  Visualizza
                    [2]  Monitora
                    [3]  Gestisci Posizioni di un Settore (FIXME)
                    [4]  Indietro
                    [5]  Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {gestioneSettori.visualizzaSettori(spazio.getId());}
                case "2" -> {
                    Settore settore = gestioneSettori.richiediSettore(spazio);
                    if(settore != null){gestioneSettori.avviaMonitoraggio(settore);}
                    else {System.out.println("Settore non trovato");}
                }
                case "3" ->{
                    Settore settore = gestioneSettori.richiediSettore(spazio);
                    if(settore != null) {handlePozioni(settore);}
                }
                case "4" -> {return;}
                case "5" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);
    }

    private static void handlePozioni(Settore settore) {
        Scanner scanner = new Scanner(System.in);
        String input;
        GestionePosizioni gestionePosizioni = new GestionePosizioni();
        settore.setPosizioni(gestionePosizioni.getPosizioniBySettore(settore.getId()));
        do {

            System.out.println(
                    """
                    \s
                                    POSIZIONI
                                    \s
                    [1]  Visualizza
                    [2]  Monitora Posizone (TBD)
                    [3]  Modifica Posizione (TBD)
                    [4]  Indietro
                    [5]  Esci
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
                                PIANTE
                                \s
                     [1]  Visualizza (TBF)
                     [2]  Check-up
                     [3]  Aggiungi tipo di pianta (TBD)
                     [4]  Rimuovi tipo di pianta (TBD)
                     [5]  Indietro
                     [6]  Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {}
                case "2" -> {
                    Operatore operatore = gestioneAttuatori.richiediAttuatoreLibero("Operatore");
                    if(operatore != null){
                        System.out.println(operatore.esegui(2));
                        ArrayList<Pianta> pianteDaCurare = gestionePiante.controllaStatoPiante();
                        if(pianteDaCurare != null && !pianteDaCurare.isEmpty()){
                            for(Pianta p : pianteDaCurare) {
                                System.out.println(operatore.esegui(3));
                                sleep(2000);
                                System.out.println(p.cura(operatore.getId()));
                                gestionePiante.aggiornaDescrizione(p.getId(), p.getDescrizione().toString());
                                gestioneAttuatori.registraOperazione(operatore, operatore.getLavoro(), LocalDateTime.now().toString());
                            }
                        }
                        System.out.println(operatore.esegui(-1));
                    }
                }
                case "3" -> {}
                case "4" -> {}
                case "5" -> {return;}
                case "6" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);
    }
}