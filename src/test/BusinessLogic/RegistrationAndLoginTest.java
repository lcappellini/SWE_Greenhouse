package test.BusinessLogic;

//import org.junit.jupiter.api.Test; FIXME NOT FOUND

import main.java.BuissnessLogic.GestioneCliente;
import main.java.DomainModel.Cliente;

public class RegistrationAndLoginTest {

    //@Test TODO ADD THIS (?)
    void registrationTest(){
        GestioneCliente gestioneCliente = new GestioneCliente();

        String nome, cognome, email, password;
        Cliente cliente;

        //Nuovo cliente non presente nel db
        nome = "Giuseppe";
        cognome = "Verdi";
        email = "giuseppe.verdi@gmail.com";
        password = "0987654321";

        cliente = gestioneCliente.registraCliente(nome, cognome, email, password);
        if (cliente != null){
            System.out.println("registrationTest: Test 1 passed");
        } else {
            System.err.println("registrationTest: Test 1 failed");
        }

        //Cliente già presente nel db
        nome = "Mario";
        cognome = "Rossi";
        email = "mario.rossi@gmail.com";
        password = "1234567890";

        cliente = gestioneCliente.registraCliente(nome, cognome, email, password);
        if (cliente == null){
            System.out.println("registrationTest: Test 2 passed");
        } else {
            System.err.println("registrationTest: Test 2 failed");
        }
    }

    //@Test TODO ADD THIS (?)
    void loginTest(){
        GestioneCliente gestioneCliente = new GestioneCliente();

        String email, password;
        Cliente cliente;

        //Cliente presente nel db
        email = "mario.rossi@gmail.com";
        password = "0987654321";

        cliente = gestioneCliente.accedi(email, password);
        if (cliente != null){
            System.out.println("loginTest: Test 1 passed");
        } else {
            System.err.println("loginTest: Test 1 failed");
        }

        //Cliente non presente nel db o credenziali non corrette
        email = "luigi.conti@gmail.com";
        password = "1234567890";

        cliente = gestioneCliente.accedi(email, password);
        if (cliente == null){
            System.out.println("loginTest: Test 2 passed");
        } else {
            System.err.println("loginTest: Test 2 failed");
        }
    }
}
