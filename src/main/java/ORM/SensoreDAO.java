package main.java.ORM;

import main.java.DomainModel.Impianto.Sensore;

import main.java.DomainModel.Impianto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.*;

public class SensoreDAO {

    private Connection connection;

    public SensoreDAO() {
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public  void registraMisura(Sensore sensore){
        //TODO ???
    };
}
