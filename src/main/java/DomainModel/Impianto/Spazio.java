package main.java.DomainModel.Impianto;


import java.util.ArrayList;

public class Spazio {

    private int id;
    private int nPosizioniMax;
    private ArrayList<Posizione> posizioni;
    private Climatizzazione climatizzazione;
    private Lampada lampada;
    private Fotosensore fotosensore;
    private Termometro termometro;
    private IgrometroAria igrometroAria;

    public Spazio(int id, ArrayList<Posizione> posizioni, Climatizzazione climatizzazione, Lampada lampada, Fotosensore fotosensore,
                  Termometro termometro, IgrometroAria igrometroAria) {
        this.id = id;
        this.posizioni = posizioni;
        this.climatizzazione = climatizzazione;
        this.lampada = lampada;
        this.fotosensore = fotosensore;
        this.termometro = termometro;
        this.igrometroAria = igrometroAria;
    }


    public Spazio(int idSpazio, int nPosMax) {
        this.id = id;
        this.nPosizioniMax = nPosMax;
    }



    public int getnPosizioniMax() {
        return nPosizioniMax;
    }

    public ArrayList<Posizione> getPosizioni() { return posizioni; }

    public int getTemperatura(){
        //FIXME int i = termometro.chiedi();
        return 0;
    }

    public boolean eDisponibile(int nPosti){
        int i = 0;
        for(Posizione p : posizioni){
            if(!p.eAssegnata()){
                i = i + 1;
            }
        }
        return i == nPosti;
    }

    public void setPosizioni(ArrayList<Posizione> posizioni) {
        this.posizioni = posizioni;
    }

    public int getId() {
        return id;
    }
}
