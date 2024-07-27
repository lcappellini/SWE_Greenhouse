package main.java.DomainModel.Pianta;

public class Pianta {
    private int id;
    private String tipoPianta;
    private String descrizione;
    private int minTemp;
    private int maxTemp;
    private int oreLuce;
    private int minAcqua;
    private int maxAcqua;

    public String getTipoPianta() {
        return tipoPianta;
    }

    public Pianta(String tipoPianta) {
        this.tipoPianta = tipoPianta;
    }

    public Pianta(int id, String tipoPianta, String descrizione) {
        this.id = id;
        this.tipoPianta = tipoPianta;
        this.descrizione = descrizione;
    }

    public boolean controllaStato() {
        boolean stato_buono;

        float probabilitaOttimale = 0.8f; // Valore di base

        // Genera stato
        if (Math.random() < probabilitaOttimale) {
            stato_buono = true;
            this.descrizione = "sta crescendo";
        } else {
            stato_buono = false;
            this.descrizione = "ha bisogno di cura";
        }

        return stato_buono;
    }
}
