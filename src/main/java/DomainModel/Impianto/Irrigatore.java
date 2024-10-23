package main.java.DomainModel.Impianto;

public class Irrigatore extends Attuatore{
    public Irrigatore(int id, boolean working) {
        super(id, working);  // Chiama il costruttore della classe base
        tipoAttuatore = "Irrigatore";
    }

    @Override
    public String esegui(int i){
        StringBuilder descrizione =  new StringBuilder("L'irrigatore ").append(getId());
        if(i>0){
            descrizione.append(" è acceso.");
            working = true;
        }else{
            descrizione.append(" è spento.");
            working = false;
        }
        return descrizione.toString();
    }
}
