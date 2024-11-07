package main.java.DomainModel;

import main.java.DomainModel.Impianto.Attuatore;
import main.java.DomainModel.Utente;

import java.util.HashMap;
import java.util.Map;

public class Operatore extends Attuatore {
    private String nome;
    private String cognome;
    private String email;
    protected Map<Integer, String> tipoLavoro;

    public Operatore(int id, String nome, String cognome, String email, boolean working) {
        super(id, working);
        tipoLavoro = new HashMap<>();
        tipoLavoro.put(0, "Semina delle piante");
        tipoLavoro.put(1, "Completamento Ordine");
        tipoLavoro.put(2, "Check-up piante");
        tipoLavoro.put(3, "Cura delle piante");
        tipoLavoro.put(-1, "");
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.tipoAttuatore = "Operatore";
    }

    public String esegui(int richiesta) {
        lavoro = "";
        StringBuilder descrizione = new StringBuilder("L'operatore ");
        descrizione.append(this.id);
        if(richiesta >= 0){
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
