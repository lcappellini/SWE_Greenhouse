package main.java.DomainModel.Impianto;

import java.time.LocalDateTime;
import java.util.Map;

public class Termometro extends Sensore {
    public Termometro(int id) {
        super(id);
    }

    @Override
    public float misura(LocalDateTime lt, boolean attuatore_acceso) {
        int ora = lt.getHour();
        float temperatura;

        // Probabilità di essere nella gamma ottimale
        float probabilitaOttimale = 0.5f; // Valore di base

        // Incrementa la probabilità se l'attuatore è acceso
        if (attuatore_acceso) {
            probabilitaOttimale += 0.3f;
        }

        // Calcola la probabilità in base all'ora del giorno
        if (ora >= 8 && ora <= 18) {
            probabilitaOttimale += 0.2f; // Più caldo durante il giorno
        } else {
            probabilitaOttimale -= 0.1f; // Più freddo durante la notte
        }

        // Genera la temperatura
        if (Math.random() < probabilitaOttimale) {
            // Genera una temperatura nella gamma ottimale [18, 30]
            temperatura = 18 + (float) Math.random() * 12;
        } else {
            // Genera una temperatura fuori dalla gamma ottimale [7, 18) o (30, 37]
            if (Math.random() < 0.5) {
                temperatura = 7 + (float) Math.random() * 11; // [7, 18)
            } else {
                temperatura = 30 + (float) Math.random() * 7; // (30, 37]
            }
        }

        this.valore = temperatura;
        return this.valore;
    }

    @Override
    public String tipoSensore(){
        return "Termometro";
    }
}