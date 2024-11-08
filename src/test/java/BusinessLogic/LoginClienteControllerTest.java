package test.java.BusinessLogic;

import main.java.BusinessLogic.AdminExtraController;
import main.java.BusinessLogic.LoginClienteController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LoginClienteControllerTest {

    LoginClienteController loginClienteController;
    String nome, cognome, email, password;

    @BeforeEach
    public void setUp() {
        AdminExtraController adminExtraController = new AdminExtraController();
        try{
            adminExtraController.resetDatabase();
            adminExtraController.createDatabase();
            adminExtraController.defaultDatabase();
        }catch (SQLException e){
            e.printStackTrace();
        }

        loginClienteController = new LoginClienteController();
    }

    @Test
    public void registrationTest_Success() {
        //Nuovo cliente non presente nel db
        nome = "Giuseppe";
        cognome = "Verdi";
        email = "giuseppe@email.it";
        password = "123";
        System.out.println("--Test Registrazione Cliente (Success)--");
        System.out.println("-Registrazione nuovo cliente");
        assertTrue(loginClienteController.registrati(nome, cognome, email, password));
        System.out.println("-Test Superato!");
    }

    @Test
    public void registrationTest_Fail(){
        //Cliente già presente nel db
        nome = "Mario";
        cognome = "Rossi";
        email = "mario@email.it";
        password = "123";
        System.out.println("--Test Registrazione Cliente (Fail)--");
        System.out.println("-Registrazione cliente già registrato");
        assertFalse(loginClienteController.registrati(nome, cognome, email, password));
        System.out.println("-Test Superato!");
    }

    @Test
    public void loginTest_Success() {
        //Cliente presente nel db, credenziali corrette
        email = "mario@email.it";
        password = "123";
        System.out.println("--Test Login Cliente (Success)--");
        System.out.println("-Login cliente presente nel db con credenziali corrette");
        assertNotNull(loginClienteController.accedi(email, password));
        System.out.println("-Test Superato!");
    }

    @Test
    public void loginTest_Fail1() {
        //Cliente presente nel db, password errata
        email = "mario@email.it";
        password = "abc";
        System.out.println("--Test Login Cliente (Fail 1)--");
        System.out.println("-Login cliente presente nel db con credenziali errate");
        assertNull(loginClienteController.accedi(email, password));
        System.out.println("-Test Superato!");
    }

    @Test
    public void loginTest_Fail2() {
        //Cliente non presente nel db
        email = "luigi.conti@gmail.com";
        password = "1234567890";
        System.out.println("--Test Login Cliente (Fail 2)--");
        System.out.println("-Login cliente non presente nel db");
        assertNull(loginClienteController.accedi(email, password));
        System.out.println("-Test Superato!");
    }
}
