package test.java.DomainModel;

import main.java.DomainModel.Impianto.IgrometroTerra;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IgrometroTerraTest {
    @Test
    public void testMisura_Irr_ON(){
        System.out.println("--Test Misura IgrometroTerra (Irrigatore ON)--");
        System.out.println("Esegue 2 misure di umidità del terreno");
        System.out.println("L'umidità deve aumentare nel tempo");
        IgrometroTerra igrometroTerra = new IgrometroTerra(0, null, 10);
        LocalDateTime lt = LocalDateTime.now();
        float misura1 = igrometroTerra.misura(lt, false);
        lt = lt.plusMinutes(1);
        float misura2 = igrometroTerra.misura(lt, true);
        assertTrue(misura2 > misura1);
        System.out.println("Test superato!");
    }

    @Test
    public void testMisura_Irr_OFF(){
        System.out.println("--Test Misura IgrometroTerra (Irrigatore OFF)--");
        System.out.println("Esegue 2 misure di umidità del terreno");
        System.out.println("L'umidità deve diminuire nel tempo");
        IgrometroTerra igrometroTerra = new IgrometroTerra(0, null, 10);
        LocalDateTime lt = LocalDateTime.now();
        float misura1 = igrometroTerra.misura(lt, false);
        lt = lt.plusMinutes(1);
        float misura2 = igrometroTerra.misura(lt, false);
        assertTrue(misura2 <= misura1);
        System.out.println("Test superato!");
    }
}
