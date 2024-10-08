package test.java.BusinessLogic;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import main.java.BusinessLogic.GestioneCliente;
import main.java.DomainModel.Cliente;

import java.sql.SQLException;

public class GestioneClienteTest {

    @Test
    public void registrationTest(){
        GestioneCliente gestioneCliente = new GestioneCliente();

        String nome, cognome, email, password;
        Cliente cliente;

        //Nuovo cliente non presente nel db
        nome = "Giuseppe";
        cognome = "Verdi";
        email = "giuseppe@email.it";
        password = "123";

        System.out.println("--Test Registrazione--");

        try{
            System.out.println("1) Registrazione nuovo cliente");
            cliente = gestioneCliente.registraCliente(nome, cognome, email, password);
            assertNotNull(cliente);
            System.out.println("Rimozione cliente per futuri test");
            gestioneCliente.rimuoviCliente(email);
        }catch(SQLException | ClassNotFoundException e){
            System.err.println(e.getMessage());
        }

        //Cliente già presente nel db
        nome = "Mario";
        cognome = "Rossi";
        email = "mario@email.it";
        password = "123";

        try{
            System.out.println("2) Registrazione cliente già registrato");
            cliente = gestioneCliente.registraCliente(nome, cognome, email, password);
            assertNull(cliente);
        }catch(SQLException | ClassNotFoundException e){
            System.err.println(e.getMessage());
        }

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




