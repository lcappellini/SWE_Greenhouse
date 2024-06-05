package main.java.DomainModel.Impianto;

import java.time.LocalDateTime;
import java.util.Map;

public class IgrometroAria extends Igrometro {
    public IgrometroAria(int id) {
        super(id); // Chiama il costruttore della classe Sensore
    }

    @Override
    public float misura(LocalDateTime lt, boolean attuatore_acceso) {
        this.valore = 30 + (float)Math.random() * 70;
        return valore;
    }

    public String tipoSensore(){
        return "IgrometroAria";
    }
}