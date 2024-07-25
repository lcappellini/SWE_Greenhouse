package main.java.DomainModel.Impianto;

import java.time.LocalDateTime;

public abstract class Sensore extends Dispositivo {
    protected int id;
    protected float valore;
    protected String data;

    public Sensore(int id) {
        this.id = id;
    }


    public float getValore() {
        return valore;
    }

    public int getId() {
        return id;
    }

    public abstract float misura(LocalDateTime lt, boolean attuatore_acceso);

    public abstract String tipoSensore() ;



}

