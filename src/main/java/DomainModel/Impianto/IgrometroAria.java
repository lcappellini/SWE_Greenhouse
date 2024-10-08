package main.java.DomainModel.Impianto;

import java.time.LocalDateTime;
import java.util.Random;

public class IgrometroAria extends Sensore<Float> {
    public IgrometroAria(int id) {
        super(id); // Chiama il costruttore della classe Sensore
    }

    @Override
    public Float misura(LocalDateTime lt, boolean attuatore_acceso) {
        Random rand = new Random();

        // Estrai l'ora dal LocalDateTime per adattare la media
        int ora = lt.getHour();
        float media, deviazione;

        // Regola la media e la deviazione standard in base all'ora del giorno
        if (ora >= 4 && ora < 12) {
            media = 80;  // Più umido al mattino
            deviazione = 10;  // Minor variabilità
        } else {
            media = 50;  // Meno umido nel resto del giorno
            deviazione = 20;  // Maggiore variabilità
        }

        // Genera un valore casuale seguendo la distribuzione normale
        float valoreGenerato = (float) (media + rand.nextGaussian() * deviazione);

        // Assicurati che il valore sia sempre entro i limiti fisici (0% - 100% umidità)
        this.valore = Math.max(0, Math.min(valoreGenerato, 100));

        return this.valore;
    }


    public String tipoSensore(){
        return "IgrometroAria";
    }
}