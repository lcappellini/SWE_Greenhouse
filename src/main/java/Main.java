package main.java;

import main.java.BuissnessLogic.GestioneAmbienti;
import main.java.BuissnessLogic.GestioneCliente;
import main.java.BuissnessLogic.GestioneOrdini;
import main.java.DomainModel.Cliente;
import main.java.DomainModel.Ordine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        handleAction();
    }

    public static void handleAction() throws SQLException, ClassNotFoundException {

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
                    System.out.println("\nEmail: ");
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
        String input;

        do {

            System.out.println(
                    """
                    \s
                     PAGINA ORDINI
                     1. Richiedi nuovo ordine
                     2. Controlla i miei ordini
                     3. Completa ordine
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
                    gestioneOrdini.completaOrdine(cliente);
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
        return new Ordine(cliente, tipoPianta, nPiante, dataConsegna);
    }

    public static void handleAdmin() {

        Scanner scanner = new Scanner(System.in);
        String input;

        do {

            System.out.println(
                    """
                    \s
                     PAGINA ADMIN
                     1. Gestione Ordini
                     2. Gestione Impianto
                     3. Gestione Posizionamenti
                     4. Gestione Piante
                     5. Indietro
                     6. Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> handleOrdini();
                case "2" -> handleImpianto();
                case "3" -> handlePosizionamenti();
                case "4" -> handlePiante();
                case "5" -> {return;}
                case "6" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);

    }
    public static void handleOrdini(){
        Scanner scanner = new Scanner(System.in);
        String input;

        do {

            System.out.println(
                    """
                    \s
                     GESTIONE ORDINI
                     1. Visualizza ordine
                     2. Modifica ordine
                     5. Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> handleOrdini();
                case "2" -> handleImpianto();
                case "3" -> handlePosizionamenti();
                case "4" -> {return;}
                case "5" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);
    }

    public static void handleImpianto(){
        Scanner scanner = new Scanner(System.in);
        String input;

        do {

            System.out.println(
                    """
                    \s
                     GESTIONE IMPIANTO
                     1. Gestione Ambienti
                     2. Gestione Spazi
                     3. Gestione Posizioni
                     4. Indietro
                     5. Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> handleAmbienti();
                case "2" -> handleSpazi();
                case "3" -> handlePosizioni();
                case "4" -> {return;}
                case "5" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);
    }

    private static void handleAmbienti() {
        Scanner scanner = new Scanner(System.in);
        String input;
        GestioneAmbienti gestioneAmbienti = new GestioneAmbienti();
        do {

            System.out.println(
                    """
                    \s
                     GESTIONE AMBIENTI
                     1. Registra Ambiente
                     2. Elimina Ambiente
                     3. Visualizza Ambienti
                     4. Indietro
                     5. Esci
                   \s"""
            );

            input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("\nNome Ambiente: ");
                    String nome = scanner1.nextLine();
                    System.out.println("Descrizione Ambiente: ");
                    String descrizione = scanner1.nextLine();
                    gestioneAmbienti.creaAmbiente(nome, descrizione);
                }
                case "2" -> {

                }
                case "3" -> {}
                case "4" -> {return;}
                case "5" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);
    }

    public static void handlePosizionamenti() {
        Scanner scanner = new Scanner(System.in);
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
                case "1" -> handleOrdini();
                case "2" -> handleImpianto();
                case "3" -> handlePosizionamenti();
                case "4" -> {
                    return;
                }
                case "5" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);
    }

    public static void handlePiante(){
        Scanner scanner = new Scanner(System.in);
        String input;

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
                case "1" -> handleOrdini();
                case "2" -> handleImpianto();
                case "3" -> handlePosizionamenti();
                case "4" -> {return;}
                case "5" -> System.exit(0);
                default -> System.out.println("Input invalido, si prega di riprovare.");
            }

        } while (true);
    }
}