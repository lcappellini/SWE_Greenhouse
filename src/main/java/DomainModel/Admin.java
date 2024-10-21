package main.java.DomainModel;

public class Admin extends Utente {
    public Admin(int id, String nome, String cognome, String email) {
        super(id, nome, cognome, email);
    }
    public Admin(String email) {
        super(email);
    }
    public Admin(int id) {
        super(id);
    }
}
