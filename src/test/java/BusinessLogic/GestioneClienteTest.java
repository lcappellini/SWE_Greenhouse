package test.java.BusinessLogic;

import main.java.BusinessLogic.LoginClienteController;
//import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import main.java.DomainModel.Cliente;
import org.junit.jupiter.api.Test;

public class GestioneClienteTest {

    @Test
    public void registrationTest(){
        LoginClienteController loginClienteController = new LoginClienteController();

        String nome, cognome, email, password;
        boolean result;

        System.out.println("--Test Registrazione--");

        //Nuovo cliente non presente nel db
        nome = "Giuseppe";
        cognome = "Verdi";
        email = "giuseppe@email.it";
        password = "123";
        System.out.println("1) Registrazione nuovo cliente");
        result = loginClienteController.registrati(nome, cognome, email, password);
        assertTrue(result);
        System.out.println("Rimozione cliente per futuri test");//loginClienteController.rimuovi(email);

        //Cliente già presente nel db
        nome = "Mario";
        cognome = "Rossi";
        email = "mario@email.it";
        password = "123";
        System.out.println("2) Registrazione cliente già registrato");
        result = loginClienteController.registrati(nome, cognome, email, password);
        assertFalse(result);
    }

    @Test
    public void loginTest() {
        LoginClienteController loginClienteController = new LoginClienteController();

        String email, password;
        Cliente cliente;

        System.out.println("--Test Login--");

        //Cliente presente nel db, credenziali corrette
        email = "mario@email.it";
        password = "123";
        System.out.println("1) Login cliente presente nel db con credenziali corrette");
        cliente = loginClienteController.accedi(email, password);
        assertNotNull(cliente);

        //Cliente presente nel db, credenziali errate
        password = "1234";
        System.out.println("2) Login cliente presente nel db con credenziali errate");
        cliente = loginClienteController.accedi(email, password);
        assertNull(cliente);

        //Cliente non presente nel db
        email = "luigi.conti@gmail.com";
        password = "1234567890";
        System.out.println("3) Login cliente non presente nel db");
        cliente = loginClienteController.accedi(email, password);
        assertNull(cliente);
    }
}




