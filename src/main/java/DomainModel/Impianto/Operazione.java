package main.java.DomainModel.Impianto;


import java.time.LocalDateTime;

public class Operazione {
    //private int id;
    private Attuatore attuatore;
    private LocalDateTime data;


    public Operazione(Attuatore attuatore, LocalDateTime data) {
        this.attuatore = attuatore;
        this.data = data;
    }


}
