package main.java.DomainModel;
import main.java.DomainModel.Pianta.Pianta;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Ordine {
    private int id;
    private int cliente;
    private ArrayList<Pianta> piante;
    private LocalDate dataConsegna;
    private String descrizione;
    private String stato;
    private double totale;

    public Ordine() {
        piante = new ArrayList<Pianta>();
    }



    public Ordine(int cliente, ArrayList<Pianta> piante) {
        this.cliente = cliente;
        this.piante = piante;
        this.dataConsegna = LocalDate.now();
        this.dataConsegna = this.dataConsegna.plusDays(maxGiorniRichiesti());
        this.stato = "da posizionare";
        this.totale = setTotale();
    }

    int maxGiorniRichiesti(){
        int i = 0;
        for(Pianta p : piante){
            if(i<p.getGiorni_rescita()){
                i = p.getGiorni_rescita();
            }
        }
        return i;
    }


    public Ordine(int id_cliente, String piante, String stato, String dataConsegna,   String descrizione) {
        this.stato = stato;
        this.dataConsegna = LocalDate.now();
        setPiante(piante);
        setTotale();
        this.cliente = id_cliente;
    }
    public Ordine(int id, int id_cliente, String piante, String stato, String dataConsegna,   String descrizione) {
        this(id_cliente, piante, stato, dataConsegna, descrizione);
        this.id = id;
    }






    public int getId() {
        return id;
    }

    public int getnPiante() {
        return piante.size();
    }

    public int getCliente() {
        return cliente;
    }

    public ArrayList<Pianta> getPiante() {
        return piante;
    }
    public String getTipoPianta(int index){
        return piante.get(index).getTipoPianta();
    }

    public LocalDate getDataConsegna() {
        return dataConsegna;
    }


    public String getStringDataConsegna(){
        return dataConsegna.toString();
    }

    public String getDescrizione() {
        return descrizione;
    }


    public String getStato() {
        return stato;
    }

    public double getTotale() {
        return totale;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPiante(ArrayList<Pianta> piante) {
        this.piante = piante;
    }

    public void setPiante(String piante) {
        // Controlla che la stringa non sia vuota o nulla
        if (piante == null || piante.trim().isEmpty()) {
            System.out.println("Errore: La stringa delle piante è vuota.");
            return;
        }

        // Dividi la stringa delle piante in base alla virgola
        String[] nomiPiante = piante.split(",");

        // Crea una lista di oggetti Pianta
        ArrayList<Pianta> listaPiante = new ArrayList<>();

        // Trasforma ogni nome in un oggetto Pianta e aggiungilo alla lista
        for (String nome : nomiPiante) {
            listaPiante.add(new Pianta(nome.trim())); // Trim per rimuovere eventuali spazi
        }

        // Imposta la lista delle piante nella classe
        this.piante = listaPiante;

    }


    public void setStato(String stato) {
        this.stato = stato;
    }



    public void setCliente(int cliente) {
        this.cliente = cliente;
    }



    public String getStringTipoPiante() {
        StringBuilder tipoPiante = new StringBuilder(piante.get(0).getTipoPianta());
        for(Pianta p : piante){
            if(!tipoPiante.toString().contains(p.getTipoPianta())){
                tipoPiante.append(p.getTipoPianta());
                tipoPiante.append(", ");
            }
        }
        tipoPiante.replace(0, tipoPiante.length()-2, tipoPiante.toString());
        return tipoPiante.toString();
    }

    double setTotale(){
        double t = 0;
        for(Pianta p : piante){
            t += p.getCosto();
        }
        return t;
    }

}
