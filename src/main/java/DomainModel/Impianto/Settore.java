package main.java.DomainModel.Impianto;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Settore {

    private int id;
    private int nPosizioniMax;
    private ArrayList<Posizione> posizioni;
    private List<Sensore> sensori;
    private List<Attuatore> attuatori;
    private Map<String, Float> misure;
    private Map<String, Boolean> accesi;
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



    /*private void attivaAttuatore(String tipoSensore) {
        Attuatore attuatore = null;

        switch (tipoSensore) {
            case "Termometro":
                attuatore = associazioneSensoreAttuatore.get(sensori.stream()
                        .filter(s -> s.tipoSensore().equals("Termometro"))
                        .findFirst().orElse(null));
                if (attuatore != null && attuatore instanceof Climatizzazione) {
                    Climatizzazione climatizzazione = (Climatizzazione) attuatore;
                    climatizzazione.regolaTemperatura();
                    System.out.println("Attivatore Climatizzazione regolato per la temperatura.");
                }
                break;

            case "IgrometroAria":
                attuatore = associazioneSensoreAttuatore.get(sensori.stream()
                        .filter(s -> s.tipoSensore().equals("IgrometroAria"))
                        .findFirst().orElse(null));
                if (attuatore != null && attuatore instanceof Climatizzazione) {
                    Climatizzazione climatizzazione = (Climatizzazione) attuatore;
                    climatizzazione.regolaUmidita();
                    System.out.println("Attivatore Climatizzazione regolato per l'umidità.");
                }
                break;

            case "Fotosensore":
                attuatore = associazioneSensoreAttuatore.get(sensori.stream()
                        .filter(s -> s.tipoSensore().equals("Fotosensore"))
                        .findFirst().orElse(null));
                if (attuatore != null && attuatore instanceof Lampada) {
                    Lampada lampada = (Lampada) attuatore;
                    lampada.accendi();
                    System.out.println("Lampada accesa.");
                }
                break;

            default:
                System.out.println("Tipo di sensore non riconosciuto.");
                break;
        }
    }
    private void spegniAttuatore(String tipoSensore) {
        // Logica per spegnere l'attuatore in base al tipo di sensore
        switch (tipoSensore) {
            case "Termometro":
                attuatori.get(0).spegni(); // Supponiamo che l'attuatore di temperatura sia il primo
                break;
            case "IgrometroAria":
                attuatori.get(1).spegni(); // Supponiamo che l'attuatore di umidità sia il secondo
                break;
            case "Fotosensore":
                attuatori.spegni(); // Supponiamo che il fotosensore controlli lo stesso attuatore di umidità
                break;
            // Aggiungi altri casi se necessario
        }
    }
*/
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

    public Map<Sensore, Object> getMisure(){
        Map<Sensore, Object> misure = new HashMap<>();
        for(Sensore s : sensori){
            misure.put(s, s.getValore());
        }
        return misure;
    }

    public int getnPosizioniMax() {
        return nPosizioniMax;
    }

    public ArrayList<Posizione> getPosizioni() { return posizioni; }

    public int getTemperatura(){
        //FIXME int i = termometro.chiedi();
        return 0;
    }

    public boolean eDisponibile(int nPosti){
        int i = 0;
        for(Posizione p : posizioni){
            if(!p.eAssegnata()){
                i = i + 1;
            }
        }
        return i == nPosti;
    }

    public void setPosizioni(ArrayList<Posizione> posizioni) {
        this.posizioni = posizioni;
    }

    public int getId() {
        return id;
    }



    public void misura(LocalDateTime lt){
        misure.clear();

        for( Sensore s : sensori){
            String tipoSensore = s.tipoSensore();
            //misure.put(tipoSensore ,s.misura(lt, accesi.get(tipoSensore)));
        }
    }

    public Map<String, String> aziona(){
        Map<String, String> descrizioni = new HashMap<>();
        for(Attuatore a : attuatori){
            if(a.tipoAttuatore().equals("Climatizzazione")){
                if(((misure.get("Termometro")<15.0 || misure.get("Termometro")>30.0)
                        || misure.get("IgrometroAria")<30)) {
                    if (!a.attivo()) {
                        descrizioni.put(a.tipoAttuatore(), a.esegui(1));
                        accesi.put("Climatizzazione",true);

                    }
                }else if(a.attivo()){
                    descrizioni.put(a.tipoAttuatore(),a.esegui(-1));
                    accesi.put("Climatizzazione",false);
                }

            }else if(a.tipoAttuatore().equals("Lampada")){
                if(misure.get("Fotosensore")<200 ){
                    if(!a.attivo()){
                        descrizioni.put(a.tipoAttuatore(),a.esegui(1));
                        accesi.put("Lampada",true);
                    }
                }else if(a.attivo()){
                    descrizioni.put(a.tipoAttuatore(),a.esegui(-1));
                    accesi.put("Lampada",false);
                }
            }

        }
        return descrizioni;
    }

    public Map<String, Boolean> getAttuatoriAccesi() {
        return accesi;
    }

    public List<Sensore> getSensori() {
        return sensori;
    }

    /*public Sensore getSensore(String tipoSensore) {
        Sensore sensore = null;
        for(Sensore s : sensori){
            if(s.tipoSensore().equals(tipoSensore)){
                sensore = s;
            }
        }
        return sensore;
    }*/

    public List<Attuatore> getAttuatori() {
        return attuatori;
    }
}
