package main.java.BusinessLogic;

import main.java.DomainModel.Impianto.*;
import main.java.ORM.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GestioneSettori {
    private SettoreDAO settoreDAO;
    private SensoreDAO sensoreDAO;
    private AttuatoreDAO attuatoreDAO;
    private ScheduledExecutorService executor;// Corrected: Added type declaration here
    private GestioneAttuatori gestioneAttuatori;
    private GestioneSensori gestioneSensori;



    public GestioneSettori() {
        settoreDAO = new SettoreDAO();
        attuatoreDAO = new AttuatoreDAO();
        sensoreDAO = new SensoreDAO();
        gestioneAttuatori = new GestioneAttuatori();
        gestioneSensori = new GestioneSensori();
    }

    public void creaSettore(int idSpazio, int nPosizioniMax,
                            int idTermometro, int idFotosensore, int idIgrometroAria,
                            int idClimatizzazione, int idLampada){
        settoreDAO.creaSettore(idSpazio, nPosizioniMax, idTermometro, idFotosensore,
                idIgrometroAria, idClimatizzazione, idLampada);
    }
    public void rimuoviSettore(int idAmbiente) {
        settoreDAO.rimuoviSettore(idAmbiente);
    }
    public void visualizzaSettori(int idSpazio){
        settoreDAO.visualizzaSettori(idSpazio);
    }

    public void monitoraSettore(Settore settore, LocalDateTime lt) {
        settore.monitora(lt);
        for (Sensore<?> s : settore.getSensori()) {
            sensoreDAO.registraMisura(s);
        }
        for (Attuatore a : settore.getAttuatori()) {
            attuatoreDAO.registraAzione(a, lt.toString());
        }
        // Definisci un formatter per il formato "3 Jun 2008 11:05:30"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm:ss");

        // Converte LocalDateTime in stringa formattata
        String formattedLt = lt.format(formatter);
        // Stampa formattata dei valori
        System.out.println(".");
        // Stampa formattata dei valori con parametri in riga
        System.out.println("TIME: " + formattedLt);  // Stampa il tempo formattato

        System.out.println("| Termometro        | Igrometro (Aria)  | Fotosensore       | A/C     | Lampada |");
        System.out.println("+-------------------+-------------------+-------------------+---------+---------+");

        // Valori corrispondenti
        System.out.printf("| %-14f °C | %-12f /100 | %-11s lumen | %-8s| %-8s|\n",
                settore.getTermometro().getValore(),
                settore.getIgrometroAria().getValore(),
                settore.getFotosensore().getValore(),
                settore.getClimatizzazione().isWorking() ? "ON" : "OFF",
                settore.getLampada().isWorking() ? "ON" : "OFF");

        System.out.println("+-------------------+-------------------+-------------------+---------+---------+");

    }

    public void avviaMonitoraggio(Settore settore) {
        executor = Executors.newScheduledThreadPool(1);

        // Usa un array come wrapper per LocalDateTime
        final LocalDateTime[] lt = {LocalDateTime.now()};

        // Esegue il task di monitoraggio ogni 2 secondi, simulando l'avanzamento di 1 ora
        executor.scheduleAtFixedRate(() -> {
            try {
                // Chiama la funzione monitoraSettore con l'ora simulata
                monitoraSettore(settore, lt[0]);

                // Aggiunge 1 ora a ogni iterazione
                lt[0] = lt[0].plusMinutes(15);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 6, TimeUnit.SECONDS);

        // Crea un thread separato per ascoltare la pressione di un tasto
        new Thread(() -> {
            try {
                System.out.println("Premi un tasto per interrompere il monitoraggio...");

                // Attende la pressione di un tasto
                System.in.read();

                // Ferma il monitoraggio quando viene premuto un tasto
                System.out.println("Tasto premuto! Arresto monitoraggio...");
                stopMonitoraggio();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    // Funzione per fermare il monitoraggio
    public void stopMonitoraggio() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown(); // Arresta il monitoraggio
            System.out.println("Monitoraggio terminato.");
        }
    }
    public Settore getById(int id){
        return settoreDAO.getById(id);
    }
    public Settore getSettore(Spazio spazio){
        Scanner scanner1 = new Scanner(System.in);
        //sensoreDAO.visualizza(spazio.getId());
        visualizzaSettori(spazio.getId());
        System.out.println("Inserire ID dello settore da monitorare: ");
        int idSettore = Integer.parseInt(scanner1.nextLine());
        Settore settore = null;
        settore = getById(idSettore);
        if(settore == null){
            System.out.println("Settore non trovato.");
            return null;
        }
        return settore;
    }




}
