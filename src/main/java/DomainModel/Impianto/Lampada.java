package main.java.DomainModel.Impianto;

public class Lampada extends Attuatore{
    private boolean stato;

    public Lampada(int id, boolean working, boolean stato) {
        super(id, working);  // Chiama il costruttore della classe base
        this.stato = stato;
    }
    public Lampada(int id){
        super(id);
    }

    // Getter e setter per stato
    public boolean getStato() {
        return stato;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }


    @Override
    public String esegui(int i){
        StringBuilder descrizione =  new StringBuilder("La lampada ").append(this.id);
        if(i>0){
            descrizione.append(" è accesa.");
            working = true;
        }else{
            descrizione.append(" è spenta.");
            working = false;
        }
        return descrizione.toString();
    }
    @Override
    public String tipoAttuatore(){
        return "Lampada";
    }
}
