package main.java.DomainModel;

public class Cliente extends Utente {
    public Cliente(int id, String nome, String cognome, String email) {
        super(id, nome, cognome, email);
    }
    public Cliente( String email, String password) {
        super(email);
    }
    public Cliente(int id) {
        super(id);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
