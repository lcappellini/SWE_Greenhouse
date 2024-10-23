package main.java.DomainModel;

public class Cliente extends Utente {
    public Cliente(int id, String nome, String cognome, String email) {
        super(id, nome, cognome, email);
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
