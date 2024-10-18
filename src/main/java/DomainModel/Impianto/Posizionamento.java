package main.java.DomainModel.Impianto;

import main.java.DomainModel.Ordine;
import main.java.DomainModel.Pianta.Pianta;

public class Posizionamento {
    private int id;
    private Posizione posizione;
    private Pianta pianta;
    private Ordine ordine;

    public Posizionamento(Posizione posizione, Pianta pianta, Ordine ordine) {
        this.posizione = posizione;
        this.pianta = pianta;
        this.ordine = ordine;
    }

    public Posizionamento(int id, Posizione posizione, Ordine ordine, Pianta pianta) {
        this(posizione, pianta, ordine);
        this.id = id;
    }

    public Posizione getPosizione() { return posizione; }

    public Pianta getPianta() { return pianta; }

    public Ordine getOrdine() { return this.ordine;}
}
