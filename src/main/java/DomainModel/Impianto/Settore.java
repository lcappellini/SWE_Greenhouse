package main.java.DomainModel.Impianto;


import java.time.LocalDateTime;
import java.util.*;

public class Settore {

    private int id;
    private ArrayList<Posizione> posizioni;
    private List<Sensore> sensori;
    private List<Attuatore> attuatori;
    private Map<Sensore, Attuatore> associazioneSensoreAttuatore;
    /*
    private Climatizzazione climatizzatore;
    private Lampada lampada;
    private Fotosensore fotosensore;
    private Termometro termometro;
    private IgrometroAria igrometroAria;


    public Settore(int id, int nPosizioniMax) {
        this.id = id;
        this.nPosizioniMax = nPosizioniMax;
    }
    */

    public Settore(int id) {
        this.id = id;
        this.posizioni = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            this.posizioni.add(new Posizione(i));
        }
        // Inizializzazione sensori e attuatori
        this.sensori = List.of(new Fotosensore(id), new Termometro(id), new IgrometroAria(id));
        this.attuatori = List.of(new Climatizzazione(id), new Lampada(id));
    }

    public Settore(int id, ArrayList<Posizione> posizioni, Termometro t,
                   IgrometroAria igA, Fotosensore fs,Climatizzazione cl, Lampada lampada) {
        this(id);
        this.posizioni = posizioni;//add controllo numero posizioni?
        this.sensori = List.of(t, igA, fs);
        this.attuatori = List.of(cl, lampada);
        this.associazioneSensoreAttuatore = new HashMap<>();
        associazioneSensoreAttuatore.put(t, cl);  // Termometro collegato al climatizzatore
        associazioneSensoreAttuatore.put(igA, cl); // Igrometro collegato al climatizzatore
        associazioneSensoreAttuatore.put(fs, lampada); // Fotosensore collegato alla lampada
    }

    private boolean fuoriRange(String tipoSensore, float misura) {
        // Definizione dei limiti per ogni tipo di sensore
        Map<String, Range<Float>> rangeAccettabili = new HashMap<>();
        rangeAccettabili.put("Termometro", new Range<>(14.0f, 30.0f));  // Range: 14°C - 30°C
        rangeAccettabili.put("IgrometroAria", new Range<>(35.0f, 85.0f));  // Range: 35% - 85% di umidità
        rangeAccettabili.put("Fotosensore", new Range<>(300.0f, 3000.0f));  // Range: 300 - 3000 lux

        // Controllo se il tipo di sensore esiste nei limiti predefiniti
        if (rangeAccettabili.containsKey(tipoSensore)) {
            // Cast della misura a Float (o il tipo corretto che usi)
            Range<Float> range = rangeAccettabili.get(tipoSensore);

            // Verifica se la misura è fuori dal range
            if (misura < range.getMin() || misura > range.getMax()) {
                return true;  // Fuori dal range
            }
        }
        return false;  // Nel range
    }

    public Climatizzazione getClimatizzazione() {
        Climatizzazione cl = null;
        for( Attuatore a : attuatori) {
            if(a.tipoAttuatore().equals("Climatizzazione")){
                cl = (Climatizzazione) a;
            }
        }
        return cl;
    }
    public Lampada getLampada() {
        Lampada lampada = null;
        for(Attuatore a : attuatori) {
            if(a.tipoAttuatore().equals("Lampada")){
                lampada =  (Lampada) a;
            }
        }
        return lampada;
    }
    public Fotosensore getFotosensore() {
        Fotosensore fotosensore = null;
        for(Sensore<?> s : sensori) {
            if(Objects.equals(s.tipoSensore(), "Fotosensore")){
                fotosensore =  (Fotosensore) s;
            }
        }
        return fotosensore;
    }

    public IgrometroAria getIgrometroAria() {
        IgrometroAria igA = null;
        for(Sensore<?> s : sensori) {
            if(Objects.equals(s.tipoSensore(), "IgrometroAria")){
                igA = (IgrometroAria) s;
            }
        }
        return igA;
    }
    public Termometro getTermometro() {
        Termometro termometro = null;
        for(Sensore<?> s : sensori) {
            if(Objects.equals(s.tipoSensore(), "Termometro")){
                termometro =  (Termometro) s;
            }
        }
        return termometro;
    }

    public void monitora(LocalDateTime lt) {
        Map<String, Object> misure = new HashMap<>();
        for (Sensore s : sensori) {
            Attuatore attuatoreAssociato = associazioneSensoreAttuatore.get(s);

            // Ottieni il valore della misura
            Object misuraVal = s.misura(lt, attuatoreAssociato.working);
            float v;

            // Verifica se il valore della misura è Integer o Float e convertilo correttamente
            if (misuraVal instanceof Integer) {
                v = ((Integer) misuraVal).floatValue();  // Converte Integer in float
            } else if (misuraVal instanceof Float) {
                v = (Float) misuraVal;
            } else {
                throw new IllegalArgumentException("Tipo di misura non supportato: " + misuraVal.getClass());
            }

            // Verifica se il valore è fuori dal range e chiama l'attuatore
            if (fuoriRange(s.tipoSensore(), v)) {
                attuatoreAssociato.esegui(1); // Fuori range, attivatore esegue azione
            } else {
                attuatoreAssociato.esegui(0); // Non fuori range, attivatore non esegue azione
            }
        }
    }

    public void setPosizioni(ArrayList<Posizione> posizioni) {
        this.posizioni = posizioni;
    }

    public int getId() {
        return id;
    }

    public List<Sensore> getSensori() {
        return sensori;
    }

    public List<Attuatore> getAttuatori() {
        return attuatori;
    }
}
