package main.java;

import main.java.BusinessLogic.*;
import main.java.DomainModel.Admin;
import main.java.DomainModel.Cliente;
import main.java.DomainModel.Impianto.*;
import main.java.DomainModel.Ordine;
import main.java.DomainModel.Pianta.Pianta;
import main.java.ORM.OperazioneDAO;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Thread.sleep;

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
                    \s
                    [1]  Cliente
                    [2]  Admin
                    [3]  Esci
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
                                LOG IN / SIGN UP CLIENTE
                     \s
                     [1]  Registrati
                     [2]  Accedi
                     [3]  Indietro
                     [4]  Esci
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
                                MAIN
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
                                        MAIN
                                        \s
                    [1]  ORIDINI
                    [2]  IMPIANTO
                    [3]  PIANTE
                    [4]  POSIZIONAMENTI
                    [5]  Indietro
                    [6]  Esci
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
                     2. Gestisci Settori di uno Spazio
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
                     3. Gestisci Posizioni di un Settore (FIXME)
                     4. Indietro
                     5. Esci
                     6. Richiedi Operatore per Check-up Piante
                     7. 
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
                     GESTIONE POSIZIONI
                     1. Visualizza Posizioni
                     2. Monitora Posizone (TBD)
                     3. Modifica Posizione (TBD)
                     4. Indietro
                     5. Esci
                     6. Richiedi Operatore per Cura Pianta
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
                     1. Visualizza Piante
                     2. Check-up Piante 
                     3. Aggiungi tipo di pianta (TBD)
                     4. Rimuovi tipo di pianta (TBD)
                     5. Indietro
                     6. Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {gestionePiante.visualizzaTutte();}
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
                case "4" -> {return;}
                case "6" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);
    }
}