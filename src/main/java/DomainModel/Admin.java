package main.java.DomainModel;

public class Admin extends Utente {
    public Admin(int id, String nome, String cognome, String email, String password) {
        super(id, nome, cognome, email, password);
    }
    public Admin(String email, String password) {
        super(email, password);
    }
    public Admin(int id) {
        super(id);
    }
}
