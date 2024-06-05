package main.java.DomainModel.Impianto;

public class Irrigatore extends Attuatore{
    public Irrigatore(int id) {
        super(id); // Chiama il costruttore della classe Attuatore
    }

    @Override
    public void aziona() {
        attivo = true;
    }

    @Override
    public String tipoAttuatore(){
        return "Irrigatore";
    }
}
