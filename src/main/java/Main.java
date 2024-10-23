package main.java;

import main.java.BusinessLogic.*;
import main.java.DomainModel.*;
import main.java.DomainModel.Impianto.*;
import main.java.DomainModel.Pianta.*;
import main.java.ORM.AttuatoreDAO;
import main.java.ORM.SensoreDAO;
import org.postgresql.core.Tuple;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.Integer.parseInt;
import static java.lang.Thread.sleep;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static String RedString(String str){
        return "\u001B[41m" + str + "\u001B[0m";
    }
    public static String GreenString(String str){
        return "\u001B[42m" + str +"\u001B[0m";
    }

    public static void main(String[] args){
        try {
            handleAction();
        } catch (Exception e) {
            System.err.println("Errore: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // UTILS
    private static boolean askForSorN(String text) {
        String input;
        while (true) {
            System.out.print(text + " (s/n): ");
            input = scanner.nextLine();
            switch (input) {
                case "s": {return true;}
                case "n": {return false;}
                default: System.out.println("Input invalido, si prega di riprovare.");
            }
        }
    }

    private static int askForChooseMenuOption(String title, String[] options) {
        String input;
        int index;

        while (true) {
            System.out.println();
            System.out.println(title);
            System.out.println();
            for (int i = 0; i < options.length; i++) {
                System.out.printf("  [%d]  %s\n", i+1, options[i]);
            }
            System.out.println();
            System.out.print(">>> ");
            input = scanner.nextLine();
            try {
                index = parseInt(input);
                if (1 <= index && index <= options.length+1)
                    return index;
            } catch (NumberFormatException ignored) {}
            System.out.println("Input invalido, si prega di riprovare.");
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

    // MAIN
    public static void handleAction() throws Exception {
        while (true) { //Loop iniziale continuo, se effetuato logout da un utente, ritorna qui
            int index = askForChooseMenuOption("   GREENHOUSE", new String[]{"Cliente", "Personale", "Esci"});
            if (index == 1)
                handleClientLogin();
            else if (index == 2)
                handlePersonale();
            else if (index == 3)
                System.exit(0);
        }
    }

    // PERSONALE
    public static void handlePersonale() throws Exception {
        int index = askForChooseMenuOption("    RUOLO", new String[]{"Admin", "Operatore", "Indietro", "Esci"});
        if (index == 1)
            handlePersonaleLogin(0);
        else if (index == 2)
            handlePersonaleLogin(1);
        else if (index == 3)
            return;
        else if (index == 4)
            System.exit(0);
    }

    public static void handlePersonaleLogin(int role) throws Exception {
        String role_string = role == 1 ? "OPERATORE" : "ADMIN";
        int index = askForChooseMenuOption("    " + role_string, new String[]{"Accedi", "Indietro", "Esci"});
        if (index == 1) {
            System.out.print("\nEmail: ");
            //String email = scanner.nextLine(); //TODO REENABLE
            String email; //TODO REMOVE, DEBUG ONLY
            if (role == 1) //TODO REMOVE, DEBUG ONLY
                email = "ferrari@email.it";  //TODO REMOVE, DEBUG ONLY
            else //TODO REMOVE, DEBUG ONLY
                email = "lorenzo";  //TODO REMOVE, DEBUG ONLY
            System.out.print("Password: ");
            //String password = scanner.nextLine(); //TODO REENABLE
            String password = "123"; //TODO REMOVE, DEBUG ONLY

            LoginPersonaleController loginPersonaleController = new LoginPersonaleController();
            if (role == 0) {
                Admin a = loginPersonaleController.loginAdmin(email, password);
                if(a != null)
                    handleAdminAction(a);
            }
            else if (role == 1) {
                Operatore o = loginPersonaleController.loginOperatore(email, password);
                if(o != null)
                    handleOperatoreAction(o);
            }
        }
        else if (index == 2)
            return;
        else if (index == 3)
            System.exit(0);
    }

    // ADMIN
    public static void handleAdminAction(Admin admin) throws Exception {
        AdminController adminController = new AdminController();
        while (true) { //Loop, una volta effettuata l'operazione scelta, ritorna qui. Esce solo con logout
            int index = askForChooseMenuOption("    ADMIN DASHBOARD", new String[]{"Visualizza", "Monitora Settore", "Logout", "Esci"});
            if (index == 1)
                handleViewTables();
            else if (index == 2)
                handleMonitoraSettore();
            else if (index == 3)
                return;
            else if (index == 4)
                System.exit(0);
        }
    }

    private static void handleMonitoraSettore() {
        GestioneSettori gestioneSettori = new GestioneSettori();
        ArrayList<Settore> settori = gestioneSettori.get(null);
        if (settori.isEmpty()) {
            System.out.println("Non ci sono settori!");
            return;
        }
        //printSettori(settori);
        System.out.printf("Sono stati trovati %d settori, con i seguenti ID:\n", settori.size());
        for (Settore settore : settori)
            System.out.printf("%d, ", settore.getId());
        System.out.println();
        int idSettore = askForInteger("ID Settore da monitorare: ");
        Settore settoreScelto = gestioneSettori.getById(idSettore);
        if (settoreScelto == null) {
            System.out.println("Nessun ordine trovato con questo id!");
        } else {

            boolean conPosizioni = askForSorN("Vuoi monitorare anche le Posizioni di questo settore?");

            LocalDateTime lt = LocalDateTime.now();
            AdminController adminController = new AdminController();
            try {
                while (true){

                    try {
                        // Chiama la funzione monitoraSettore con l'ora simulata
                        System.out.println("TIME: " + lt.format(DateTimeFormatter.ofPattern("d MMM yyyy HH:mm:ss")));  // Stampa il tempo formattato

                        Settore settore = adminController.monitoraSettore(idSettore, lt);
                        printSettore(settore);
                        if (conPosizioni) {
                            ArrayList<Map.Entry<Posizione, Pianta>> posizioni_piante = adminController.monitoraPosizioniBySettoreId(idSettore, lt);
                            printPosizioniInSettore(posizioni_piante);
                        }

                        // Aggiunge 1 ora a ogni iterazione
                        lt = lt.plusMinutes(15);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    System.out.println("Premi Invio per interrompere il monitoraggio...");
                    if (System.in.available() > 0) {
                        String input = scanner.nextLine();
                        System.out.println("Invio premuto! Arresto monitoraggio...");
                        System.out.println("Monitoraggio terminato.");
                        break;
                    }
                    Thread.sleep(2500);
                }
            } catch (InterruptedException | IOException ignored) {}
        }

    }

    public static void handleViewTables() throws Exception {
        while (true) { //Loop, una volta effettuata l'operazione scelta, ritorna qui. Esce solo con logout
            int index = askForChooseMenuOption("    VISUALIZZA TABELLE", new String[]{"ORDINI", "PIANTE", "SPAZI", "Indietro", "Esci"});
            if (index == 1) {
                GestioneOrdini gestioneOrdini = new GestioneOrdini();
                ArrayList<Ordine> ordini = gestioneOrdini.get(null);
                if (!ordini.isEmpty())
                    printOrdini(ordini);
            }
            else if (index == 2) {
                GestionePiante gestionePiante = new GestionePiante();
                ArrayList<Pianta> piante = gestionePiante.get(null);
                if (!piante.isEmpty())
                    printPiante(piante);
            }
            else if (index == 3)
                ;//handleSpazi();
            else if (index == 4)
                return;
            else if (index == 5)
                System.exit(0);
        }
    }

    /*public static void handleOrdini() throws Exception {
        // Passa i DAO alle classi di gestione
        GestioneOrdini gestioneOrdini = new GestioneOrdini();
        GestionePosizionamenti gestionePosizionamenti = new GestionePosizionamenti();
        GestionePosizioni gestionePosizioni = new GestionePosizioni();
        GestioneAttuatori gestioneAttuatori = new GestioneAttuatori();
        GestionePiante gestionePiante = new GestionePiante();

        while (true) {
            int index = askForChooseMenuOption("    ORDINI & POSIZIONAMENTI", new String[]{"VISUALIZZA", "Posiziona ordine", "Sistema ordini pronti", "Modifica ordine", "Indietro", "Esci"});

            if (index == 1)
                visualizzaOrdini_Posizionamenti();
            else if (index == 2) {
                //Ricerca Ordine da posizionare
                //Ordine ordine = gestioneOrdini.getOrdineDaPosizionare();
                //Scelta ordine da preparare
                Map<String, Object> cc = new HashMap<>();
                cc.put("stato", "da posizionare");
                if (gestioneOrdini.visualizzaOrdini(cc)) {
                    int idOrdine = askForInteger("ID Ordine da posizionare: ");
                    Ordine o = gestioneOrdini.getbyId(idOrdine);

                    //Ricerca Operatore libero
                    Operatore operatore = gestioneAttuatori.richiediAttuatoreLibero("Operatore");

                    if (operatore != null && o != null) {
                        List<Posizione> posizioni = gestionePosizioni.getPosizioniLibere(o.getnPiante());
                        if (posizioni != null) {
                            ArrayList<Posizionamento> posizionamenti = new ArrayList<>();

                            operatore.esegui(1);

                            gestionePosizioni.occupa(posizioni);
                            ArrayList<Pianta> piante = gestionePiante.aggiungi(o.getPiante(), o.getId());
                            gestioneOrdini.posiziona(o);
                            for (int i = 0; i < posizioni.size(); i++) {
                                posizionamenti.add(new Posizionamento(posizioni.get(i).getId(), piante.get(i).getId(), o.getId()));
                            }
                            gestionePosizionamenti.creaPosizionamento(posizionamenti);

                            operatore.esegui(-1);
                        }
                    }
                }
            } else if (index == 3) {
                //FIXMe le piante non vengono eliminate correttamente
                //Scelta ordine da preparare
                Map<String, Object> c1 = new HashMap<>();
                c1.put("stato", "da preparare");
                gestioneOrdini.visualizzaOrdini(c1);

                int idOrdine = askForInteger("ID Ordine da preparare: ");

                //Ricerca Operatore libero
                Operatore operatore = gestioneAttuatori.richiediAttuatoreLibero("Operatore");

                //Eliminazione Posizionamenti (l'operatore prepara ordine...)
                System.out.println(operatore.esegui(3));
                ArrayList<Posizionamento> posEliminate = gestionePosizionamenti.eliminaPosizionamentiByOrdine(idOrdine);
                if (posEliminate != null) {
                    System.out.println("Posizionamenti eliminati");
                    //Aggiornare Posizioni e Ordine e Piante
                    for (Posizionamento p : posEliminate) {
                        gestionePosizioni.libera(p.getIdPosizione());
                        //gestionePiante.elimina(p.getIdPianta());
                    }
                    gestioneOrdini.prepara(posEliminate.get(0).getIdOrdine());
                } else {
                    System.out.println("Posizionamenti non trovati");
                }

                //L'operatore registra l'operazione
                System.out.println(operatore.esegui(-1));
                System.out.println("L'ordine è pronto per essere ritirato dal cliente. ");

            } else if (index == 4) {
                gestioneOrdini.visualizzaOrdini(new HashMap<>());

                int idOrdine = askForInteger("ID Ordine da modificare: ");

                int index2 = askForChooseMenuOption("Scegli quale parametro modificare", new String[]{"Cliente", "Data Consegna", "Piante", "Totale", "Stato", "Indietro", "Esci"});

                switch (index2) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5: {
                        String[] stati = new String[]{"da posizionare", "posizionato", "da preparare", "da ritirare", "ritirato", "Indietro", "Esci"};
                        int index3 = askForChooseMenuOption("Scegli quale stato impostare", stati);
                        if (index3 == 6)
                            return;
                        else if (index3 == 7)
                            System.exit(0);
                        else {
                            String stato_scelto = stati[index - 1];
                            Map<String, Object> mm = new HashMap<>();
                            mm.put("stato", stato_scelto);
                            gestioneOrdini.aggiorna(idOrdine, mm);
                        }
                    }
                    case 6:
                        return;
                    case 7:
                        System.exit(0);
                }
            } else if (index == 5)
                return;
            else if (index == 6)
                System.exit(0);
        }
    }*/

    // OPERATORE
    public static void handleOperatoreAction(Operatore operatore) throws Exception {
        OperatoreController operatoreController = new OperatoreController(operatore);
        while (true) { //Loop, una volta effettuata l'operazione scelta, ritorna qui. Esce solo con logout
            int index = askForChooseMenuOption("    OPERATORE DASHBOARD", new String[]{"VISUALIZZA", "Pianta Ordine", "Completa Ordine", "Check-Up Piante", "Logout", "Esci"});
            if (index == 1) {
                handleViewTables();
            }
            else if (index == 2) {
                Ordine ordine = handleSceltaOrdineDaPiantare();
                if (ordine != null) {
                    System.out.println(operatore.esegui(2));
                    if (operatoreController.piantaOrdine(ordine, operatore))
                        System.out.println("Ordine piantato con successo!");
                    else
                        System.out.println("C'è stato un problema nel piantare l'ordine.");
                    System.out.println(operatore.esegui(-1));
                }
            }
            else if (index == 3) {
                Ordine ordine = handleSceltaOrdineDaCompletare();
                if (ordine != null) {
                    System.out.println(operatore.esegui(4));
                    operatoreController.completaOrdine(ordine, operatore);
                }
            }
            else if (index == 4) {
                System.out.println(operatore.esegui(2));

                ArrayList<Pianta> pianteDaCurare = operatoreController.checkupPiante(operatore);
                if(pianteDaCurare != null && !pianteDaCurare.isEmpty()) {
                    System.out.printf("%d Piante hanno bisogno di cure.", pianteDaCurare.size());
                    System.out.println("Avvio operazione di CURA");
                    for (Pianta pianta : pianteDaCurare) {
                        System.out.println("\nDettagli pianta:");
                        System.out.println("Id: " + pianta.getId());
                        System.out.println("Tipo: " + pianta.getTipoPianta());
                        System.out.println("Cura pianta in corso");
                        for (int i = 0; i < 3; i++) {
                            System.out.print(".");
                            sleep(600);
                        }
                        operatoreController.curaPianta(pianta, operatore);
                        System.out.println("Pianta curata!");
                    }
                }
            }
            else if (index == 5)
                return;
            else if (index == 6)
                System.exit(0);
        }
    }

    private static Ordine handleSceltaOrdineDaPiantare() {
        GestioneOrdini gestioneOrdini = new GestioneOrdini();
        while (true) {
            //if (!gestioneOrdini.visualizzaOrdini(Map.of("stato", "da piantare"))) {
            ArrayList<Ordine> ordiniDaPiantare = gestioneOrdini.get(Map.of("stato", "da piantare"));
            if (ordiniDaPiantare.isEmpty()) {
                System.out.println("Non ci sono ordini da piantare!");
                return null;
            }
            printOrdini(ordiniDaPiantare);
            int idOrdine = askForInteger("ID Ordine da piantare: ");
            Ordine ord = gestioneOrdini.getById(idOrdine);
            if (ord == null) {
                System.out.println("Nessun ordine trovato con questo id!");
            } else {
                if (ord.getStato().equals("da piantare"))
                    return ord;
                else
                    System.out.println("Quest'ordine non è da piantare!");
            }
        }
    }

    private static Ordine handleSceltaOrdineDaCompletare() {
        GestioneOrdini gestioneOrdini = new GestioneOrdini();
        while (true) {
            //if (!gestioneOrdini.visualizzaOrdini(Map.of("stato", "da completare"))) {
            ArrayList<Ordine> ordiniDaCompletare = gestioneOrdini.get(Map.of("stato", "da completare"));
            if (ordiniDaCompletare.isEmpty()) {
                System.out.println("Non ci sono ordini da completare!");
                return null;
            }
            printOrdini(ordiniDaCompletare);
            int idOrdine = askForInteger("ID Ordine da completare: ");
            Ordine ord = gestioneOrdini.getById(idOrdine);
            if (ord.getStato().equals("da completare"))
                return ord;
        }
    }

    // CLIENTE
    public static void handleClientLogin() throws SQLException {
        int index = askForChooseMenuOption("    LOG IN / SIGN UP CLIENTE:", new String[]{"Registrati", "Accedi", "Indietro", "Esci"});

        if (index == 1) {
            System.out.print("\nNome: ");
            String nome = scanner.nextLine();
            System.out.print("Cognome: ");
            String cognome = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            LoginClienteController loginClienteController = new LoginClienteController();
            if (loginClienteController.registrati(nome, cognome, email, password)) {
                System.out.println("Registrazione avvenuta con successo!");
                handleClientLogin();
            } else
                System.out.println("Registrazione fallita.");
        } else if (index == 2) {
            System.out.print("\nEmail: ");
            //String email = scanner.nextLine();
            String email = "mario@email.it"; //TODO REMOVE, DEBUG ONLY
            System.out.print("Password: ");
            //String password = scanner.nextLine();
            String password = "123"; //TODO REMOVE, DEBUG ONLY

            LoginClienteController loginClienteController = new LoginClienteController();
            Cliente cliente = loginClienteController.accedi(email, password);

            if (cliente != null) {
                handleClientAction(cliente);
            } else {
                System.out.println("Login non riuscito.");
            }
        } else if (index == 3)
            return;
        else if (index == 4)
            System.exit(0);
    }

    public static void handleClientAction(Cliente cliente) throws SQLException {
        while (true) {
            int index = askForChooseMenuOption("    MAIN - CLIENTE ", new String[]{"ORDINI", "PROFILO", "Logout", "Esci"});
            if (index == 1)
                handleClientOrders(cliente);
            else if (index == 2)
                handleClienteProfile(cliente);
            else if (index == 3)
                return;
            else if (index == 4)
                System.exit(0);
        }
    }

    public static ArrayList<Pianta> handleSceltaPiante(){
        String tipoPianta = "";

        String[] tipi_piante = new String[]{"Basilico", "Geranio", "Rosa", "Girasole"};

        int index = askForChooseMenuOption("Scegli il tipo di pianta:", tipi_piante);
        tipoPianta = tipi_piante[index-1];

        int nPiante = askForInteger("Quantità: ");

        ArrayList<Pianta> piante = new ArrayList<>();
        for(int i = 0; i < nPiante; i++){
            piante.add(new Pianta(tipoPianta, "da piantare"));
        }

        if (askForSorN("Vuoi aggiungere altre piante?"))
            piante.addAll(handleSceltaPiante());

        return piante;
    }

    public static void handleClientOrders(Cliente cliente) {
        ClienteController clienteController = new ClienteController(cliente);
        GestioneOrdini gestioneOrdini = new GestioneOrdini();
        int index = askForChooseMenuOption("    ORDINI", new String[]{"Richiedi nuovo ordine", "Controlla i miei ordini", "Paga e ritira ordine", "Indietro", "Esci"});

        if (index == 1){
            if (clienteController.richiediNuovoOrdine(new Ordine(cliente.getId(), handleSceltaPiante())))
                System.out.println("Ordine accettato!");
            else
                System.out.println("Ordine non accettato!");
        }
        else if (index == 2) {
            //gestioneOrdini.visualizzaOrdini(Map.of("cliente", cliente.getId()));
            printOrdini(gestioneOrdini.get(Map.of("cliente", cliente.getId())));
        }
        else if (index == 3) {
            //FIXME aggiorna la funzione in base ai cambiamenti fatti...
            //if (printOrdini(gestioneOrdini.visualizzaOrdini(Map.of("stato", "da ritirare")))) {
            ArrayList<Ordine> ordiniDaRitirare = gestioneOrdini.get(Map.of("stato", "da ritirare"));
            if (!ordiniDaRitirare.isEmpty()) {
                printOrdini(ordiniDaRitirare);
                int idOrdine = askForInteger("ID Ordine da ritirare: ");
                //FIXME CHECK IF VALID ID, Hint: printOrdini potrebbe ritornare un array degli id degli elementi visualizzati
                //  tra i quali far scegliere
                Ordine ordine = gestioneOrdini.getById(idOrdine);
                if(clienteController.pagaEritiraOrdine(ordine)){
                    System.out.println("Ordine pagato! Può ritirare l'ordine.");
                }else {
                    System.out.println("Il pagamento non è andato a buon fine");
                }

            } else
                return;
        }
        else if (index == 4)
            return;
        else if (index == 5)
            System.exit(0);
    }

    public static void handleClienteProfile(Cliente cliente) {
        int index = askForChooseMenuOption("    PROFILO", new String[]{"Modifica Profilo", "Visualizza Profilo", "Indietro", "Esci"});

        if (index == 1) {
            int index2 = askForChooseMenuOption("Scegliere cosa modificare:", new String[]{"Nome", "Cognome", "Email", "Password", "Indietro", "Esci"});

            String currentValue = "********"; //La password non può essere visualizzata
            if (index2 == 1)
                currentValue = cliente.getNome();
            else if (index2 == 2)
                currentValue = cliente.getCognome();
            else if (index2 == 3)
                currentValue = cliente.getEmail();
            System.out.println("Valore attuale: "+currentValue);

            System.out.print("Inserire nuovo valore: ");
            String newValue = scanner.nextLine();

            ClienteController clienteController = new ClienteController(cliente);
            if (index2 < 5) {
                boolean result = clienteController.aggiornaProfilo(new String[]{"nome", "cognome", "email", "password"}[index2-1], newValue);
                if (result) {
                    if (index2 == 1) {
                        cliente.setNome(newValue);
                    }
                    else if (index2 == 2) {
                        cliente.setCognome(newValue);
                    }
                    else if (index2 == 3) {
                        cliente.setEmail(newValue);
                    }
                    System.out.println("Modifica avvenuta con successo.");
                }
            } else if (index2 == 5) {
                return;
            } else if (index2 == 6) {
                System.exit(0);
            }
        }
        else if (index == 2) {
            System.out.println("    DATI PROFILO\n");
            System.out.println("  Nome:     "+cliente.getNome());
            System.out.println("  Cognome:  "+cliente.getCognome());
            System.out.println("  Email:    "+cliente.getEmail());
            System.out.println();
            System.out.println("Premi invio per tornare indietro...");
            scanner.nextLine();
        }
        else if (index == 3) {
            return;
        }
        else if (index == 4) {
            System.exit(0);
        }
    }

    /*public static void visualizzaOrdini_Posizionamenti() {
        GestioneOrdini gestioneOrdini = new GestioneOrdini();
        GestionePosizionamenti gestionePosizionamenti = new GestionePosizionamenti();
        int index;
        index = askForChooseMenuOption("VISUALIZZA", new String[]{"Ordini", "Posizionamenti", "Indietro", "Esci"});

        switch (index) {
            case 1:

            case 2:
                gestionePosizionamenti.visualizza();
            case 3:
                return;
            case 4:
                System.exit(0);
        }
    }

    private static void handleSpazi() {
        int index;
        GestioneSpazi gestioneSpazi = new GestioneSpazi();
        index = askForChooseMenuOption("SPAZI", new String[]{"Visualizza", "Gestisci Settori di uno Spazio", "Indietro", "Esci"});

        switch (index) {
            case 1: {
                gestioneSpazi.visualizzaSpazi();
            }
            case 2: {
                gestioneSpazi.visualizzaSpazi();

                int idSpazio = askForInteger("ID Spazio del quale gestire i settori: ");

                Spazio spazio = gestioneSpazi.getSpazio(idSpazio);
                if (spazio != null) {
                    handleSettori(spazio);
                }
            }
            case 3:
                return;
            case 4:
                System.exit(0);
        }
    }

    private static void handleSettori(Spazio spazio) {
        GestioneSettori gestioneSettori = new GestioneSettori();

        int index = askForChooseMenuOption("SETTORI", new String[]{"Visualizza", "Monitora", "Gestisci Posizioni di un Settore (FIXME)", "Indietro", "Esci"});

        switch (index) {
            case 1: {
                gestioneSettori.visualizzaSettori(spazio.getId());
            }
            case 2: {
                Settore settore = gestioneSettori.richiediSettore(spazio);
                if (settore != null) {
                    gestioneSettori.avviaMonitoraggio(settore);
                } else {
                    System.out.println("Settore non trovato");
                }
            }
            case 3: {
                Settore settore = gestioneSettori.richiediSettore(spazio);
                if (settore != null) {
                    handlePozioni(settore);
                }
            }
            case 4:
                return;
            case 5:
                System.exit(0);
        }
    }

    private static void handlePosizioni(Settore settore) {
        GestionePosizioni gestionePosizioni = new GestionePosizioni();
        settore.setPosizioni(gestionePosizioni.getPosizioniBySettore(settore.getId()));
        int index = askForChooseMenuOption("POSIZIONI", new String[]{"Visualizza", "Monitora Posizone (TBD)", "Modifica Posizione (TBD)", "Indietro", "Esci"});

        switch (index) {
            case 1: {
                gestionePosizioni.visualizzaPosizioni(settore.getId());
            }
            case 2: {
                int idPosizione = askForInteger("Inserire ID della posizione da monitorare: ");
                gestionePosizioni.monitoraPosizone(idPosizione);
            }
            case 3: {
                //FIXME
                int idPosizione = askForInteger("ID Posizione da modificare: ");

                StringBuilder queryBuilder = new StringBuilder("UPDATE \"Posizione\" SET ");

                int index_attributo = askForChooseMenuOption("Quale attributo vuoi modificare?", new String[]{"assegnata", "settore", "irrigatore", "igrometroTerreno"});

                System.out.println("Inserisci il nuovo valore:");
                String valore = scanner.nextLine();

                switch (index_attributo) {
                    case 1: {
                        queryBuilder.append("assegnata").append(" = ? ");
                    }
                    case 2: {
                        queryBuilder.append("settore").append(" = ? ");
                    }
                    case 3: {
                        queryBuilder.append("irrigatore").append(" = ? ");
                    }
                    case 4: {
                        queryBuilder.append("igrometroTerreno").append(" = ? ");
                    }
                }

                queryBuilder.append("WHERE id = ?");

                String query = queryBuilder.toString();
                gestionePosizioni.modificaPosizione(idPosizione, query, valore, index_attributo);
            }
            case 4:
                return;
            case 5:
                System.exit(0);
        }
    }*/

    public static void printSettore(Settore settore) {

        System.out.println("+-----+---------+---------+----------+---------+---------+");
        System.out.println("| ID  | Temp.   | Umidità | Luce     | A/C     | Lampada |");
        System.out.println("+-----+---------+---------+----------+---------+---------+");

        // Valori corrispondenti
        System.out.printf("| %-3d | %-4.1f °C | %-4.1f  %% | %-4.1f l | %s | %s |\n",
                settore.getId(),
                settore.getTermometro().getValore(),
                settore.getIgrometroAria().getValore(),
                settore.getFotosensore().getValore(),
                settore.getClimatizzatore().isWorking() ? GreenString("  ON   ") : RedString("  OFF  "),
                settore.getLampada().isWorking() ? GreenString("  ON   ") : RedString("  OFF  "));

        System.out.println("+-----+---------+---------+----------+---------+---------+");
    }

    public static void printPosizioniInSettore(ArrayList<Map.Entry<Posizione, Pianta>> posizioni_piante) {
        System.out.println("+-----------+--------+------------+---------------------+-------------------+-------------+");
        System.out.println("| Posizione | Pianta |    Tipo    |        Stato        | Igrometro (Terra) | Irrigatore  |");
        System.out.println("+-----------+--------+------------+---------------------+-------------------+-------------+");

        for (var posizione_pianta : posizioni_piante) {
            Posizione posizione = posizione_pianta.getKey();
            Pianta pianta = posizione_pianta.getValue();


            // Valori corrispondenti
            System.out.printf("| %-9d | %-6d | %-10s | %-19s | %-15.1f %% | %-11s |\n",
                    posizione.getId(),
                    pianta.getId(),
                    pianta.getTipoPianta(),
                    pianta.getStato(),
                    posizione.getIgrometroTerra().getValore(),
                    posizione.getIrrigatore().isWorking() ? GreenString("    ON     ") : RedString("    OFF    "));

            System.out.println("+-----------+--------+------------+---------------------+-------------------+-------------+");

        }

    }

    public static void printOrdini(ArrayList<Ordine> ordini) {
        System.out.println("+------+---------+------------+--------+----------------+--------------------------+");
        System.out.println("|  ID  | Cliente | Consegna   | Totale | Stato          | Piante                   |");

        for (Ordine o : ordini) {
            System.out.println("+------+---------+------------+--------+----------------+--------------------------+");
            String[] pianteLinee = o.getPianteString().split("\n");

            System.out.printf("| %-4d | %-7d | %-10s | %-6.1f | %-14s | %-24s |\n",
                    o.getId(), o.getCliente(), o.getStringDataConsegna(), o.getTotale(), o.getStato(), pianteLinee[0]
            ); // Stampa la prima riga con i dati principali

            // Stampa le righe successive con le piante spezzate, mantenendo gli altri campi vuoti
            for (int i = 1; i < pianteLinee.length; i++) {
                System.out.printf("| %-4s | %-7s | %-10s | %-6s | %-14s | %-24s |\n", "", "", "", "", "", pianteLinee[i]);
            }
        }
        System.out.println("+------+---------+------------+--------+----------------+--------------------------+");
    }

    public static void printPiante(ArrayList<Pianta> piante) {
        System.out.println("+------+---------+------------+--------+---------+-------------------------------+");
        System.out.println("|  ID  | Tipo    | Piantato   | Stato  | Costo   | Descrizione                   |");

        for (Pianta p : piante) {
            System.out.println("+------+---------+------------+--------+---------+-------------------------------+");
            System.out.printf("| %-4d | %-7s | %-10s | %-6s | %-14.1f | %-24s |\n",
                    p.getId(), p.getTipoPianta(), p.getDataInizio().toString(), p.getStato(), p.getCosto(), p.getDescrizione()
            ); // Stampa la prima riga con i dati principali

            //ArrayList<String> pianteLinee = spezzaStringa(p.getDescrizione(), 24); //TODO RESTORE LOST METHOD "spezzaStringa"
            ArrayList<String> pianteLinee = new ArrayList<>(); //TODO REMOVE AFTER RESTORE "spezzaStringa"
            pianteLinee.add(p.getDescrizione()); //TODO REMOVE AFTER RESTORE "spezzaStringa"

            // Stampa le righe successive con le piante spezzate, mantenendo gli altri campi vuoti
            for (int i = 1; i < pianteLinee.size(); i++) {
                System.out.printf("| %-4s | %-7s | %-10s | %-6s | %-14s | %-24s |\n", "", "", "", "", "", pianteLinee.get(i));
            }
        }
        System.out.println("+------+---------+------------+--------+----------------+--------------------------+");
    }

    public static void printSpazio(Spazio spazio) {
        GestioneSettori gestioneSettori = new GestioneSettori();
        int i = 1;
        Settore s = gestioneSettori.getSettoreBySpazio(spazio.getId(), i);

        if (s == null) {
            System.out.println("  N/A   "); // Se non ci sono settori, stampa N/A
        } else {
            System.out.println("+------------------------------------------------------------------------------------------+");
            System.out.println("|   ID   | Spazio | Termometro |  Fotosensore | Climatizzatore | Lampada | Igrometro aria |");
            System.out.println("|--------|--------|------------|--------------|-----------------|---------|----------------|");
            // Ciclo che continua fino a quando non ci sono più settori
            do {
                System.out.printf("| %-6d | %-6d | %-10s | %-12s | %-15s | %-7s | %-14s |\n",
                        s.getId(), spazio.getId(),
                        (s.getTermometro() != null ? s.getTermometro().getId() : "N/A"),
                        (s.getFotosensore() != null ? s.getFotosensore().getId() : "N/A"),
                        (s.getClimatizzatore() != null ? (s.getClimatizzatore().isWorking() ? "ON" : "OFF") : "N/A"),
                        (s.getLampada() != null ? (s.getLampada().isWorking() ? "ON" : "OFF") : "N/A"),
                        (s.getIgrometroAria() != null ? s.getIgrometroAria().getId() : "N/A")
                );
                i++;
                s = gestioneSettori.getSettoreBySpazio(spazio.getId(), i); // Ottiene il settore successivo dello spazio specificato
            } while (s != null);
        }

        System.out.println("+------------------------------------------------------------------------------------------+");
    }
}