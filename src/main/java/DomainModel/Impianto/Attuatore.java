package main.java.DomainModel.Impianto;

import java.util.Map;

public abstract class Attuatore extends Dispositivo{
    protected int id;
    protected boolean attivo = false;
    // Costruttore della classe Attuatore
    public Attuatore(int id) {
        this.id = id;
    }

    public void spegni(){
        this.attivo = false;
    }

    public int getId() {
        return id;
    }
    public String esegui(int value){
        if(value >= 0){
            attivo = true;
            return this.tipoAttuatore()+" "+this.id+" accesso.";
        }else{
            attivo = false;
            return this.tipoAttuatore()+" "+this.id+" spento.";
        }

    };
    public abstract String tipoAttuatore();

    public boolean attivo() {
        return attivo;
    }
}
