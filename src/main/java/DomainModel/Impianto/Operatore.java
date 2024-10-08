package main.java.DomainModel.Impianto;

import java.util.HashMap;
import java.util.Map;

public class Operatore extends Attuatore {
    protected Map<Integer, String> tipoLavoro;
    String ruolo;
    
    public Operatore(int id, boolean working, String ruolo) {
        this(id, working);
        this.ruolo = ruolo;
        tipoLavoro = new HashMap<>();
        tipoLavoro.put(0,"posizionamento");
        tipoLavoro.put(1, "liberazione");
        tipoLavoro.put(2, "controllo");
        tipoLavoro.put(3, "cura della pianta");
        tipoLavoro.put(-1, "");
    }

    public Operatore(int id, boolean working) {
        super(id, working);
        tipoLavoro = new HashMap<>();
        tipoLavoro.put(0,"posizionamento");
        tipoLavoro.put(1, "liberazione");
        tipoLavoro.put(2, "controllo");
        tipoLavoro.put(3, "cura della pianta");
        tipoLavoro.put(-1, "");

    }


    public void lavora(){}

    @Override
    public String tipoAttuatore() {
        return "Operatore";
    }

    public String esegui(int richiesta) {
        lavoro = "";
        StringBuilder descrizione = new StringBuilder("L'operatore ");
        descrizione.append(this.id);
        if(richiesta > 0){
            descrizione.append(" sta eseguendo: ");
            descrizione.append(tipoLavoro.get(richiesta));
            descrizione.append(". ");
            working = true;
        }else{
            descrizione.append(" ha terminato. ");
            working = false;
        }
        lavoro = descrizione.toString();
        return lavoro;
    }

}
