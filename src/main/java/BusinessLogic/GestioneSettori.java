package main.java.BusinessLogic;

import main.java.DomainModel.Impianto.*;
import main.java.DomainModel.Pianta.Pianta;
import main.java.ORM.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GestioneSettori {
    private ScheduledExecutorService executor;// Corrected: Added type declaration here

    public GestioneSettori() {}

    public Settore getSettoreBySpazio(int idSpazio, int i){
        SettoreDAO settoreDAO = new SettoreDAO();
        return settoreDAO.getSettoreBySpazio(idSpazio, i);
    }

    public void visualizzaSettori(int idSpazio) {
        SettoreDAO settoreDAO = new SettoreDAO();
        int i = 1;
        Settore s = settoreDAO.getSettoreBySpazio(idSpazio, i); // Supponiamo che il metodo recuperi i settori legati allo spazio

        if (s == null) {
            System.out.println("  N/A   "); // Se non ci sono settori, stampa N/A
        } else {
            System.out.println("+------------------------------------------------------------------------------------------+");
            System.out.println("|   ID   | Spazio | Termometro |  Fotosensore | Climatizzatore | Lampada | Igrometro aria |");
            System.out.println("|--------|--------|------------|--------------|-----------------|---------|----------------|");
            // Ciclo che continua fino a quando non ci sono più settori
            do {
                System.out.printf("| %-6d | %-6d | %-10s | %-12s | %-15s | %-7s | %-14s |\n",
                        s.getId(), idSpazio,
                        (s.getTermometro() != null ? s.getTermometro().getId() : "N/A"),
                        (s.getFotosensore() != null ? s.getFotosensore().getId() : "N/A"),
                        (s.getClimatizzatore() != null ? (s.getClimatizzatore().isWorking() ? "ON" : "OFF") : "N/A"),
                        (s.getLampada() != null ? (s.getLampada().isWorking() ? "ON" : "OFF") : "N/A"),
                        (s.getIgrometroAria() != null ? s.getIgrometroAria().getId() : "N/A")
                );
                i++;
                s = settoreDAO.getSettoreBySpazio(idSpazio, i); // Ottiene il settore successivo dello spazio specificato
            } while (s != null);
        }

        System.out.println("+------------------------------------------------------------------------------------------+");
    }

    public Settore getById(int id){
        SettoreDAO settoreDAO = new SettoreDAO();
        return settoreDAO.getById(id);
    }

    public ArrayList<Settore> get(Map<String, Object> criteri){
        SettoreDAO settoreDAO = new SettoreDAO();
        return settoreDAO.get(criteri);
    }

}
