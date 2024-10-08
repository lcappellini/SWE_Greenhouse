package main.java.DomainModel.Impianto;

import main.java.DomainModel.Pianta.Pianta;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Posizione {

    private int id;
    private Pianta pianta;
    private Irrigatore irriqatore;
    private IgrometroTerra igrometroTerra;
    private boolean assegnata;
    private boolean occupata;

    //costruttore

    public Posizione(int id, Irrigatore irriqatore,
                     IgrometroTerra igrometroTerra, boolean assegnata,
                     boolean occupata) {
        this.id = id;
        this.irriqatore = irriqatore;
        this.igrometroTerra = igrometroTerra;
        this.assegnata = assegnata;
        this.occupata = occupata;
    }

    public void monitora(){
        Map<String, Object> misure = new HashMap<>();
        LocalDateTime lt = LocalDateTime.now();

        igrometroTerra.misura(lt, irriqatore.working);
        /*
        if(fuoriRange(igrometroTerra.tipoSensore(), misure.get(igrometroTerra.tipoSensore()))){
            irriqatore.esegui(1);
        }else if(irriqatore.working){
            irriqatore.esegui(0);
        }*/
    }

    boolean fuoriRange(String tipoSensore, Float misura){
        Map<String, Range<Float>> rangeAccettabili = new HashMap<>();
        rangeAccettabili.put("IgrometroTerra", new Range<>(14.0f, 30.0f));  // Range: 14°C - 30°C


        // Controllo se il tipo di sensore esiste nei limiti predefiniti
        if (rangeAccettabili.containsKey(tipoSensore)) {
            // Cast della misura a Float (o il tipo corretto che usi)
            Float valore = (Float) misura;
            Range<Float> range = rangeAccettabili.get(tipoSensore);

            // Verifica se la misura è fuori dal range
            if (valore < range.getMin() || valore > range.getMax()) {
                return true;  // Fuori dal range
            }
        }
        return false;
    }
    public Posizione(int id) {
        this.id = id;
    }
    //setters

    //getters
    public int getId() { return id; }
    public Irrigatore getIrriqatore() { return irriqatore; }
    public IgrometroTerra getIgrometroTerra() { return igrometroTerra; }
    public boolean eAssegnata() { return assegnata; }
    public boolean eOccupata() { return occupata; }
}
