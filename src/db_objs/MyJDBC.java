package db_objs;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

// Class responsible for interacting with the database
public class MyJDBC {
    
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/restaurant_db";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "MohdHakim501#";

    // Method to validate user login credentials
    public static User validateLogin(String username, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM users WHERE username = ? AND password = ?")) {
            
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("id");
                BigDecimal currentBalance = resultSet.getBigDecimal("currentBalance");

                return new User(userId, username, password, currentBalance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Method to register a new user
    public static boolean register(String username, String password) {
        try {
            if (!checkUser(username)) {
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                     PreparedStatement preparedStatement = connection.prepareStatement(
                             "INSERT INTO users(username, password, currentBalance) VALUES(?, ?, ?)")) {

                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    preparedStatement.setBigDecimal(3, new BigDecimal(0));

                    preparedStatement.executeUpdate();
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Method to transfer funds between users
    public static boolean transfer(User user, String transferredUsername, BigDecimal amountVal) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement queryUser = connection.prepareStatement(
                     "SELECT * FROM users WHERE username = ?");
             PreparedStatement updateSenderBalance = connection.prepareStatement(
                     "UPDATE users SET currentBalance = ? WHERE id = ?");
             PreparedStatement updateReceiverBalance = connection.prepareStatement(
                     "UPDATE users SET currentBalance = ? WHERE username = ?");
             PreparedStatement insertSenderTransaction = connection.prepareStatement(
                     "INSERT INTO transactions(user_id, transaction_type, transaction_amount, transaction_date, transaction_time) " +
                             "VALUES(?, ?, ?, NOW(), NOW())");
             PreparedStatement insertReceiverTransaction = connection.prepareStatement(
                     "INSERT INTO transactions(user_id, transaction_type, transaction_amount, transaction_date, transaction_time) " +
                             "VALUES(?, ?, ?, NOW(), NOW())")) {

            // Query the user to whom the funds will be transferred
            queryUser.setString(1, transferredUsername);
            ResultSet resultSet = queryUser.executeQuery();

            if (resultSet.next()) {
                // Get the transferred user's information
                int transferredUserId = resultSet.getInt("id");
                BigDecimal transferredUserBalance = resultSet.getBigDecimal("currentBalance");

                // Calculate the new balances for both users
                BigDecimal newSenderBalance = user.getCurrentBalance().subtract(amountVal);
                BigDecimal newReceiverBalance = transferredUserBalance.add(amountVal);

                // Update the balances in the database
                updateSenderBalance.setBigDecimal(1, newSenderBalance);
                updateSenderBalance.setInt(2, user.getId());
                updateSenderBalance.executeUpdate();

                updateReceiverBalance.setBigDecimal(1, newReceiverBalance);
                updateReceiverBalance.setString(2, transferredUsername);
                updateReceiverBalance.executeUpdate();

                // Add transaction records for both users
                insertSenderTransaction.setInt(1, user.getId());
                insertSenderTransaction.setString(2, "Transfer");
                insertSenderTransaction.setBigDecimal(3, BigDecimal.valueOf(-amountVal.doubleValue()));
                insertSenderTransaction.executeUpdate();

                insertReceiverTransaction.setInt(1, transferredUserId);
                insertReceiverTransaction.setString(2, "Transfer");
                insertReceiverTransaction.setBigDecimal(3, BigDecimal.valueOf(amountVal.doubleValue()));
                insertReceiverTransaction.executeUpdate();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Method to check if a username already exists
    private static boolean checkUser(String username) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM users WHERE username = ?")) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // Method to add a transaction record to the database
    public static int addTransactionToDatabase(Transaction transaction) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement insertTransaction = connection.prepareStatement(
                     "INSERT INTO transactions(user_id, transaction_type, transaction_amount, transaction_date, transaction_time) " +
                             "VALUES(?, ?, ?, NOW(), NOW())", PreparedStatement.RETURN_GENERATED_KEYS)) {

            insertTransaction.setInt(1, transaction.getUserId());
            insertTransaction.setString(2, transaction.getTransactionType());
            insertTransaction.setBigDecimal(3, transaction.getTransactionAmount());
            insertTransaction.executeUpdate();

            ResultSet generatedKeys = insertTransaction.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Get the generated transaction ID
                int transactionId = generatedKeys.getInt(1);
                return transactionId;
            } else {
                return -1; // Return -1 if no keys were generated
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Return -1 if insertion failed
        }
    }

    // Method to update the current balance of a user in the database
    public static boolean updateCurrentBalance(User user) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE users SET currentBalance = ? WHERE id = ?")) {

            preparedStatement.setBigDecimal(1, user.getCurrentBalance());
            preparedStatement.setInt(2, user.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // Method to reserve a table in the database
    public static boolean reserveTable(ReservedTable reservedTable) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO reserved_tables (table_id, table_reservation) VALUES (?, ?)")) {

            preparedStatement.setInt(1, reservedTable.getTableId());
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis())); // Set current timestamp

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if at least one row was inserted

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to retrieve past reserved tables for a user from the database
    public static ArrayList<ReservedTable> getPastReserveTables(User user) {
        ArrayList<ReservedTable> pastReserveTables = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM reserved_tables WHERE user_id = ?");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            preparedStatement.setInt(1, user.getId());

            while (resultSet.next()) {
                ReservedTable reservedTable = new ReservedTable(
                );
                pastReserveTables.add(reservedTable);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pastReserveTables;
    }
    
    // Method to retrieve menu items from the database
    public static ArrayList<String> getMenuItems() {
        ArrayList<String> menuItems = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT menu_name, menu_price FROM menu")) {

            while (resultSet.next()) {
                String itemName = resultSet.getString("menu_name");
                double itemPrice = resultSet.getDouble("menu_price");
                String menuItem = itemName + " - RM " + String.format("%.2f", itemPrice);
                menuItems.add(menuItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuItems;
    }
}
