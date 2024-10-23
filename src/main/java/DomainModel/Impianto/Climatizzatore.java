package main.java.DomainModel.Impianto;

public class Climatizzatore extends Attuatore{
    private int temperatura;

    public Climatizzatore(int id){
        super(id);
        working = false;
    }
    public Climatizzatore(int id, boolean working) {
        super(id, working); // Chiama il costruttore della classe Attuatore
    }

    public Climatizzatore(int id, boolean working, int temperatura) {
        super(id, working);
        this.temperatura = temperatura;
    }

    // Getter e setter per temperatura
    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }


    @Override
    public String esegui(int i){
        StringBuilder descrizione =  new StringBuilder("La climatizzazione ").append(this.id);
        if(i>0){
            temperatura = 0;
            descrizione.append(" è accesa.");
            working = true;
        }else{
            descrizione.append(" è spenta.");
            working = false;
        }
        return descrizione.toString();
    }


    @Override
    public String tipoAttuatore() {
        return "Climatizzatore";
    }
}
