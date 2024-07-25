package main.java.ORM;

import main.java.DomainModel.Impianto.Attuatore;
import main.java.DomainModel.Impianto.Dispositivo;
import main.java.DomainModel.Impianto.Sensore;

import java.sql.Connection;
import java.util.Map;

public abstract class AttuatoreDAO extends DispositivoDAO {
    private Connection connection;

    public abstract void registraAzione(Attuatore attuatore);

}
