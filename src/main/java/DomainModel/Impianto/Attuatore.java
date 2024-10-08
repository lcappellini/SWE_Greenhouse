package main.java.DomainModel.Impianto;

import java.util.Map;
public abstract class Attuatore {
    protected int id;
    protected boolean working;
    protected String tipoAttuatore; // Tipo dell'attuatore (es. "Climatizzatore", "Lampada", ecc.)
    protected String lavoro; // Descrizione del lavoro che svolge (es. "Raffreddamento", "Illuminazione")

    // Costruttore della classe Attuatore
    public Attuatore(int id) {
        this.id = id;
        this.working = false;
    }

    public Attuatore(int id, boolean working) {
        this.id = id;
        this.working = working;
    }

    // Getter e setter per gli attributi comuni
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }
   
 

    public String getLavoro() {
        return lavoro;
    }

    public void setLavoro(String lavoro) {
        this.lavoro = lavoro;
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
    // Metodo per ottenere il tipo dell'attuatore
    public abstract String tipoAttuatore() ;

    // Metodo per controllare se l'attuatore è attivo
    public boolean attivo() {
        return working;
    }

    public String getDescrizione() {
        return lavoro;
    }
}
