package main.java.DomainModel.Impianto;

import main.java.DomainModel.Pianta.Pianta;

public class Posizionamento {
    private Posizione posizione;
    private Pianta pianta;
    private Operatore operatore;


    public Posizionamento() {}

    public Posizionamento(Posizione posizione, Pianta pianta, Operatore operatore) {
        this.posizione = posizione;
        this.pianta = pianta;
        this.operatore = operatore;
    }



    public Posizione getPosizione() { return posizione; }

    public Pianta getPianta() { return pianta; }

    public Operatore getOperatore() { return operatore; }
}
