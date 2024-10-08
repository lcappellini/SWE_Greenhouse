package main.java.DomainModel.Impianto;

import main.java.DomainModel.Ordine;
import main.java.DomainModel.Pianta.Pianta;

public class Posizionamento {
    private int id;
    private Posizione posizione;
    private Pianta pianta;
    private Operatore operatore;
    private Ordine ordine;


    public Posizionamento() {}

    public Posizionamento(int id, Posizione posizione, Pianta pianta, Operatore operatore, Ordine ordine) {
        this.id = id;
        this.posizione = posizione;
        this.pianta = pianta;
        this.operatore = operatore;
        this.ordine = ordine;
    }

    public Posizionamento(Posizione posizione, Pianta pianta, Operatore operatore, Ordine ordine) {
        this.posizione = posizione;
        this.pianta = pianta;
        this.operatore = operatore;
        this.ordine = ordine;
    }



    public Posizione getPosizione() { return posizione; }

    public Pianta getPianta() { return pianta; }

    public Operatore getOperatore() { return operatore; }

    public Ordine getOrdine() { return this.ordine;}
}
