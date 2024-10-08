package main.java.DomainModel.Pianta;

import java.time.LocalDate;

public class Pianta {
    private int id;
    private String tipoPianta;
    private LocalDate dataInizio;
    private StringBuilder descrizione;
    //FIXME statoooo
    private String stato;
    private double costo;
    private int giorni_rescita;
    private int minTemp;
    private int maxTemp;
    private int oreLuce;
    private int minAcqua;
    private int maxAcqua;

    public String getTipoPianta() {
        return tipoPianta;
    }


    public int getGiorni_rescita() {
        return giorni_rescita;
    }
    public void setGiorni_rescitaDaTipo(String tipoPianta) {
        switch (this.tipoPianta){
            case "Basilico" -> giorni_rescita = 15;
            case "Rosa" -> giorni_rescita = 30;
            case "Geranio" -> giorni_rescita = 45;
            case "Girasole" -> giorni_rescita = 50;
        }
    }
    public Pianta(String tipoPianta) {
        this.tipoPianta = tipoPianta;
        this.setGiorni_rescitaDaTipo(tipoPianta);
        this.setCosto(tipoPianta);
    }

    public Pianta(int id, String tipoPianta) {
        this(tipoPianta);
        this.id = id;
        this.dataInizio = LocalDate.now();
        this.descrizione.append("La pianta è stata posata il ");
        this.descrizione.append(dataInizio.toString());
        this.descrizione.append(". ");

    }

    public Pianta(int id, String tipoPianta, String descrizione) {
        this(id, tipoPianta);
        this.descrizione.append(descrizione);
    }



    public double getCosto(){ return costo; }
    public void setCosto(String tipoPianta){
        switch (tipoPianta){
            case "Basilico" -> this.costo = 1.5;
            case "Rosa" -> this.costo = 3.5;
            case "Geranio" -> this.costo = 2.5;
            case "Girasole" -> this.costo = 4;
            default -> this.costo = 0;
        }
    }
    public StringBuilder getDescrizione(){
        return descrizione;
    }

    public void generaStato(){
        float probabilitaOttimale = 0.8f; // Valore di base

        // Genera stato
        if (Math.random() < probabilitaOttimale) {
            this.stato = "Sta crescendo. ";
        } else {
            this.stato = "Ha bisogno di cura";
        }
    }

    public boolean controllaStato() {
    //FIXME
        boolean stato_buono;

        float probabilitaOttimale = 0.8f; // Valore di base

        // Genera stato
        if (Math.random() < probabilitaOttimale) {
            stato_buono = true;
            this.stato = "sta crescendo";
        } else {
            stato_buono = false;
            this.stato = "ha bisogno di cura";
        }

        return stato_buono;
    }

    public int getId() {return this.id;}

    public LocalDate getDataInizio() {return this.dataInizio;}
    public String getStato() {return this.stato;}

    public void setId(int id) {this.id = id;}

    public void setDescrizione(String descrizione_) {
        this.descrizione = new StringBuilder(descrizione_);
    }

    public void setDataInizio(LocalDate now) {
        this.dataInizio = now;
    }
}
