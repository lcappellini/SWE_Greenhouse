package main.java.DomainModel;
import main.java.DomainModel.Pianta.Pianta;

import java.time.LocalDate;
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
        this.dataConsegna = LocalDate.now().plusDays(maxGiorniRichiesti());
        this.stato = "da posizionare";
        this.totale = setTotale();
    }

    public Ordine(int id, int cliente, String piante, String stato, String dataConsegna, double totale) {
        this.id = id;
        this.stato = stato;
        this.dataConsegna = LocalDate.parse(dataConsegna);
        setPiante(piante);
        this.cliente = cliente;
        this.totale = totale;
    }

    int maxGiorniRichiesti(){
        int i = 0;
        for(Pianta p : piante){
            if(i<p.getGiorni_crescita()){
                i = p.getGiorni_crescita();
            }
        }
        return i;
    }

    public Ordine(int id_cliente, String piante, String stato, String dataConsegna) {
        this.stato = stato;
        this.dataConsegna = LocalDate.parse(dataConsegna);
        setPiante(piante);
        this.cliente = id_cliente;
    }

    public Ordine(int id, int id_cliente, String piante, String stato, String dataConsegna) {
        this(id_cliente, piante, stato, dataConsegna);
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
            throw new IllegalArgumentException("Errore: La stringa delle piante è vuota.");
        }

        // Dividi la stringa delle piante in base alla virgola (anche se ci sono spazi aggiuntivi)
        String[] nomiPiante = piante.split("\\s*,\\s*");

        // Crea una lista di oggetti Pianta
        ArrayList<Pianta> listaPiante = new ArrayList<>();

        // Trasforma ogni nome in un oggetto Pianta e aggiungilo alla lista
        for (String nome : nomiPiante) {
            // Verifica che il nome non sia vuoto dopo il trim
            if (!nome.trim().isEmpty()) {
                listaPiante.add(new Pianta(nome.trim())); // Crea una nuova Pianta solo se il nome non è vuoto
            }
        }

        // Se la lista risultante è vuota, segnala un errore
        if (listaPiante.isEmpty()) {
            throw new IllegalArgumentException("Errore: Nessuna pianta valida trovata nella stringa fornita.");
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



    public String getPianteString() {
        StringBuilder tp = new StringBuilder();
        for(Pianta p : piante){
            tp.append(p.getTipoPianta()).append(", ");
        }
        tp = new StringBuilder(tp.substring(0, tp.length() - 2));
        return tp.toString();
    }

    double setTotale(){
        double t = 0;
        for(Pianta p : piante){
            t += p.getCosto();
        }
        return t;
    }

}
