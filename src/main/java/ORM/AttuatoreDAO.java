package main.java.ORM;

import main.java.DomainModel.Impianto.Attuatore;
import main.java.DomainModel.Impianto.Sensore;

import java.sql.Connection;

public abstract class AttuatoreDAO {
    private Connection connection;

    public abstract void registraAzione(Attuatore attuatore);

}
