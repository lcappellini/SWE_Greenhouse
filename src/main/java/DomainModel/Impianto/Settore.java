package main.java.DomainModel.Impianto;


import java.util.*;

public class Settore {

    private int id;
    private ArrayList<Posizione> posizioni;
    private ArrayList<Sensore> sensori;
    private ArrayList<Attuatore> attuatori;
    private Map<Sensore, Attuatore> associazioneSensoreAttuatore;
    private Map<String, Range<Float>> rangeAccettabili;

    private Climatizzatore climatizzatore;
    private Lampada lampada;
    private Fotosensore fotosensore;
    private Termometro termometro;
    private IgrometroAria igrometroAria;

    public Settore(int id) {
        this.id = id;
        this.posizioni = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            this.posizioni.add(new Posizione(i));
        }
        // Inizializzazione sensori e attuatori
        this.sensori = new ArrayList<>();
        this.attuatori = new ArrayList<>();

        this.rangeAccettabili = new HashMap<>();
        this.rangeAccettabili.put("Termometro", new Range<>(14.0f, 30.0f));  // Range: 14°C - 30°C
        this.rangeAccettabili.put("IgrometroAria", new Range<>(35.0f, 85.0f));  // Range: 35% - 85% di umidità
        this.rangeAccettabili.put("Fotosensore", new Range<>(300.0f, 3000.0f));  // Range: 300 - 3000 lux
    }

    public Settore(int id, ArrayList<Posizione> posizioni, Termometro termometro, IgrometroAria igrometroAria, Fotosensore fotosensore, Climatizzatore climatizzatore, Lampada lampada) {
        this.id = id;
        this.posizioni = posizioni;//add controllo numero posizioni?
        this.rangeAccettabili = new HashMap<>();
        this.rangeAccettabili.put("Termometro", new Range<>(14.0f, 30.0f));  // Range: 14°C - 30°C
        this.rangeAccettabili.put("IgrometroAria", new Range<>(35.0f, 85.0f));  // Range: 35% - 85% di umidità
        this.rangeAccettabili.put("Fotosensore", new Range<>(300.0f, 3000.0f));  // Range: 300 - 3000 lux
        this.termometro = termometro;
        this.igrometroAria = igrometroAria;
        this.fotosensore = fotosensore;
        this.climatizzatore = climatizzatore;
        this.lampada = lampada;
    }

    public Climatizzatore getClimatizzatore() {
        return climatizzatore;
    }
    public Lampada getLampada() {
        return lampada;
    }
    public Fotosensore getFotosensore() {
        return fotosensore;
    }

    public IgrometroAria getIgrometroAria() {
        return igrometroAria;
    }
    public Termometro getTermometro() {
        return termometro;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Sensore> getSensori() {
        return new ArrayList<>(List.of(termometro, igrometroAria, fotosensore));
    }

    public ArrayList<Attuatore> getAttuatori() {
        return new ArrayList<>(List.of(climatizzatore, lampada));
    }

    public Attuatore getAttuatoreAssociato(Sensore s) {
        if ("termometro".equalsIgnoreCase(s.getTipoSensore()) || "igrometroAria".equalsIgnoreCase(s.getTipoSensore()))
            return climatizzatore;
        else if ("fotosensore".equalsIgnoreCase(s.getTipoSensore()))
            return lampada;
        return null;
    }

    public boolean isSensorValueInRange(String tipoSensore, float misura) {
        if (rangeAccettabili.containsKey(tipoSensore)) {
            Range<Float> range = rangeAccettabili.get(tipoSensore);
            return misura >= range.getMin() && misura <= range.getMax();
        }
        return true;
    }
}
