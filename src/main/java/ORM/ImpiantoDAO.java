package main.java.ORM;

import java.sql.Connection;
import java.sql.SQLException;

public class ImpiantoDAO {

    private Connection connection;

    public ImpiantoDAO() {

        try {
            this.connection = ConnectionManager.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    public boolean verificaDisponibilità(Ordine o) {
        try {
            // Assuming you have a table named 'impianto' in your database
            // and 'Ordine' object contains necessary information to check availability

            // Query to check the availability based on some conditions
            String query = "SELECT COUNT(*) FROM impianto WHERE condition = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Set parameters for the query based on 'Ordine' object
                // For example:
                // statement.setString(1, o.getSomeValue());

                // Execute the query
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        // Perform availability check based on the result
                        // For example:
                        // if (count > 0) {
                        //     return true; // Available
                        // } else {
                        //     return false; // Not available
                        // }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while verifying availability: " + e.getMessage());
        }
        // Return false by default or handle the case when an exception occurs
        return false;
    }

    public boolean commissionaOrdine(Ordine o){
        try {
            // Prepare SQL statement to update the order status in the database
            String query = "UPDATE ordine SET stato = 'commissionato' WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Set the parameter for the order ID
                statement.setInt(1, o.getId());

                // Execute the update
                int rowsAffected = statement.executeUpdate();

                // Check if the update was successful
                if (rowsAffected > 0) {
                    System.out.println("Order commissioned successfully.");
                    return true;
                } else {
                    System.out.println("Failed to commission order. Order not found or already commissioned.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error commissioning order: " + e.getMessage());
            return false;
        }
    }
}
