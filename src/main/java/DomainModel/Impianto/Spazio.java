package main.java.DomainModel.Impianto;


import java.util.ArrayList;
import java.util.List;

public class Spazio {
    private int id;
    //private String nome;
    private List<Settore> settori;

    public Spazio(int id) {
        this.id = id;
        //this.nome = nome;
        settori = new ArrayList<>();
        for(int i = 0; i<4 ; i++){
            settori.add(new Settore(i));
        }
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

}
