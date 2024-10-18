package main.java.DomainModel;

public class Cliente extends Utente {
    public Cliente(int id, String nome, String cognome, String email, String password) {
        super(id, nome, cognome, email, password);
    }
    public Cliente( String email, String password) {
        super(email, password);
    }
    public Cliente(int id) {
        super(id);
    }
}
