package main.java.DomainModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Ordine {
    private int id;
    private int cliente;
    private ArrayList<Pianta> piante;
    private LocalDate dataConsegna;
    private StatoOrdine stato;
    private double totale;

    public Ordine(int cliente, ArrayList<Pianta> piante) {
        this.cliente = cliente;
        this.piante = piante;
        this.dataConsegna = LocalDate.now().plusDays(maxGiorniRichiesti());
        this.stato = StatoOrdine.da_piantare;
        this.totale = setTotale();
    }

    public Ordine(int id, int cliente, ArrayList<Pianta> piante, StatoOrdine stato, String dataConsegna, double totale) {
        this.id = id;
        this.piante = piante;
        this.stato = stato;
        this.dataConsegna = LocalDate.parse(dataConsegna);
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

    public String getStringDataConsegna(){
        return dataConsegna.toString();
    }

    public StatoOrdine getStato() {
        return stato;
    }

    public int getStatoId() {
        return stato.getId();
    }

    public String getStatoString() {
        return stato.name().replace("_", " ");
    }

    public double getTotale() {
        return totale;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStato(StatoOrdine stato) {
        this.stato = stato;
    }

    public String getPianteString() {
        HashMap<String, Integer> counter = new HashMap<>();
        for(Pianta p : piante){
            if (counter.containsKey(p.getTipoPianta()))
                counter.put(p.getTipoPianta(), counter.get(p.getTipoPianta())+1);
            else
                counter.put(p.getTipoPianta(), 1);
        }
        StringBuilder tp = new StringBuilder();
        for (var entry : counter.entrySet()) {
            tp.append(entry.getKey()).append(" x ");
            tp.append(entry.getValue());
            tp.append("\n");
        }
        //System.out.println(tp.substring(0, tp.length() - 1));
        return tp.substring(0, tp.length() - 1);
    }
    public String getPianteforDB() {
        HashMap<String, Integer> counter = new HashMap<>();
        for(Pianta p : piante){
            if (counter.containsKey(p.getTipoPianta()))
                counter.put(p.getTipoPianta(), counter.get(p.getTipoPianta())+1);
            else
                counter.put(p.getTipoPianta(), 1);
        }
        StringBuilder tp = new StringBuilder();
        for (var entry : counter.entrySet()) {
            tp.append(entry.getKey()).append(" x ");
            tp.append(entry.getValue());
            tp.append(", ");
        }

        //System.out.println(tp.substring(0, tp.length() - 1));
        return tp.substring(0, tp.length() - 2);
    }

    double setTotale(){
        double t = 0;
        for(Pianta p : piante){
            t += p.getCosto();
        }
        return t;
    }
}
