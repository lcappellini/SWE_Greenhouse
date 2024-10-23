package main.java.DomainModel.Impianto;

import java.time.LocalDateTime;

public abstract class Sensore{
    protected int id;
    protected Float valore;
    protected LocalDateTime data;

    public Sensore(int id, LocalDateTime data, float valore) {
        this.id = id;
        this.data = data;
        this.valore = valore;
    }

    Sensore(int id){
        this.id = id;
    }

    // Getter e setter per gli attributi comuni
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getValore() {
        /*String str = String.format("%.1f", this.valore).replace(",", ".");;
        return Float.parseFloat(str);*/
        return this.valore;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }


    public abstract float misura(LocalDateTime lt, boolean attuatore_acceso);

    public abstract String getTipoSensore() ;



}

