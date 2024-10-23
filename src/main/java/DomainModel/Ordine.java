package main.java.DomainModel;
import main.java.DomainModel.Pianta.Pianta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Ordine {
    private int id;
    private int cliente;
    private ArrayList<Pianta> piante;
    private LocalDate dataConsegna;
    private String stato;
    private double totale;

    public Ordine() {
        piante = new ArrayList<Pianta>();
    }

    public Ordine(int cliente, ArrayList<Pianta> piante) {
        this.cliente = cliente;
        this.piante = piante;
        this.dataConsegna = LocalDate.now().plusDays(maxGiorniRichiesti());
        this.stato = "da piantare";
        this.totale = setTotale();
    }

    public Ordine(int id, int cliente, ArrayList<Pianta> piante, String stato, String dataConsegna, double totale) {
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

    public String getStringDataConsegna(){
        return dataConsegna.toString();
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

    public void setPiante(String piante_string) {
        this.piante = new ArrayList<>();
        if(piante_string.contains("\n")){
            for (String line : piante_string.split("\n")) {
                String[] parts = line.split(" x ");
                int n = Integer.parseInt(parts[1]);
                for (int i = 0; i < n; i++)
                    this.piante.add(new Pianta(parts[0]));
            }
        }else if(piante_string.contains(", ")){
            for (String line : piante_string.split(", ")) {
                String[] parts = line.split(" x ");
                int n = Integer.parseInt(parts[1]);
                for (int i = 0; i < n; i++)
                    this.piante.add(new Pianta(parts[0]));
            }
        }

    }
    public void setPiante(ArrayList<Pianta> piante) { this.piante = piante; }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
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
