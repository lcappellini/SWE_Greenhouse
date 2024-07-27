package main.java.DomainModel.Impianto;

public class Climatizzatore extends Attuatore{
    public Climatizzatore(int id) {
        super(id); // Chiama il costruttore della classe Attuatore
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
        return "Climatizzatore";
    }
}
