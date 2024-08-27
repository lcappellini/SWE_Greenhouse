package main.java.DomainModel.Pianta;

public class Pianta {
    private int id;
    private String tipoPianta;
    private String descrizione;
    private double costo;
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
        this.setCosto(tipoPianta);
    }

    public Pianta(int id, String tipoPianta, String descrizione) {
        this.id = id;
        this.tipoPianta = tipoPianta;
        generaStato();
    }
    public double getCosto(){
        if(costo == 0) {
            return costo;
        }
        return 0;
    }
    public void setCosto(String tipoPianta){
        switch (tipoPianta){
            case "Basilico" -> this.costo = 1.5;
            case "Rosa" -> this.costo = 3.5;
            case "Geranio" -> this.costo = 2.5;
            case "Girasole" -> this.costo = 4;
            default -> this.costo = 0;
        }
    }
    public String getDescrizione(){
        return descrizione;
    }

    public void generaStato(){
        float probabilitaOttimale = 0.8f; // Valore di base

        // Genera stato
        if (Math.random() < probabilitaOttimale) {
            this.descrizione = "Sta crescendo";
        } else {
            this.descrizione = "Ha bisogno di cura";
        }
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
