package main.java.ORM;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class ObjectDAO {

    private Connection connection;

    public ObjectDAO() {
        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void visualizza(String nomeTabella, Map<String, Object> criteri) {
        StringBuilder query = new StringBuilder("SELECT * FROM \"").append(nomeTabella).append("\" ");

        if (!criteri.isEmpty()) {
            query.append("WHERE ");
            for (String key : criteri.keySet()) {
                query.append(key).append(" = ? AND ");
            }
            query.setLength(query.length() - 5);  // Rimuove l'ultimo " AND "
        }

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            int index = 1;
            for (Object value : criteri.values()) {
                statement.setObject(index++, value);
            }

            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Stampiamo l'intestazione
            System.out.println("+--------+------------+-------------------+");
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("| %-10s ", metaData.getColumnName(i));
            }
            System.out.println("|");
            System.out.println("+--------+------------+-------------------+");

            // Stampiamo le righe
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("| %-10s ", resultSet.getString(i));
                }
                System.out.println("|");
            }
            System.out.println("+--------+------------+-------------------+");

        } catch (SQLException e) {
            System.err.println("Errore durante la visualizzazione degli ordini: " + e.getMessage());
        }
    }

    public List<Integer> restituisciChiavi(String nomeTabella, Map<String, Object> criteri) {
        List<Integer> chiaviPrimarie = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT id FROM \"").append(nomeTabella).append("\" ");

        if (!criteri.isEmpty()) {
            query.append("WHERE ");
            for (String key : criteri.keySet()) {
                query.append(key).append(" = ? AND ");
            }
            query.setLength(query.length() - 5);  // Rimuove l'ultimo " AND "
        }

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            int index = 1;
            for (Object value : criteri.values()) {
                statement.setObject(index++, value);
            }

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                chiaviPrimarie.add(resultSet.getInt("id"));
            }

        } catch (SQLException e) {
            System.err.println("Errore durante l'esecuzione della query: " + e.getMessage());
        }

        return chiaviPrimarie;
    }

    public void inserisci(Object object) {
        String nomeTabella = object.getClass().getSimpleName();
        Field[] campi = object.getClass().getDeclaredFields();

        StringJoiner nomiCampi = new StringJoiner(", ", "(", ")");
        StringJoiner valoriCampi = new StringJoiner(", ", "(", ")");

        for (Field campo : campi) {
            campo.setAccessible(true);
            nomiCampi.add(campo.getName());
            valoriCampi.add("?");
        }

        StringBuilder query = new StringBuilder("INSERT INTO \"").append(nomeTabella).append("\" ")
                .append(nomiCampi).append(" VALUES ").append(valoriCampi);

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            int index = 1;
            for (Field campo : campi) {
                campo.setAccessible(true);
                statement.setObject(index++, campo.get(object));
            }

            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            System.err.println("Errore durante l'inserimento dell'oggetto: " + e.getMessage());
        }
    }

    public void aggiorna(int id, String nomeTabella, Map<String, Object> criteri) {

        // Costruisci dinamicamente la query SQL
        StringBuilder query = new StringBuilder("UPDATE \"").append(nomeTabella).append("\" ").append(" SET ");

        // Aggiungi i campi da aggiornare alla query
        int count = 0;
        for (String key : criteri.keySet()) {
            if (count > 0) {
                query.append(", ");
            }
            query.append(key).append(" = ?");
            count++;
        }

        // Aggiungi la clausola WHERE con l'array di ID
        query.append(" WHERE id IN (?)");

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            // Imposta i parametri dinamicamente per i campi da aggiornare
            int paramIndex = 1;
            for (Map.Entry<String, Object> entry : criteri.entrySet()) {
                Object value = entry.getValue();
                statement.setObject(paramIndex, value); // Supporta qualsiasi tipo di dato
                paramIndex++;
            }

            // Imposta i parametro ID
            statement.setInt(paramIndex, id);


            // Esegui la query di aggiornamento
            int rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento: " + e.getMessage());
        }
    }

    public void aggiorna(Object object) {
        String nomeTabella = object.getClass().getSimpleName();
        Field[] campi = object.getClass().getDeclaredFields();

        StringJoiner setClause = new StringJoiner(", ");
        Field idField = null;
        Object idValue = null;

        for (Field campo : campi) {
            campo.setAccessible(true);
            if (campo.getName().equalsIgnoreCase("id")) {
                idField = campo;
                try {
                    idValue = campo.get(object);
                } catch (IllegalAccessException e) {
                    System.err.println("Errore durante l'accesso al campo ID: " + e.getMessage());
                }
            } else {
                setClause.add(campo.getName() + " = ?");
            }
        }

        if (idField == null || idValue == null) {
            System.err.println("Campo ID non trovato o valore ID non impostato.");
            return;
        }

        StringBuilder query = new StringBuilder("UPDATE \"").append(nomeTabella).append("\" SET ")
                .append(setClause).append(" WHERE id = ?");

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            int index = 1;
            for (Field campo : campi) {
                if (!campo.getName().equalsIgnoreCase("id")) {
                    statement.setObject(index++, campo.get(object));
                }
            }
            statement.setObject(index, idValue);

            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            System.err.println("Errore durante l'aggiornamento dell'oggetto: " + e.getMessage());
        }
    }
}
