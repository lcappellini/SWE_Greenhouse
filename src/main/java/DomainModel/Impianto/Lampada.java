package main.java.DomainModel.Impianto;

public class Lampada extends Attuatore{

    private int limite_luce = 400;
    //TODO si potrebbe implementare un diverso limite per tipologia di pianta...

    public Lampada(int id) {
        super(id); // Chiama il costruttore della classe Attuatore
    }

    @Override
    public void aziona() {
        attivo = true;
    }

    @Override
    public String tipoAttuatore(){
        return "Lampada";
    }
}
