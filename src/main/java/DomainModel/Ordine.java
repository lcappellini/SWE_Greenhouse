package main.java.DomainModel;
import main.java.DomainModel.Pianta.Pianta;

import java.util.ArrayList;

public class Ordine {
    private int id;
    private Cliente cliente;
    private ArrayList<Pianta> piante;
    private String dataConsegna;
    private String dataInizio;
    private String stato;
    private int prezzo;

    public Ordine() {
    }

    public Ordine(String dataConsegna, ArrayList<Pianta> piante, Cliente cliente) {
        this.dataConsegna = dataConsegna;
        this.piante = piante;
        this.cliente = cliente;
    }

    public Ordine(Cliente cliente, ArrayList<Pianta> piante, String dataConsegna) {
        this.cliente = cliente;
        this.piante = piante;
        this.dataConsegna = dataConsegna;
    }

    public Ordine(Cliente cliente, String tipoPianta, int nPiante, String dataConsegna) {
        this.cliente = cliente;
        this.piante = new ArrayList<>();
        for (int i = 0; i < nPiante; i++) {
            this.piante.add(new Pianta(tipoPianta));
        }
        this.dataConsegna = dataConsegna;
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

    public String getDataConsegna() {
        return dataConsegna;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setPiante(ArrayList<Pianta> piante) {
        this.piante = piante;
    }
    public void setPianteDalTipo(String tipoPianta, int nPiante) {
        for (int i = 0; i < nPiante; i++) {
            this.piante.add(new Pianta(tipoPianta));
        }
    }
    public void setDataConsegna(String dataConsegna) {
        this.dataConsegna = dataConsegna;
    }

    public void setDataInizio(String dataInizio) {
        this.dataInizio = dataInizio;
    }



    public void setStato(String stato) {
        this.stato = stato;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setCliente(int idcliente) {
        this.cliente = new Cliente(idcliente);
    }
}
