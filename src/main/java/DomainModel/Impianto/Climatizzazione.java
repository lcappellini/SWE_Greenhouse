package main.java.DomainModel.Impianto;

public class Climatizzazione extends Attuatore{
    public Climatizzazione(int id) {
        super(id); // Chiama il costruttore della classe Attuatore
    }

    @Override
    public void aziona() {
        attivo = true;
    }

    @Override
    public String tipoAttuatore() {
        return "Climatizzazione";
    }
}
