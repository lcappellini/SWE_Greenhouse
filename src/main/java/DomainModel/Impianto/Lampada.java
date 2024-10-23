package main.java.DomainModel.Impianto;

public class Lampada extends Attuatore{

    public Lampada(int id, boolean working) {
        super(id, working);
        tipoAttuatore = "Lampada";
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
}
