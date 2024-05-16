

package main.java.;
import main.java.DomainModel.Ordine;
import main.java.DomainModel.Impianto.Spazio;

import java.util.ArrayList;

public class Ambiente {
    private ArrayList<Spazio> spazi;
    private ArrayList<Ambiente> ambienti;

    public Ambiente() {}

    public void aggiungiSpazio(Spazio spazio) { this.spazi.add(spazio); }
    //public void aggingiAree(ArrayList<Area> a){ aree.addAll(a); }
    public void aggiungiAmbiente(Ambiente ambiente) { this.ambienti.add(ambiente); }

    public boolean creaPosizionamento(Ordine c){
        for(Ambiente ambiente : ambienti){
            for(Spazio spazio : ambiente.spazi){
                if(spazio.eDisponibile(c.getnPiante())){
                    return true;
                }
            }
        }
        return false;
    }

}
