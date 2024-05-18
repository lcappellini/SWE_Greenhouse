package main.java.DomainModel;

public class Cliente {
    private String name;
    private String cognome;

    public Cliente(String name, String cognome) {
        this.name = name;
        this.cognome = cognome;
    }

    public String getName() {return name;}
    public String getCognome() {return cognome;}
}
