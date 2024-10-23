package main.java.DomainModel.Impianto;


import java.util.*;

public class Settore {

    private int id;
    private ArrayList<Posizione> posizioni;
    private Map<String, float[]> rangeAccettabili;

    private Climatizzatore climatizzatore;
    private Lampada lampada;
    private Fotosensore fotosensore;
    private Termometro termometro;
    private IgrometroAria igrometroAria;

    public Settore(int id, ArrayList<Posizione> posizioni, Termometro termometro, IgrometroAria igrometroAria, Fotosensore fotosensore, Climatizzatore climatizzatore, Lampada lampada) {
        this.id = id;
        this.posizioni = posizioni;
        this.rangeAccettabili = new HashMap<String, float[]>();
        this.rangeAccettabili.put("Termometro", new float[]{14.0f, 30.0f});  // Range: 14°C - 30°C
        this.rangeAccettabili.put("IgrometroAria", new float[]{35.0f, 85.0f});  // Range: 35% - 85% di umidità
        this.rangeAccettabili.put("Fotosensore", new float[]{300.0f, 3000.0f});  // Range: 300 - 3000 lux
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

    public Attuatore getAttuatoreAssociato(Sensore s) {
        if ("termometro".equalsIgnoreCase(s.getTipoSensore()) || "igrometroAria".equalsIgnoreCase(s.getTipoSensore()))
            return climatizzatore;
        else if ("fotosensore".equalsIgnoreCase(s.getTipoSensore()))
            return lampada;
        return null;
    }

    public boolean isSensorValueInRange(String tipoSensore, float misura) {
        if (rangeAccettabili.containsKey(tipoSensore)) {
            float[] range = rangeAccettabili.get(tipoSensore);
            return misura >= range[0] && misura <= range[1];
        }
        return true;
    }
}
