package main.java.DomainModel.Impianto;

public class Lampada extends Attuatore{

    private int limite_luce = 400;
    //TODO si potrebbe implementare un diverso limite per tipologia di pianta...

    public Lampada(int id) {
        super(id); // Chiama il costruttore della classe Attuatore
    }

    @Override
    public String esegui(int i){
        String descrizione = "La Lampada "+this.id;
        if(i>=0){
            descrizione = descrizione+" è accesa.";
            attivo = true;
        }else{
            descrizione = descrizione+" è spenta.";
            attivo = false;
        }
        return descrizione;
    }
    @Override
    public String tipoAttuatore(){
        return "Lampada";
    }
}
