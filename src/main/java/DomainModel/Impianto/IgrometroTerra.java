package main.java.DomainModel.Impianto;

import java.time.LocalDateTime;
import java.util.Map;

public class IgrometroTerra extends Sensore {
    public IgrometroTerra(int id) {
        super(id); // Chiama il costruttore della classe Sensore
    }

    @Override
    public float misura(LocalDateTime lt, boolean attuatori_accesi) {
        this.valore = 30 + (float)Math.random() * 70;
        return 0;
    }

    @Override
    public String tipoSensore(){
        return "IgrometroTerra";
    }
}

