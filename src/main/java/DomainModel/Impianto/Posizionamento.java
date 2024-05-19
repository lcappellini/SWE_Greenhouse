package main.java.DomainModel.Impianto;

import main.java.DomainModel.Pianta.Pianta;

public class Posizionamento {
    private int id;

    public Posizionamento(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    /* Dati presenti soltanto nel db. Se serve, creare un metodo che popola gli attributi con una query dal db
    private Posizione posizione;
    private Pianta pianta;
    private Operatore operatore;

    public Posizionamento(Posizione posizione, Pianta pianta, Operatore operatore) {
        this.posizione = posizione;
        this.pianta = pianta;
        this.operatore = operatore;
    }

    public Posizione getPosizione() { return posizione; }
    public Pianta getPianta() { return pianta; }
    public Operatore getOperatore() { return operatore; }
    */

}
