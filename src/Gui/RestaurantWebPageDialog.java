package Gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

import db_objs.MyJDBC;
import db_objs.ReservedTable;
import db_objs.Transaction;
import db_objs.User;

public class RestaurantWebPageDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JPanel pastReserveTablePanel;
    private User user;

    private JLabel balanceLabel;
    private JLabel enterAmountLabel;
    private JTextField enterAmountField;
    private JTextField enterUserField;
    private JButton actionButton;

    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;
    private JTextField textField_6;
    private JTextField textField_7;
    private JTextField textField_8;

    public RestaurantWebPageDialog(User user) {
        this.user = user;
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(new FlowLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Add past reservation panel
        addPastReserveTableComponents();

        // Add current balance and amount fields
        addCurrentBalanceAndAmount();

        // Add action button for deposit/withdraw/transfer
        addActionButton("Deposit");

        // Button pane
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        // OK button
        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        // Cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);

        // Action listeners for buttons
        okButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }

    public void addPastReserveTableComponents() {
        pastReserveTablePanel = new JPanel();
        pastReserveTablePanel.setLayout(new BoxLayout(pastReserveTablePanel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical alignment
        JScrollPane scrollPane = new JScrollPane(pastReserveTablePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        contentPanel.add(scrollPane);

        // Retrieve past reserve tables
        ArrayList<ReservedTable> pastReserveTables = MyJDBC.getPastReserveTables(user);

        // Display past reserve tables
        for (ReservedTable reservedTable : pastReserveTables) {
            JLabel reserveLabel = new JLabel("Table ID: " + reservedTable.getTableId() +
                    ", Reserve Time: " + reservedTable.getTableReservation());
            reserveLabel.setHorizontalAlignment(SwingConstants.LEFT);
            pastReserveTablePanel.add(reserveLabel);
        }
    }

    public void addCurrentBalanceAndAmount() {
        // Balance label
        balanceLabel = new JLabel("Balance: RM" + user.getCurrentBalance());
        balanceLabel.setBounds(0, 10, getWidth() - 20, 20);
        balanceLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(balanceLabel);

        // Enter amount label
        enterAmountLabel = new JLabel("Enter Amount:");
        enterAmountLabel.setBounds(0, 50, getWidth() - 20, 20);
        enterAmountLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(enterAmountLabel);

        // Enter amount field
        enterAmountField = new JTextField();
        enterAmountField.setBounds(15, 80, getWidth() - 50, 40);
        enterAmountField.setFont(new Font("Dialog", Font.BOLD, 20));
        enterAmountField.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(enterAmountField);

        // Add user field if needed
        if (enterUserField != null) {
            enterUserField = new JTextField();
            enterUserField.setBounds(15, 120, getWidth() - 50, 40);
            enterUserField.setFont(new Font("Dialog", Font.BOLD, 20));
            enterUserField.setHorizontalAlignment(SwingConstants.CENTER);
            contentPanel.add(enterUserField);
        }
    }

    public void addActionButton(String actionButtonType) {
        actionButton = new JButton(actionButtonType);
        actionButton.setBounds(15, 300, getWidth() - 50, 40);
        actionButton.setFont(new Font("Dialog", Font.BOLD, 20));
        actionButton.addActionListener(this);
        contentPanel.add(actionButton);
    }

    private void handleTransaction(String transactionType, BigDecimal amountVal) {
        Transaction transaction;

        if (transactionType.equalsIgnoreCase("Deposit")) {
            // Deposit transaction type
            // Add to current balance
            user.setCurrentBalance(user.getCurrentBalance().add(amountVal));

            // Create transaction
            // We leave date and time null because we are going to be using the NOW() in SQL which will get the current date and time
            transaction = new Transaction(user.getId(), transactionType, amountVal, null, null);
        } else {
            // Withdraw transaction type
            // Subtract from current balance
            user.setCurrentBalance(user.getCurrentBalance().subtract(amountVal));

            // We want to show a negative sign for the amount val when withdrawing
            transaction = new Transaction(user.getId(), transactionType, amountVal.negate(), null, null);
        }
     // Update database
        int transactionId = MyJDBC.addTransactionToDatabase(transaction);
        if (transactionId != -1 && MyJDBC.updateCurrentBalance(user)) {
            // Show success dialog
            JOptionPane.showMessageDialog(this, transactionType + " Successfully!");

            // Reset the fields
            resetFieldsAndUpdateCurrentBalance();
        } else {
            // Show failure dialog
            JOptionPane.showMessageDialog(this, transactionType + " Failed...");
        }
    }
    private void resetFieldsAndUpdateCurrentBalance() {
        // Reset fields
        enterAmountField.setText("");

        // Only appear when transfer is clicked
        if (enterUserField != null) {
            enterUserField.setText("");
        }

        // Update current balance on dialog
        balanceLabel.setText("Balance: RM" + user.getCurrentBalance());
    }

    private void handleTransfer(User user, String transferredUser, BigDecimal amountVal) {
        // Attempt to perform transfer
        if (MyJDBC.transfer(user, transferredUser, amountVal)) {
            // Show success dialog
            JOptionPane.showMessageDialog(this, "Transfer Success!");
            resetFieldsAndUpdateCurrentBalance();

        } else {
            // Show failure dialog
            JOptionPane.showMessageDialog(this, "Transfer Failed...");
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();

        // Get amount value
        BigDecimal amountVal = new BigDecimal(enterAmountField.getText());

        // Pressed deposit
        if (buttonPressed.equalsIgnoreCase("Deposit")) {
            // We want to handle the deposit transaction
            handleTransaction(buttonPressed, amountVal);
        } else {
            // Pressed withdraw or transfer

            // Validate input by making sure that withdraw or transfer amount is less than current balance
            // If result is -1 it means that the entered mount is more, 0 means they are equal, and 1 means that
            // the entered amount is less
            int result = user.getCurrentBalance().compareTo(amountVal);
            if (result < 0) {
                // Display error dialog
                JOptionPane.showMessageDialog(this, "Error: Input value is more than current balance");
                return;
            }

            // Check to see if withdraw or transfer was pressed
            if (buttonPressed.equalsIgnoreCase("Withdraw")) {
                handleTransaction(buttonPressed, amountVal);
            } else {
                // Transfer
                String transferredUser = enterUserField.getText();

                // Handle transfer
                handleTransfer(user, transferredUser, amountVal);
            }
        }
    }

    public void updateReceipt(String name, String tableNumber, String paymentMethod, String totalPayment, String reserveDate, String reserveTime) {
        // Populate the receipt fields with the provided data
        textField_3.setText(name);
        textField_4.setText(tableNumber);
        textField_5.setText(paymentMethod);
        textField_6.setText(totalPayment);
        textField_7.setText(reserveDate);
        textField_8.setText(reserveTime);
    }
}
