package main.java.BusinessLogic;

import main.java.DomainModel.Impianto.Spazio;
import main.java.ORM.SpazioDAO;

public class GestioneSpazi {
    public GestioneSpazi() {}

    public void visualizzaSpazi() {
        SpazioDAO spazioDAO = new SpazioDAO();
        int i = 1;
        Spazio s = spazioDAO.getSpazio(i);

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
                s = spazioDAO.getSpazio(i);
            } while (s != null);
        }
        System.out.println("+--------+");
    }
}
