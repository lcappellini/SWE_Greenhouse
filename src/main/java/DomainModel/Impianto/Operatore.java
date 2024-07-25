package main.java.DomainModel.Impianto;

import java.util.Map;

public class Operatore extends Attuatore {
    public Operatore(int id) {
        super(id); // Chiama il costruttore della classe Attuatore
        tipoLavoro.put(0,"posizionamento");
        tipoLavoro.put(1, "liberazione");
        tipoLavoro.put(2, "controllo");
        tipoLavoro.put(3, "cura della pianta");
        tipoLavoro.put(-1, "");
    }
    int valore_operativo;
    private boolean occupato = false;
    Map<Integer, String> tipoLavoro;
    public void lavora(){}

    @Override
    public String tipoAttuatore() {
        return "Operatore";
    }

    public String esegui(int richiesta) {
        String descrizione = "L'operatore "+this.id;
        if(richiesta >= 0){
            descrizione += " sta eseguendo :"+tipoLavoro.get(richiesta);
            attivo = true;
        }else{
            descrizione += " ha terminato.";
            attivo = false;
        }
        return descrizione;
    }
}
