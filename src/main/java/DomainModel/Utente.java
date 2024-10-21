package main.java.DomainModel;

public abstract class Utente {
    protected int id;
    protected String nome;
    protected String cognome;
    protected String email;

    public Utente(int id, String nome, String cognome, String email) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
    }
    public Utente( String email) {
        this.email = email;
    }
    public Utente(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public String getNome() {return nome;}
    public String getCognome() {return cognome;}
    public String getEmail() {return email;}
}
