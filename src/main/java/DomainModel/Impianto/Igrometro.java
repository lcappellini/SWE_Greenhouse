package main.java.DomainModel.Impianto;

import java.time.LocalDateTime;

public abstract class Igrometro extends Sensore<Integer> {
    public Igrometro(int id) {
        super(id); // Chiama il costruttore della classe Sensore
    }

    @Override
    public abstract Integer misura(LocalDateTime lt, boolean attuatore_acceso);
}
