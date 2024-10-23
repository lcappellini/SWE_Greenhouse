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
    String ruolo;

    public Operatore(int id, String nome, String cognome, String email, boolean working) {
        super(id, working);
        tipoLavoro = new HashMap<>();
        tipoLavoro.put(0,"posizionamento");
        tipoLavoro.put(1, "liberazione");
        tipoLavoro.put(2, "controllo");
        tipoLavoro.put(3, "cura della pianta");
        tipoLavoro.put(4, "semina delle piante");
        tipoLavoro.put(-1, "");
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    @Override
    public String tipoAttuatore() {
        return "Operatore";
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

    // inherits Utente

    public int getId() {
        return id;
    }
    public String getNome() {return nome;}
    public String getCognome() {return cognome;}
    public String getEmail() {return email;}
}
