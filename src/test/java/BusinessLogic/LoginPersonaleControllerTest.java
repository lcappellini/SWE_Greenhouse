package test.java.BusinessLogic;

import main.java.BusinessLogic.LoginPersonaleController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

public class LoginPersonaleControllerTest {
    private LoginPersonaleController loginPersonaleController;

    @BeforeEach
    public void setUp(){
        loginPersonaleController = new LoginPersonaleController();
    }

    @Test
    public void loginAdmin_Success() {
        System.out.println("--Test Login Admin (Success)--");
        System.out.println("-Login Admin presente nel db con credenziali corrette");
        assertNotNull(loginPersonaleController.loginAdmin("elion", "123"));
        System.out.println("-Test superato!");
    }

    @Test
    public void loginAdmin_Fail1() {
        System.out.println("--Test Login Admin (Fail 1)--");
        System.out.println("-Login Admin presente nel db con credenziali errate");
        assertNull(loginPersonaleController.loginAdmin("elion", "abc"));
        System.out.println("-Test superato!");
    }

    @Test
    public void loginAdmin_Fail2(){
        System.out.println("--Test Login Admin (Fail 2)--");
        System.out.println("-Login Admin non presente nel db");
        assertNull(loginPersonaleController.loginAdmin("giacomo","456"));
        System.out.println("-Test superato!");
    }
    @Test
    public void loginOperatore_Success() {
        System.out.println("--Test Login Operatore (Success)--");
        System.out.println("-Login Operatore presente nel db con credenziali corrette");
        assertNotNull(loginPersonaleController.loginOperatore("ferrari@email.it", "123"));
        System.out.println("-Test superato!");
    }

    @Test
    public void loginOperatore_Fail1() {
        System.out.println("--Test Login Operatore (Fail 1)--");
        System.out.println("-Login Operatore presente nel db con credenziali errate");
        assertNull(loginPersonaleController.loginOperatore("ferrari@email.it", "abc"));
        System.out.println("-Test superato!");
    }

    @Test
    public void loginOperatore_Fail2(){
        System.out.println("--Test Login Operatore (Fail 2)--");
        System.out.println("-Login Operatore non presente nel db");
        assertNull(loginPersonaleController.loginOperatore("verdi@email.it","456"));
        System.out.println("-Test superato!");

    }

}
