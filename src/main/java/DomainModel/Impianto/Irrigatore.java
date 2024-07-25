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
    public String esegui(int i){
        String descrizione = "L'irrigatore "+this.id;
        if(i>=0){
            descrizione = descrizione+" è acceso.";
            attivo = true;
        }else{
            descrizione = descrizione+" è spento.";
            attivo = false;
        }
        return descrizione;
    }

    @Override
    public String tipoAttuatore(){
        return "Irrigatore";
    }
}
