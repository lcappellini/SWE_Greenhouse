package main.java.DomainModel.Impianto;

import java.time.LocalDateTime;

public class Fotosensore extends Sensore<Integer> {

    public Fotosensore(int id) {
        super(id); // Chiama il costruttore della classe Sensore
    }

    @Override
    public Integer misura(LocalDateTime lt, boolean attuatore_acceso) {
        int ora = lt.getHour();
        int valoreLuce;

        // Logica per la misurazione della luce in base all'ora del giorno
        if (ora >= 6 && ora <= 18) {
            // Giorno: valori di luce tra 100 e 3000
            valoreLuce = 100 + (int) (Math.random() * (3000 - 100 + 1)); // Valore tra 100 e 3000
        } else {
            // Notte: valori di luce più bassi, tra 0 e 50 (o anche un valore minimo)
            valoreLuce = 0 + (int) (Math.random() * 51); // Valore tra 0 e 50
        }

        // Se l'attuatore è acceso (es. lampada accesa), aggiunge luce artificiale
        if (attuatore_acceso) {
            // Aggiunge un valore casuale tra 50 e 500 per simulare la luce artificiale
            valoreLuce += 50 + (int) (Math.random() * 451); // Aggiunge un valore tra 50 e 500
        }

        // Limita il valore massimo della luce a 3000
        valoreLuce = Math.min(valoreLuce, 3000);

        this.valore = valoreLuce; // Assegna il valore generato
        return this.valore;
    }


    public String tipoSensore(){
        return "Fotosensore";
    }
}
