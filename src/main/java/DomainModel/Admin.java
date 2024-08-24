package main.java.DomainModel;

public class Admin {
    private int id;
    private String nome;
    private String cognome;
    private String email;
    private String password;

    public Admin(int id, String nome, String cognome, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }
    public Admin( String email, String password) {
        this.email = email;
        this.password = password;
    }


    public Admin(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public String getNome() {return nome;}
    public String getCognome() {return cognome;}

    public String getEmail() {return email;}
    public String getPassword() {return password;}


}
