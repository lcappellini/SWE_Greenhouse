package main.java.DomainModel;
import main.java.DomainModel.Cliente;
import main.java.DomainModel.Pianta.Pianta

import java.util.ArrayList;

public class Ordine {
    private int id;
    private Cliente cliente;
    private ArrayList<Pianta> piante;
    private String dataScadenza;
    private String dataInizio;
    private String stato;
    private int prezzo;


    public Ordine(String dataScadenza, ArrayList<Pianta> piante, Cliente cliente) {
        this.dataScadenza = dataScadenza;
        this.piante = piante;
        this.cliente = cliente;
    }

    public Ordine(Cliente cliente, ArrayList<Pianta> piante, String dataScadenza) {
        this.cliente = cliente;
        this.piante = piante;
        this.dataScadenza = dataScadenza;
    }

    public int getId() {
        return id;
    }

    public int getnPiante() {
        return piante.size();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public ArrayList<Pianta> getPiante() {
        return piante;
    }
    public String getTipoPiante(){
        return piante.get(0).getTipoPianta();
    }

    public String getDataScadenza() {
        return dataScadenza;
    }

    public String getDataInizio() {
        return dataInizio;
    }

    public String getStato() {
        return stato;
    }

    public int getPrezzo() {
        return prezzo;
    }
}
