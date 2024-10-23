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
    public void loginAdmin_Success(){
       assertNotNull(loginPersonaleController.loginAdmin("elion","123"));
    }
    @Test
    public void loginAdmin_Fail(){
       assertNull(loginPersonaleController.loginAdmin("elion","1"));
    }
    @Test
    public void loginOperatore_Success(){
        assertNotNull(loginPersonaleController.loginOperatore("ferrari@email.it","123"));
    }
    @Test
    public void loginOperatore_Fail(){
        assertNull(loginPersonaleController.loginOperatore("ferrari@email.it","1"));

    }

}
