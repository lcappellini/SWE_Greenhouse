package test.java.BusinessLogic;

import main.java.BusinessLogic.LoginClienteController;
//import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import main.java.BusinessLogic.GestioneCliente;
import main.java.DomainModel.Cliente;

import java.sql.SQLException;

public class GestioneClienteTest {

    @Test
    public void registrationTest(){
        LoginClienteController loginClienteController = new LoginClienteController();

        String nome, cognome, email, password;
        boolean result;

        //Nuovo cliente non presente nel db
        nome = "Giuseppe";
        cognome = "Verdi";
        email = "giuseppe@email.it";
        password = "123";

        System.out.println("--Test Registrazione--");

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
    public void loginTest() throws SQLException, ClassNotFoundException{
        GestioneCliente gestioneCliente = new GestioneCliente();

        String email, password;
        Cliente cliente;

        //Cliente presente nel db
        email = "mario@email.it";
        password = "123";

        System.out.println("--Test Login--");
        try {
            System.out.println("1) Login cliente presente nel db con credenziali corrette");
            cliente = gestioneCliente.accedi(email, password);
            assertNotNull(cliente);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

        //Cliente non presente nel db o credenziali non corrette
        password = "1234";
        try {
            System.out.println("2) Login cliente presente nel db con credenziali errate");
            cliente = gestioneCliente.accedi(email, password);
            assertNull(cliente);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

        email = "luigi.conti@gmail.com";
        password = "1234567890";
        try {
            System.out.println("3) Login cliente non presente nel db");
            cliente = gestioneCliente.accedi(email, password);
            assertNull(cliente);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}




