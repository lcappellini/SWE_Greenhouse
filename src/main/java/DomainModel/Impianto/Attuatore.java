package main.java.DomainModel.Impianto;

public abstract class Attuatore extends Dispositivo{
    protected int id;
    protected boolean attivo = false;

    // Costruttore della classe Attuatore
    public Attuatore(int id) {
        this.id = id;
    }

    public void aziona() {

    }
    public void spegni(){
        this.attivo = false;
    }

    public int getId() {
        return id;
    }
    public abstract String esegui(int value);
    public abstract String tipoAttuatore();

    public boolean attivo() {
        return attivo;
    }
}
