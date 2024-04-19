package db_objs;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Class representing a User entity
public class User {
    
    // Member variables
    private final int id; // Unique identifier for the user
    private final String username; // Username of the user
    private final String password; // Password of the user
    private BigDecimal currentBalance; // Current balance of the user
    
    // Constructor to initialize a User object
    public User(int id, String username, String password, BigDecimal currentBalance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.currentBalance = currentBalance;
    }

    // Getter method for id
    public int getId() {
        return id;
    }

    // Getter method for username
    public String getUsername() {
        return username;
    }

    // Getter method for password
    public String getPassword() {
        return password;
    }

    // Getter method for currentBalance
    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    // Setter method for currentBalance
    public void setCurrentBalance(BigDecimal newBalance) {
        // Store new value to the second decimal place
        currentBalance = newBalance.setScale(2, RoundingMode.FLOOR);
    }
}
