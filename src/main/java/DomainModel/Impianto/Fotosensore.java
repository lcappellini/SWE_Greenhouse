package main.java.DomainModel.Impianto;

import java.time.LocalDateTime;
import java.util.Map;

public class Fotosensore extends Sensore {

    public Fotosensore(int id) {
        super(id); // Chiama il costruttore della classe Sensore
    }

    @Override
    public float misura(LocalDateTime lt, boolean attuatore_acceso) {
        //FIXME
        this.valore = 200 +(float) Math.random() * 800;
        return 0;
    }

    public String tipoSensore(){
        return "Fotosensore";
    }
}
