package main.java.DomainModel;

public class Cliente {
    private int id;
    private String nome;
    private String cognome;
    private String email;
    private String password;

    public Cliente(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
    public Cliente( String email, String password) {

        this.email = email;
        this.password = password;
    }

    public Cliente(int id, String nome, String cognome, String email, String password) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }

    public Cliente(int id) {
        this.id = id;
    }

    public String getEmail() {return email;}
    public String getPassword() {return password;}

    public int getId() {
        return this.id;
    }
}
