package main.java.DomainModel;

public class Ordine {
    private Cliente cliente;
    private ArrayList<Pianta> piante;
    private String dataConsegna;
    private String dataInizio;
    private int prezzo;

    public Ordine(ArrayList<Pianta> piante) {
        this.piante = piante;
    }

    public Ordine(String dataConsegna, ArrayList<Pianta> piante, Cliente cliente) {
        this.dataConsegna = dataConsegna;
        this.piante = piante;
        this.cliente = cliente;
    }

    public int getnPiante() {
        return piante.size();
    }
}
