package main.java.DomainModel.Impianto;


import java.util.ArrayList;

public class Spazio {
    private int id;
    private String nome;
    private String descrizione;
    private int nAmbientiMax;
    private ArrayList<Ambiente> ambienti;

    public Spazio(int id, String nome, String descrizione, int nAmbientiMax) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.nAmbientiMax = nAmbientiMax;
    }

    public void setAmbienti(ArrayList<Ambiente> ambienti) {
        this.ambienti = ambienti;
    }

    public ArrayList<Ambiente> getAmbienti() {
        return ambienti;
    }

    public int getnAmbientiMax() {
        return nAmbientiMax;
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

    public int getNAmbientiMax() {
        return nAmbientiMax;
    }
}
