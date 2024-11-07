package main.java.BusinessLogic;

import main.java.ORM.AdminDAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;   

public class AdminExtraController {

    public void resetDatabase() {
        StringBuilder sql_tmp = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/sql/reset.sql"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) { sql_tmp.append(line).append("\n"); }
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file \"reset.sql\": " + e.getMessage());
            return;
        }
        String sql = sql_tmp.toString();
        AdminDAO adminDAO = new AdminDAO();
        adminDAO.resetDatabase(sql);
    }

    public void createDatabase() {
        StringBuilder sql_tmp = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/sql/schema.sql"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) { sql_tmp.append(line).append("\n"); }
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file \"schema.sql\": " + e.getMessage());
            return;
        }
        String sql = sql_tmp.toString();
        AdminDAO adminDAO = new AdminDAO();
        adminDAO.createDatabase(sql);
    }

    public void defaultDatabase() throws SQLException{
        StringBuilder sql_tmp = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/sql/default.sql"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) { sql_tmp.append(line).append("\n"); }
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file \"default.sql\": " + e.getMessage());
            return;
        }
        String sql = sql_tmp.toString();
        AdminDAO adminDAO = new AdminDAO();
        adminDAO.generateDefaultDatabase(sql);
    }

}