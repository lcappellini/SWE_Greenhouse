package main.java.DomainModel.Impianto;

public class Climatizzazione extends Attuatore{
    public Climatizzazione(int id) {
        super(id); // Chiama il costruttore della classe Attuatore
    }

    @Override
    public void aziona() {
        attivo = true;
    }

    public String esegui(int value){
        String descrizione = "Il climatizzatore "+this.id;
        if(value >= 0){
            descrizione += " è accesso.";
            attivo = true;
        }else{
            descrizione += " ha terminato.";
            attivo = false;
        }
        return descrizione;
    }


    @Override
    public String tipoAttuatore() {
        return "Climatizzazione";
    }
}
