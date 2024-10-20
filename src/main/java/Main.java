package main.java;

import main.java.BusinessLogic.*;
import main.java.DomainModel.Admin;
import main.java.DomainModel.Cliente;
import main.java.DomainModel.Impianto.*;
import main.java.DomainModel.Ordine;
import main.java.DomainModel.Pianta.Pianta;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Integer.parseInt;
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

    private static boolean askForSorN(String text) {
        String input;
        while (true) {
            System.out.print(text + " (s/n): ");
            input = scanner.nextLine();
            switch (input) {
                case "s" -> {return true;}
                case "n" -> {return false;}
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }
        }
    }

    private static int askForChooseMenuOption(String title, String[] options) {
        String input;
        int index;

        while (true) {
            System.out.println(title);
            System.out.println();
            for (int i = 0; i < options.length; i++) {
                System.out.printf("  [%d]  %s\n", i+1, options[i]);
            }
            System.out.println();
            System.out.println("Scegli un'opzione: ");
            input = scanner.nextLine();
            try {
                index = parseInt(input);
                if (1 <= index && index <= options.length+1)
                    return index;
            } catch (NumberFormatException exc) {
                System.out.println("Input invalido, si prega di riprovare.");
            }
        }
    }

    private static int askForInteger(String text){
        String input;
        while (true){
            System.out.println(text);
            input = scanner.nextLine();
            try {
                return parseInt(input);
            } catch (NumberFormatException exc) {
                System.out.println("Input invalido. Per favore inserisci un numero intero.");
            }
        }
    }

    public static void handleAction() throws Exception {
        int index;
        do {

            index = askForChooseMenuOption("    GREENHOUSE", new String[]{"Cliente", "Admin", "Esci"});

            switch (index) {
                case 1 -> handleClient();
                case 2 -> handleAdmin();
                case 3 -> System.exit(0);
            }
        } while (true);

    }

    public static void handleClient() throws SQLException, ClassNotFoundException {
        GestioneCliente gestioneCliente = new GestioneCliente();
        int index;

        do {

            index = askForChooseMenuOption("    LOG IN / SIGN UP CLIENTE:", new String[]{"Registrati", "Accedi", "Indietro", "Esci"});

            switch (index) {
                case 1 -> {
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
                case 2 -> {
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
                case 3 -> {return;}
                case 4 -> System.exit(0);
            }

        } while (true);

    }
    public static ArrayList<Pianta> handleSceltaPiante(){
        String tipoPianta = "";

        int index = askForChooseMenuOption("Scegli il tipo di pianta:", new String[]{"Basilico", "Geranio", "Rosa", "Girasole"});

        switch (index) {
            case 1 -> {tipoPianta = "Basilico";}
            case 2 -> {tipoPianta = "Geranio";}
            case 3 -> {tipoPianta = "Rosa";}
            case 4 -> {tipoPianta = "Girasole";}
        }

        int nPiante = askForInteger("Quantità: ");

        ArrayList<Pianta> piante = new ArrayList<>();
        for(int i = 0; i < nPiante; i++){
            piante.add(new Pianta(tipoPianta));
        }

        if (askForSorN("Vuoi aggiungere altre piante?"))
            piante.addAll(handleSceltaPiante());

        return piante;
    }

    public static void handleClientAction(Cliente cliente) throws SQLException {
        int index;

        do {
            index = askForChooseMenuOption("    MAIN", new String[]{"ORDINI", "PROFILO", "Indietro", "Esci"});

            switch (index) {
                case 1 -> {handleClientOrders(cliente);}
                case 2 -> {handleClienteProfile(cliente);}
                case 3 -> {return;}
                case 4 -> System.exit(0);
            }

        } while (true);

    }

    public static void handleClientOrders(Cliente cliente) throws SQLException {
        GestioneOrdini gestioneOrdini = new GestioneOrdini();
        int index;

        do {
            index = askForChooseMenuOption("ORDINI", new String[]{"Richiedi nuovo ordine", "Controlla i miei ordini", "Paga e ritira ordine", "Indietro", "Esci"});

            switch (index) {
                case 1 -> {
                    if(gestioneOrdini.creazioneOrdine(new Ordine(cliente.getId(), handleSceltaPiante()))){
                        System.out.println("Ordine accettato!");
                    }else {
                        System.out.println("Ordine non accettato!");
                    };
                }

                case 2 -> {
                    Map<String, Object> c = new HashMap<>();
                    c.put("cliente", cliente.getId());
                    gestioneOrdini.visualizzaOrdini(c);}

                case 3 -> {
                    //FIXME aggiorna la funzione in base ai cambiamenti fatti...
                    Map<String, Object> cc = new HashMap<>();
                    cc.put("stato", "da ritirare");

                    if(gestioneOrdini.visualizzaOrdini(cc)){
                        int idOrdine = askForInteger("ID Ordine da ritirare: ");
                        //FIXME CHECK IF VALID ID
                        Ordine o = gestioneOrdini.getbyId(idOrdine);
                        gestioneOrdini.ritira(o);
                    }else{return;}
                }
                case 4 -> {return;}
                case 5 -> System.exit(0);
            }

        } while (true);

    }

    public static void handleClienteProfile(Cliente cliente){
        int index;
        do {
            index = askForChooseMenuOption("    PROFILO", new String[]{"Modifica Profilo (TBD)", "Visualizza Profilo (TBD)", "Indietro", "Esci"});

            switch (index) {
                case 1 -> {}
                case 2 -> {}
                case 3 -> {return;}
                case 4 -> System.exit(0);
            }

        } while (true);
    }


    public static void handleAdmin() throws Exception {
        GestioneAdmin gestioneAdmin = new GestioneAdmin();
        int index;
        do {
            index = askForChooseMenuOption("    LOG IN PERSONALE LAVORATIVO", new String[]{"Accedi", "Indietro", "Esci"});

            switch (index) {
                case 1 -> {
                    System.out.print("\nEmail: ");
                    String email = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    Admin admin = gestioneAdmin.accedi(email, password);

                    if (admin != null)
                        handleAdminAction();
                }
                case 2 -> {
                    return;
                }
                case 3 -> System.exit(0);
            }

        } while (true);

    }


    public static void handleAdminAction() throws Exception {
        int index;
        do {
            index = askForChooseMenuOption("    MAIN", new String[]{"ORIDINI & POSIZIONAMENTI", "IMPIANTO", "PIANTE", "Indietro", "Esci"});

            switch (index) {
                case 1 -> handleOrdini();
                case 2 -> handleSpazi();
                case 3 -> handlePiante();
                case 4 -> {return;}
                case 5 -> System.exit(0);
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

        int index;
        do {

            index = askForChooseMenuOption("    ORDINI & POSIZIONAMENTI", new String[]{"VISUALIZZA", "Posiziona ordine", "Sistema ordini pronti", "Modifica ordine", "Indietro", "Esci"});

            switch (index) {
                case 1 -> {visualizzaOrdini_Posizionamenti();}
                case 2 -> {
                    //Ricerca Ordine da posizionare
                    //Ordine ordine = gestioneOrdini.getOrdineDaPosizionare();
                    //Scelta ordine da preparare
                    Map<String, Object> cc = new HashMap<>();
                    cc.put("stato", "da posizionare");
                    if(gestioneOrdini.visualizzaOrdini(cc)){
                        int idOrdine = askForInteger("ID Ordine da posizionare: ");
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
                case 3 -> {
                    //FIXMe le piante non vengono eliminate correttamente
                    //Scelta ordine da preparare
                    Map<String, Object> c1 = new HashMap<>();
                    c1.put("stato", "da preparare");
                    gestioneOrdini.visualizzaOrdini(c1);

                    int idOrdine = askForInteger("ID Ordine da preparare: ");

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
                case 4 ->{
                    gestioneOrdini.visualizzaOrdini(new HashMap<>());

                    int idOrdine = askForInteger("ID Ordine da modificare: ");

                    index = askForChooseMenuOption("Scegli quale parametro modificare", new String[]{"Cliente", "Data Consegna", "Piante", "Totale", "Stato", "Indietro", "Esci"});

                    switch (index){
                        case 1 -> {}
                        case 2 -> {}
                        case 3 -> {}
                        case 4 -> {}
                        case 5 -> {
                            index = askForChooseMenuOption("Scegli quale stato impostare", new String[]{"da posizionare", "posizionato", "da preparare", "da ritirare", "ritirato", "Indietro", "Esci"});

                            Map<String, Object> mm = new HashMap<>();
                            switch (index) {
                                case 1 -> {
                                    mm.put("stato", "da posizionare");
                                    gestioneOrdini.aggiorna(idOrdine, mm);
                                }
                                case 2 -> {
                                    mm.put("stato", "posizionato");
                                    gestioneOrdini.aggiorna(idOrdine, mm);
                                }
                                case 3 -> {
                                    mm.put("stato", "da preparare");
                                    gestioneOrdini.aggiorna(idOrdine, mm);
                                }
                                case 4 -> {
                                    mm.put("stato", "da ritirare");
                                    gestioneOrdini.aggiorna(idOrdine, mm);
                                }
                                case 5 -> {
                                    mm.put("stato", "ritirato");
                                    gestioneOrdini.aggiorna(idOrdine, mm);
                                }
                                case 6 -> {
                                    return;
                                }
                                case 7 -> {
                                    System.exit(0);
                                }
                            }
                        }
                        case 6 -> {return;}
                        case 7 -> {System.exit(0);}
                    }
                }
                case 5 -> {return;}
                case 6 -> System.exit(0);
            }
        } while (true);
    }

    public static void visualizzaOrdini_Posizionamenti(){
        GestioneOrdini gestioneOrdini = new GestioneOrdini();
        GestionePosizionamenti gestionePosizionamenti = new GestionePosizionamenti();
        int index;
        do {
            index = askForChooseMenuOption("VISUALIZZA", new String[]{"Ordini", "Posizionamenti", "Indietro", "Esci"});

            switch (index) {
                case 1 -> {gestioneOrdini.visualizzaOrdini(new HashMap<>());}
                case 2 -> {gestionePosizionamenti.visualizza();}
                case 3 -> {return;}
                case 4 -> System.exit(0);
            }
        } while (true);
    }



    private static void handleSpazi() {
        int index;
        GestioneSpazi gestioneSpazi = new GestioneSpazi();
        do {
            index = askForChooseMenuOption("SPAZI", new String[]{"Visualizza", "Gestisci Settori di uno Spazio", "Indietro", "Esci"});

            switch (index) {
                case 1 -> {gestioneSpazi.visualizzaSpazi();}
                case 2 -> {
                    gestioneSpazi.visualizzaSpazi();

                    int idSpazio = askForInteger("ID Spazio del quale gestire i settori: ");

                    Spazio spazio = gestioneSpazi.getSpazio(idSpazio);
                    if(spazio != null) {
                        handleSettori(spazio);
                    }
                }
                case 3 -> {return;}
                case 4 -> System.exit(0);
            }

        } while (true);
    }
    private static void handleSettori(Spazio spazio) {
        GestioneSettori gestioneSettori = new GestioneSettori();
        int index;
        do {

            index = askForChooseMenuOption("SETTORI", new String[]{"Visualizza", "Monitora", "Gestisci Posizioni di un Settore (FIXME)", "Indietro", "Esci"});

            switch (index) {
                case 1 -> {gestioneSettori.visualizzaSettori(spazio.getId());}
                case 2 -> {
                    Settore settore = gestioneSettori.richiediSettore(spazio);
                    if(settore != null){gestioneSettori.avviaMonitoraggio(settore);}
                    else {System.out.println("Settore non trovato");}
                }
                case 3 ->{
                    Settore settore = gestioneSettori.richiediSettore(spazio);
                    if(settore != null) {handlePozioni(settore);}
                }
                case 4 -> {return;}
                case 5 -> System.exit(0);
            }

        } while (true);
    }

    private static void handlePozioni(Settore settore) {
        int index;
        GestionePosizioni gestionePosizioni = new GestionePosizioni();
        settore.setPosizioni(gestionePosizioni.getPosizioniBySettore(settore.getId()));
        do {
            index = askForChooseMenuOption("POSIZIONI", new String[]{"Visualizza", "Monitora Posizone (TBD)", "Modifica Posizione (TBD)", "Indietro", "Esci"});

            switch (index) {
                case 1 -> {
                    gestionePosizioni.visualizzaPosizioni(settore.getId());
                }
                case 2 -> {
                    int idPosizione = askForInteger("Inserire ID della posizione da monitorare: ");
                    gestionePosizioni.monitoraPosizone(idPosizione);
                }
                case 3 ->{
                    //FIXME
                    int idPosizione = askForInteger("ID Posizione da modificare: ");

                    StringBuilder queryBuilder = new StringBuilder("UPDATE \"Posizione\" SET ");

                    int index_attributo = askForChooseMenuOption("Quale attributo vuoi modificare?", new String[]{"assegnata", "settore", "irrigatore", "igrometroTerreno"});

                    System.out.println("Inserisci il nuovo valore:");
                    String valore = scanner.nextLine();

                    switch (index_attributo){
                        case 1 -> {queryBuilder.append("assegnata").append(" = ? ");}
                        case 2 -> {queryBuilder.append("settore").append(" = ? ");}
                        case 3 -> {queryBuilder.append("irrigatore").append(" = ? ");}
                        case 4 -> {queryBuilder.append("igrometroTerreno").append(" = ? ");}
                    }

                    queryBuilder.append("WHERE id = ?");

                    String query = queryBuilder.toString();
                    gestionePosizioni.modificaPosizione(idPosizione, query, valore, index_attributo);
                }
                case 4 -> {return;}
                case 5 -> System.exit(0);
            }

        } while (true);
    }


    public static void handlePiante() throws Exception {
        int index;
        GestioneAttuatori gestioneAttuatori = new GestioneAttuatori();
        GestionePiante gestionePiante = new GestionePiante();
        do {
            index = askForChooseMenuOption("PIANTE", new String[]{"Visualizza (TBF)", "Check-up", "Aggiungi tipo di pianta (TBD)", "Rimuovi tipo di pianta (TBD)", "Indietro", "Esci"});

            switch (index) {
                case 1 -> {}
                case 2 -> {
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
                case 3 -> {}
                case 4 -> {}
                case 5 -> {return;}
                case 6 -> System.exit(0);
            }

        } while (true);
    }
}