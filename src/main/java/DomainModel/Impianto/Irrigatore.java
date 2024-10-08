package main.java.DomainModel.Impianto;

public class Irrigatore extends Attuatore{
    private int durata;

    public Irrigatore(int id) {
        super(id);
    }
    public Irrigatore(int id, boolean working) {
        super(id, working);  // Chiama il costruttore della classe base
    }

    // Getter e setter per durata
    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
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

    @Override
    public String tipoAttuatore(){
        return "Irrigatore";
    }

}
