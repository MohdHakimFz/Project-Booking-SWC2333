package db_objs;

import java.sql.Timestamp;

public class ReservedTable {
    // Fields to store reserved table details
    private int tableId;            // ID of the reserved table
    private Timestamp tableReservation; // Timestamp indicating the reservation time
    private int userId;             // ID of the user who reserved the table
    private int transactionId;      // ID of the transaction associated with the reservation

    // Constructors
    // Constructor with parameters to initialize reserved table details
    public ReservedTable() {
        this.tableId = tableId;
        this.tableReservation = tableReservation;
        this.userId = userId;
        this.transactionId = transactionId;
    }

    // Default constructor (not used in the provided code snippet)

    // Getters and setters for accessing and modifying reserved table details
    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public Timestamp getTableReservation() {
        return tableReservation;
    }

    public void setTableReservation(Timestamp tableReservation) {
        this.tableReservation = tableReservation;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }
}
