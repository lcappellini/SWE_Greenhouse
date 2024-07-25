package main.java.DomainModel.Impianto;


import java.util.ArrayList;

public class Ambiente {
    private int id;
    private String nome;
    private String descrizione;
    private int nSpaziMax;
    private ArrayList<Spazio> spazi;

    public Ambiente(int id, String nome, String descrizione, int nSpaziMax) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.nSpaziMax = nSpaziMax;
    }

    public void setSpazi(ArrayList<Spazio> spazi) {
        this.spazi = spazi;
    }

    public ArrayList<Spazio> getSpazi() {
        return spazi;
    }

    public int getnSpaziMax() {
        return nSpaziMax;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getNSpaziMax() {
        return nSpaziMax;
    }
}
