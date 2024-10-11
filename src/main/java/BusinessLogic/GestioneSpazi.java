package main.java.BusinessLogic;

import main.java.DomainModel.Impianto.Spazio;
import main.java.ORM.SpazioDAO;

public class GestioneSpazi {

    public GestioneSpazi() {}

    public void creaSpazio(String nome, String descrizione, int nAmbientiMax){
        SpazioDAO spazioDAO = new SpazioDAO();
        spazioDAO.creaSpazio(nome, descrizione, nAmbientiMax);
    }

    public void rimuoviSpazio(int idSpazio){
        SpazioDAO spazioDAO = new SpazioDAO();
        spazioDAO.rimuoviSpazio(idSpazio);
    }

    public void visualizzaSpazi() {

        int i = 1;
        Spazio s = getSpazio(i);

        if (s == null) {
            System.out.println("|  N/A   |"); // Se il primo spazio è null, stampa N/A
        } else {
            System.out.println("+--------+");
            System.out.println("|   ID   |");
            System.out.println("+--------+");

            // Ciclo che continua fino a quando lo spazio non è null
            do {
                System.out.printf("| %-6d |\n", s.getId());
                i++;
                s = getSpazio(i);
            } while (s != null);
        }

        System.out.println("+--------+");

    }


    public int getNAmbientiMaxByIdSpazio(int idSpazio) {
        SpazioDAO spazioDAO = new SpazioDAO();
        return spazioDAO.getNAmbientiMaxByIdSpazio(idSpazio);
    }

    public Spazio getSpazio(int idSpazio) {
        SpazioDAO spazioDAO = new SpazioDAO();
        return spazioDAO.getSpazio(idSpazio);
    }

    public void visualizzaSpazio(int idSpazio) {
        //DONE
        SpazioDAO spazioDAO = new SpazioDAO();
        spazioDAO.visualizzaSpazio(idSpazio);
    }
}
