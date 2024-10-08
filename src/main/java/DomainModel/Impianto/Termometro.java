package main.java.DomainModel.Impianto;

import java.time.LocalDateTime;
import java.util.Map;

public class Termometro extends Sensore<Float> {
    public Termometro(int id) {
        super(id);
    }

    @Override
    public Float misura(LocalDateTime lt, boolean attuatore_acceso) {
        int ora = lt.getHour();
        float temperatura;

        // Base probabilità di temperatura ottimale (range tra 14 e 30 gradi)
        float probabilitaOttimale = 0.5f;

        // Se l'attuatore è acceso, la probabilità di restare nel range ottimale aumenta
        if (attuatore_acceso) {
            probabilitaOttimale += 0.3f;
        }

        // Aumenta la probabilità se siamo durante il giorno (più caldo)
        if (ora >= 8 && ora <= 18) {
            probabilitaOttimale += 0.2f;
        } else {
            // Diminuisce leggermente la probabilità durante la notte (più freddo)
            probabilitaOttimale -= 0.1f;
        }

        // Genera la temperatura in base alla probabilità calcolata
        if (Math.random() < probabilitaOttimale) {
            // Genera una temperatura nella gamma ottimale [14, 30]
            temperatura = 14 + (float) Math.random() * 16; // Range [14, 30]
        } else {
            // Genera una temperatura fuori dal range ottimale
            if (Math.random() < 0.5) {
                // Gamma di temperature fredde [3, 14)
                temperatura = 3 + (float) Math.random() * 11;
            } else {
                // Gamma di temperature calde (30, 37]
                temperatura = 30 + (float) Math.random() * 7;
            }
        }

        // Assegna il valore generato alla variabile membro e restituisce il risultato
        this.valore = temperatura;
        return this.valore;
    }


    @Override
    public String tipoSensore(){
        return "Termometro";
    }
}