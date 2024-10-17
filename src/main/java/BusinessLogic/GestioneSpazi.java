package main.java.BusinessLogic;

import main.java.DomainModel.Impianto.Spazio;
import main.java.ORM.SpazioDAO;

public class GestioneSpazi {
    SpazioDAO spazioDAO;
    public GestioneSpazi() {
        spazioDAO = new SpazioDAO();
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

    public Spazio getSpazio(int idSpazio) {
        return spazioDAO.getSpazio(idSpazio);
    }
}
