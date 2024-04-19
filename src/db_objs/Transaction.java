package db_objs;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

/*
 * Transaction entity used to store transaction data
 */
public class Transaction {
    // Fields to store transaction details
    private final int userId;              // ID of the user involved in the transaction
    private final String transactionType;  // Type of transaction (e.g., "Deposit", "Withdrawal", "Transfer")
    private final BigDecimal transactionAmount; // Amount involved in the transaction
    private final Date transactionDate;    // Date of the transaction
    private final Time transactionTime;    // Time of the transaction

    // Constructor to initialize transaction details
    public Transaction(int userId, String transactionType, BigDecimal transactionAmount, Date transactionDate, Time transactionTime) {
        this.userId = userId;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
        this.transactionTime = transactionTime;
    }

    // Getter methods to access transaction details

    public int getUserId() {
        return userId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public Time getTransactionTime() {
        return transactionTime;
    }
}
