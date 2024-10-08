package main.java.DomainModel.Impianto;

import java.time.LocalDateTime;

public abstract class Sensore<T>{
    protected int id;
    protected T valore;
    protected LocalDateTime data;

    public Sensore(int id) {
        this.id = id;
    }

    Sensore(int id, LocalDateTime lt){
        this.id = id;
        this.data = lt;
    }
    // Metodo astratto per generare una misura
   // public abstract void misura();

    // Getter e setter per gli attributi comuni
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public T getValore() {
        return this.valore;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }


    public abstract T misura(LocalDateTime lt, boolean attuatore_acceso);

    public abstract String tipoSensore() ;



}

