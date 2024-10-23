package main.java.DomainModel.Impianto;

import java.util.HashMap;
import java.util.Map;

public class Posizione {
    private int id;
    private Irrigatore irrigatore;
    private IgrometroTerra igrometroTerra;
    private boolean assegnata;
    private boolean occupata;
    private Map<String, float[]> rangeAccettabili;

    public Posizione(int id, boolean assegnata, boolean occupata, IgrometroTerra igrometroTerra, Irrigatore irrigatore) {
        this(id);
        this.assegnata = assegnata;
        this.occupata = occupata;
        this.igrometroTerra = igrometroTerra;
        this.irrigatore = irrigatore;

    }
    public Posizione(int id) {
        this.id = id;
        this.rangeAccettabili = new HashMap<String, float[]>();
        this.rangeAccettabili.put("IgrometroTerra", new float[]{40.0f, 60.0f});  // Range:
    }

    //getters
    public int getId() { return id; }
    public Irrigatore getIrrigatore() { return irrigatore; }
    public IgrometroTerra getIgrometroTerra() { return igrometroTerra; }

    public boolean isSensorValueInRange(String tipoSensore, float misura) {
        if (rangeAccettabili.containsKey(tipoSensore)) {
            float[] range = rangeAccettabili.get(tipoSensore);
            return misura >= range[0] && misura <= range[1];
        }
        return true;
    }
}
