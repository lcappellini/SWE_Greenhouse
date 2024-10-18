package main.java.DomainModel.Impianto;

import main.java.DomainModel.Pianta.Pianta;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Posizione {
    private int id;
    private Irrigatore irrigatore;
    private IgrometroTerra igrometroTerra;
    private boolean assegnata;
    private boolean occupata;

    public Posizione(int id, Irrigatore irrigatore,
                     IgrometroTerra igrometroTerra, boolean assegnata,
                     boolean occupata) {
        this.id = id;
        this.irrigatore = irrigatore;
        this.igrometroTerra = igrometroTerra;
        this.assegnata = assegnata;
        this.occupata = occupata;
    }
    public Posizione(int id) {
        this.id = id;
    }

    //getters
    public int getId() { return id; }
    public Irrigatore getIrrigatore() { return irrigatore; }
    public IgrometroTerra getIgrometroTerra() { return igrometroTerra; }
    public boolean isAssegnata() { return assegnata; }
    public boolean isOccupata() { return occupata; }

}
