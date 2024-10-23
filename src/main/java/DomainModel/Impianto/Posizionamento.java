package main.java.DomainModel.Impianto;

public class Posizionamento {
    private int id;
    private int idPosizione;
    private int idPianta;
    private int idOrdine;

    public Posizionamento(int idPosizione, int idPianta, int idOrdine) {
        this.idPosizione = idPosizione;
        this.idPianta = idPianta;
        this.idOrdine = idOrdine;
    }

    public Posizionamento(int id, int idPosizione, int idPianta, int idOrdine) {
        this(idPosizione, idPianta, idOrdine);
        this.id = id;
    }

    public int getIdPosizione() { return idPosizione; }

    public int getIdPianta() { return idPianta; }

    public int getIdOrdine() { return idOrdine;}
}
