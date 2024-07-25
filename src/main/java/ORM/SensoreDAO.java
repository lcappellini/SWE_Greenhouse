package main.java.ORM;

import main.java.DomainModel.Impianto.Dispositivo;
import main.java.DomainModel.Impianto.Sensore;

import java.sql.Connection;

public abstract class SensoreDAO extends DispositivoDAO {
    private Connection connection;

    public abstract void registraMisura(Sensore sensore);
}
