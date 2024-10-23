package main.java.DomainModel.Impianto;

import java.util.Map;
public abstract class Attuatore {
    protected int id;
    protected boolean working;
    protected String tipoAttuatore; // Tipo dell'attuatore (es. "Climatizzatore", "Lampada", ecc.)
    protected String lavoro; // Descrizione del lavoro che svolge (es. "Raffreddamento", "Illuminazione")

    public Attuatore(int id, boolean working) {
        this.id = id;
        this.working = working;
    }

    // Getter e setter per gli attributi comuni
    public int getId() {
        return id;
    }

    public boolean isWorking() {
        return working;
    }

    public String getLavoro() {
        return lavoro;
    }

    // Metodo che esegue l'azione dell'attuatore in base al valore
    public String esegui(int value) {
        if (value > 0) {
            working = true;
            lavoro = tipoAttuatore + " " + id + " acceso. Valore: " + value;
        } else {
            working = false;
            lavoro = tipoAttuatore + " " + id + " spento.";
        }
        return lavoro;
    }

    public String getTipoAttuatore(){
        return tipoAttuatore;
    }
}
