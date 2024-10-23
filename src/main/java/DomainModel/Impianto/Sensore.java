package main.java.DomainModel.Impianto;

import java.time.LocalDateTime;

public abstract class Sensore{
    protected int id;
    protected Float valore;
    protected LocalDateTime data;

    Sensore(int id){
        this.id = id;
    }

    // Getter e setter per gli attributi comuni
    public int getId() {
        return id;
    }

    public float getValore() {
        return this.valore;
    }

    public LocalDateTime getData() {
        return data;
    }

    public abstract float misura(LocalDateTime lt, boolean attuatore_acceso);

    public abstract String getTipoSensore() ;



}

