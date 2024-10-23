package main.java.DomainModel.Impianto;

public class Climatizzatore extends Attuatore{

    public Climatizzatore(int id, boolean working) {
        super(id, working); // Chiama il costruttore della classe Attuatore
        tipoAttuatore = "Climatizzatore";
    }

    @Override
    public String esegui(int i){
        StringBuilder descrizione =  new StringBuilder("La climatizzazione ").append(this.id);
        if(i>0){
            descrizione.append(" è accesa.");
            working = true;
        }else{
            descrizione.append(" è spenta.");
            working = false;
        }
        return descrizione.toString();
    }
}
