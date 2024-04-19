package Gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EventObject;
import java.util.List;
import javax.swing.Timer;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

import db_objs.MyJDBC;
import db_objs.ReservedTable;
import db_objs.Transaction;
import db_objs.User;
import java.awt.SystemColor;
import java.util.Arrays;
import javax.swing.UIManager;

public class RestaurantWebPage extends JFrame {

    private static final long serialVersionUID = 1L;
    protected static final Transaction Transaction = null;
    private JPanel contentPane;
    private JPanel cardsPanel;
    private CardLayout cardLayout;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;
    private JTextField textField_6;
    private JTextField textField_7;
    private JTextField textField_8;
    private JTextField textField_9;
    protected AbstractButton txtDate;
    private DefaultTableModel model; 
    private JTextField currentBalanceField;
    private double totalPrice = 0.0;
    private JLabel totalLabel;
    private StringBuilder foodDetails = new StringBuilder();
    private int delay = 2000; // delay for 2 sec.
    private int index = 0;
    private List<ImageIcon> images;
    private JLabel lblNewLabel_3_1;
    private JTextField textField_10;

    public JTextField getCurrentBalanceField() {
        return currentBalanceField;
    }
     
    public static void main(String[] args) {
         EventQueue.invokeLater(new Runnable() {
             public void run() {
                 try {
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
         });
     }
 
  public RestaurantWebPage(User user) {
        
        // Setting up JFrame
        setTitle("Restaurant Sukiya Booking");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1220, 802);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Create a panel to hold the cards
        cardsPanel = new JPanel();
        cardsPanel.setBounds(294, 0, 906, 755);
        contentPane.add(cardsPanel);
        cardLayout = new CardLayout();
        cardsPanel.setLayout(cardLayout);

        // Create individual panels for each "card"
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setBackground(Color.WHITE);
        cardsPanel.add(dashboardPanel, "dashboard");
        dashboardPanel.setLayout(null);
        
        // Image label for the dashboard
        lblNewLabel_3_1 = new JLabel("");
        lblNewLabel_3_1.setBackground(new Color(255, 255, 255));
        lblNewLabel_3_1.setIcon(new ImageIcon(RestaurantWebPage.class.getResource("/img/gy1.jpg")));
        lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3_1.setBounds(56, 254, 829, 356);
        dashboardPanel.add(lblNewLabel_3_1);
        
        // Panel for the header
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(255, 102, 102));
        panel_1.setBounds(0, 0, 906, 72);
        dashboardPanel.add(panel_1);
        panel_1.setLayout(null);
        
        // Label for the restaurant name
        JLabel lblNewLabel_2 = new JLabel("Restaurant Sukiya");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setFont(new Font("Bodoni MT Black", Font.BOLD, 35));
        lblNewLabel_2.setBounds(262, 10, 360, 52);
        panel_1.add(lblNewLabel_2);
        
        // Logo for the restaurant
        JLabel lblNewLabel_3 = new JLabel("");
        lblNewLabel_3.setIcon(new ImageIcon(RestaurantWebPage.class.getResource("/img/Sukiyalogo.png")));
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3.setBounds(174, 10, 78, 52);
        panel_1.add(lblNewLabel_3);
        
        // Label for total available tables
        JLabel lblNewLabel_4 = new JLabel("Total Available Table:");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel_4.setBounds(222, 673, 145, 17);
        dashboardPanel.add(lblNewLabel_4);

        
        JLabel lblNewLabel_5 = new JLabel("");
        lblNewLabel_5.setIcon(new ImageIcon(RestaurantWebPage.class.getResource("/img/top1.jpg")));
        lblNewLabel_5.setBackground(UIManager.getColor("Button.background"));
        lblNewLabel_5.setBounds(24, 82, 861, 162);
        dashboardPanel.add(lblNewLabel_5);
       
        // Text field for displaying total available tables
        textField_10 = new JTextField();
        textField_10.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
        textField_10.setHorizontalAlignment(SwingConstants.CENTER);
        textField_10.setBounds(377, 662, 184, 35);
        dashboardPanel.add(textField_10);
        textField_10.setColumns(10);

        // Initialize total available tables
        int totalAvailableTables = 30; // Initial value, adjust as needed

        // Set initial value of text field
        textField_10.setText(String.valueOf(totalAvailableTables));
        
        // List of images for cycling
        images = Arrays.asList(
            new ImageIcon(RestaurantWebPage.class.getResource("/img/family_mini1.jpg")),
            new ImageIcon(RestaurantWebPage.class.getResource("/img/gy2.jpg")),
            new ImageIcon(RestaurantWebPage.class.getResource("/img/alacarte1.jpg")),
            new ImageIcon(RestaurantWebPage.class.getResource("/img/alacarte2.jpg")),
            new ImageIcon(RestaurantWebPage.class.getResource("/img/family_mini2.jpg"))
        );

        // Timer task to cycle through images
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // Here, we're just cycling through the list of images
                lblNewLabel_3_1.setIcon(images.get(index));
                index = (index + 1) % images.size();
            }
        };

        // Start the timer
        new Timer(delay, taskPerformer).start();

        //tablePanel
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.WHITE);
        cardsPanel.add(tablePanel, "table");
        tablePanel.setLayout(null);

        // Label for user ID
        JLabel lblUserId = new JLabel("User ID:");
        lblUserId.setFont(new Font("Tahoma", Font.BOLD, 10));
        lblUserId.setBounds(40, 72, 48, 32);
        tablePanel.add(lblUserId);
        

        // Text field for user ID
        JTextField txtUserId = new JTextField();
        txtUserId.setText("" + user.getId());
        txtUserId.setEditable(false);
        txtUserId.setBounds(96, 78, 175, 21);
        tablePanel.add(txtUserId);

        // Label for transaction ID
        JLabel lblTransactionId = new JLabel("Transaction ID:");
        lblTransactionId.setFont(new Font("Tahoma", Font.BOLD, 10));
        lblTransactionId.setBounds(322, 72, 86, 32);
        tablePanel.add(lblTransactionId);

        // Text field for transaction ID
        JTextField txtTransactionId = new JTextField();
        txtTransactionId.setBounds(420, 78, 175, 21);
        tablePanel.add(txtTransactionId);

        // Label for table reserve time
        JLabel lblTableReserveTime = new JLabel("Table Reserve Time:");
        lblTableReserveTime.setFont(new Font("Tahoma", Font.BOLD, 10));
        lblTableReserveTime.setBounds(541, 120, 114, 21);
        tablePanel.add(lblTableReserveTime);

        // Text field for username
        JTextField txtUsername = new JTextField();
        txtUsername.setText("" + user.getUsername());
        txtUsername.setFont(new Font("Tahoma", Font.BOLD, 10));
        txtUsername.setBounds(677, 78, 151, 21);
        txtUsername.setEditable(false); // Set non-editable
        tablePanel.add(txtUsername);

        // Label for table number
        JLabel lblTableNumber = new JLabel("Table Number:");
        lblTableNumber.setFont(new Font("Tahoma", Font.BOLD, 10));
        lblTableNumber.setBounds(40, 114, 86, 32);
        tablePanel.add(lblTableNumber);

        // Combo box for table number
        JComboBox<String> cmbTableNumber = new JComboBox<String>();
        cmbTableNumber.setBounds(127, 120, 120, 21);
        for (int i = 1; i <= 30; i++) {
            cmbTableNumber.addItem(String.valueOf(i));
        }
        tablePanel.add(cmbTableNumber);

        // Label for date
        JLabel lblDate = new JLabel("Date:");
        lblDate.setFont(new Font("Tahoma", Font.BOLD, 10));
        lblDate.setBounds(280, 114, 33, 32);
        tablePanel.add(lblDate);
        
        // Label for username
        JLabel Username = new JLabel("Username:");
        Username.setFont(new Font("Tahoma", Font.BOLD, 10));
        Username.setBounds(617, 82, 60, 13);
        tablePanel.add(Username);

        // Button to reserve table
        JButton btnReserveTable = new JButton("Reserve Table ");
        btnReserveTable.setBounds(218, 640, 175, 40);
        tablePanel.add(btnReserveTable);

        // Date picker for selecting date
        DatePicker datePicker = new DatePicker();
        datePicker.setBounds(318, 122, 182, 21);
        tablePanel.add(datePicker);
        
        // Time picker for selecting time
        TimePicker timePicker = new TimePicker();
        timePicker.setBounds(677, 120, 151, 21);
        tablePanel.add(timePicker);
     
        // Action listener for reserving table button
        btnReserveTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get input values
                String userId = txtUserId.getText();
                String transactionId = txtTransactionId.getText();
                String username = txtUsername.getText();
                int tableNumber = Integer.parseInt(cmbTableNumber.getSelectedItem().toString());
                
                // Validate input fields
                if (userId.isEmpty() || transactionId.isEmpty() || username.isEmpty()) {
                    JOptionPane.showMessageDialog(RestaurantWebPage.this, "Please fill in all fields.");
                    return;
                }

                // Get current time as table reserve time
                LocalTime tableReserveTime = LocalTime.now();
                
                // Validate table number selection
                if (tableNumber == 0) {
                    JOptionPane.showMessageDialog(RestaurantWebPage.this, "Please select a table number.");
                    return;
                }
                
                // Get selected date from date picker
                LocalDate date = datePicker.getDate();
                
                if (date != null) {
                    // Get selected time from time picker
                    LocalTime time = timePicker.getTime();
                    
                    if (time != null) {
                        // Both date and time are selected, create LocalDateTime
                        LocalDateTime dateTime = LocalDateTime.of(date, time);
                                     
                        // Add reservation details to table model
                        model.addRow(new Object[]{userId, transactionId, tableReserveTime, tableNumber, date, time, username});
          
                        // Create a ReservedTable object (not implemented in this code snippet)
                        ReservedTable reservedTable = new ReservedTable();
                            
                        // Attempt to reserve the table (not implemented in this code snippet)
                        boolean saved = MyJDBC.reserveTable(reservedTable);
                        
                        if (saved) {
                            JOptionPane.showMessageDialog(RestaurantWebPage.this, "Table reservation saved successfully.");
                        } else {
                            JOptionPane.showMessageDialog(RestaurantWebPage.this, "Failed to save table reservation.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(RestaurantWebPage.this, "Please select a time.");
                    }
                } else {
                    JOptionPane.showMessageDialog(RestaurantWebPage.this, "Please select a date.");
                }
            }
        });

        // Define column names and types for the table
        String[] columnNames = {"User ID", "Transaction ID", "Table Reserve Time", "Table Number", "Date", "Time", "Username"};
        Class[] columnTypes = {Integer.class, String.class, String.class, Integer.class, LocalDate.class, LocalTime.class, String.class};

        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel(new Object[][] {}, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
        };

        // Initialize model variable
        model = tableModel;

        // Add table model to table
        JTable table = new JTable(model);

        // Create custom cell editor for specific columns
        for (int i = 0; i < columnNames.length; i++) {
            if (i != 6) { // Exclude the "Username" column from being editable
                table.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(new JTextField()) {
                    @Override
                    public boolean isCellEditable(EventObject e) {
                        return false; // Make the cell not editable
                    }
                });
            }
        }

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(33, 168, 856, 425);
        tablePanel.add(scrollPane);

        // Add a filter to the table
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(tableModel);
        table.setRowSorter(rowSorter);

        // Button for sorting table data
        JButton btnSort = new JButton("Sort");
        btnSort.setBounds(352, 603, 75, 23);
        tablePanel.add(btnSort);

        // Action listener for sorting button
        btnSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get sorting column
                int sortingColumn = table.getColumnModel().getColumnIndexAtX(table.getMousePosition().x);
                
                // Get sorting order
                SortOrder sortingOrder = rowSorter.getSortKeys().isEmpty() ? SortOrder.ASCENDING : rowSorter.getSortKeys().get(0).getSortOrder();
                
                // Sort the table
                rowSorter.setSortKeys(List.of(new RowSorter.SortKey(sortingColumn, sortingOrder)));
            }
        });
        
        // Add a filtering button to the table
        JButton btnFilter = new JButton("Filter");
        btnFilter.setBounds(446, 603, 75, 23);
        tablePanel.add(btnFilter);

        // Add an event listener to the filtering button
        btnFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the filter text
                String filterText = JOptionPane.showInputDialog(null, "Enter the filter text:");
                
                // Apply the filter to the table
                rowSorter.setRowFilter(RowFilter.regexFilter(filterText));
            }
        });

        // Add a panel to the top of the table
        JPanel panel = new JPanel();
        panel.setBackground(new Color(176, 196, 222));
        panel.setBounds(0, 0, 906, 61);
        tablePanel.add(panel);
        
        // Add a label to the top of the table
        JLabel lblTableReservations = new JLabel("Table Reservations");
        lblTableReservations.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTableReservations.setBounds(355, 11, 190, 40);
        panel.add(lblTableReservations);

        // Add a border to the table
        table.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
       
       //paymentPanel 
        JPanel paymentPanel = new JPanel();
        paymentPanel.setBackground(Color.WHITE);
        cardsPanel.add(paymentPanel, "payment");
        paymentPanel.setLayout(null);
        
        JButton btnNewButton = new JButton("Proceed To Payment");
        btnNewButton.setBounds(480, 640, 175, 40);
        tablePanel.add(btnNewButton);
        // ActionListener for the "Proceed To Payment" button in tablePanel
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Set paymentPanel visible and hide tablePanel
                paymentPanel.setVisible(true);
                tablePanel.setVisible(false);
            }
        });
        
        JLabel lblNewLabel_8 = new JLabel("Deposit:\r\n");
        lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 22));
        lblNewLabel_8.setBounds(110, 515, 88, 39);
        paymentPanel.add(lblNewLabel_8);
        
        textField = new JTextField();
        textField.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setBounds(208, 520, 636, 39);
        paymentPanel.add(textField);
        textField.setColumns(10);
        
        JLabel lblNewLabel_8_2 = new JLabel("Total Payment:");
        lblNewLabel_8_2.setFont(new Font("Tahoma", Font.PLAIN, 22));
        lblNewLabel_8_2.setBounds(42, 588, 156, 39);
        paymentPanel.add(lblNewLabel_8_2);
        
        textField_1 = new JTextField();
        textField_1.setFont(new Font("Segoe UI Black", Font.PLAIN, 20));
        textField_1.setHorizontalAlignment(SwingConstants.CENTER);
        textField_1.setColumns(10);
        textField_1.setBounds(208, 593, 636, 39);
        paymentPanel.add(textField_1);
        
        JLabel lblNewLabel_8_1 = new JLabel("Current Balance:\r\n\r\n");
        lblNewLabel_8_1.setFont(new Font("Tahoma", Font.PLAIN, 22));
        lblNewLabel_8_1.setBounds(31, 449, 167, 39);
        paymentPanel.add(lblNewLabel_8_1);
        
        textField_2 = new JTextField("RM" + user.getCurrentBalance());
        textField_2.setColumns(10);
        textField_2.setBounds(208, 449, 636, 39);
        textField_2.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
        textField_2.setHorizontalAlignment(SwingConstants.CENTER);
        textField_2.setEditable(false); // Prevent From Edit Such Character.
        paymentPanel.add(textField_2);
        
        JButton btnNewButton_1 = new JButton("Pay");
        btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNewButton_1.setBounds(525, 652, 118, 33);
        paymentPanel.add(btnNewButton_1);
        
        btnNewButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Parse the payment amount entered by the user
                    String paymentText = textField_1.getText().substring(2).trim(); // Remove "RM" and trim whitespace
                    BigDecimal paymentAmount = new BigDecimal(paymentText);

                    // Get the current balance
                    BigDecimal currentBalance = user.getCurrentBalance();

                    // Check if the payment amount is greater than 0 and less than or equal to the current balance
                    if (paymentAmount.compareTo(BigDecimal.ZERO) > 0 && paymentAmount.compareTo(currentBalance) <= 0) {
                        // Subtract the payment amount from the current balance
                        BigDecimal newBalance = currentBalance.subtract(paymentAmount);

                        // Update the user's current balance locally
                        user.setCurrentBalance(newBalance);

                        // Update the display to show the new balance in textField_2
                        textField_2.setText("RM" + user.getCurrentBalance());

                        // Update the current balance in the MySQL database
                        if (MyJDBC.updateCurrentBalance(user)) {
                            JOptionPane.showMessageDialog(paymentPanel, "Payment successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            // If updating balance in database fails, rollback the local change
                            user.setCurrentBalance(currentBalance);
                            JOptionPane.showMessageDialog(paymentPanel, "Payment failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                            return; // Return without proceeding to populate receipt if payment failed
                        }
                        
                        int tableNumber = Integer.parseInt(cmbTableNumber.getSelectedItem().toString());
                        String transactionId = txtTransactionId.getText();
                     // Populate the receipt fields with data from tablePanel
                        textField_3.setText(user.getUsername());
                        textField_4.setText(""+ tableNumber);
                        textField_5.setText(""+transactionId); // Assuming lblTransactionId contains the transaction ID
                        textField_6.setText(String.valueOf(totalPrice)); // Assuming totalPrice is the total payment
                        textField_7.setText(""+datePicker); // Assuming datePicker is a string representing the reserve date
                        textField_8.setText(""+timePicker); // Assuming timePicker is a string representing the reserve time

                        // Navigate to the receiptPanel
                        CardLayout cardLayout = (CardLayout) cardsPanel.getLayout();
                        cardLayout.show(cardsPanel, "receipt");
                    } else {
                        // Display an error message indicating insufficient balance or invalid payment amount
                        JOptionPane.showMessageDialog(paymentPanel, "Invalid payment amount or insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    // Handle the case where the input is not a valid number
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(paymentPanel, "Invalid payment amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        textField_9 = new JTextField();
        textField_9.setBounds(42, 27, 842, 372);
        paymentPanel.add(textField_9);
        textField_9.setColumns(10);
        textField_9.setEditable(false);
          
        JButton OkDeposit = new JButton("Ok Deposit ");
        OkDeposit.setFont(new Font("Tahoma", Font.PLAIN, 12));
        OkDeposit.setBounds(274, 653, 136, 33);
        paymentPanel.add(OkDeposit);
        final RestaurantWebPage dialog = this; // Create a final reference to the dialog instance
        
        OkDeposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Parse the amount entered by the user
                    BigDecimal depositAmount = new BigDecimal(textField.getText());
                    
                    // Add the deposit amount to the user's current balance
                    user.setCurrentBalance(user.getCurrentBalance().add(depositAmount));
                    
                    // Update the display to show the new balance in textField_2
                    textField_2.setText("RM" + user.getCurrentBalance());
                    
                    // Update the current balance in the database
                    if (MyJDBC.updateCurrentBalance(user)) {
                        JOptionPane.showMessageDialog(dialog, "Deposit successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Failed to update current balance in database.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    // Reset the text field after the deposit is processed
                    textField.setText("");
                } catch (NumberFormatException ex) {
                    // Handle the case where the user enters invalid input (non-numeric)
                    JOptionPane.showMessageDialog(dialog, "Invalid input. Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    
        //receiptPanel
        JPanel receiptPanel = new JPanel();
        receiptPanel.setBackground(new Color(144, 238, 144));
        cardsPanel.add(receiptPanel, "receipt");
        receiptPanel.setLayout(null);
        
        JLabel lblNewLabel_7 = new JLabel("Name:");
        lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_7.setBounds(174, 244, 55, 18);
        receiptPanel.add(lblNewLabel_7);
        
        JLabel lblNewLabel_9 = new JLabel("Table:");
        lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_9.setBounds(174, 289, 55, 18);
        receiptPanel.add(lblNewLabel_9);
        
        JLabel lblNewLabel_10 = new JLabel("Reserve Time:");
        lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_10.setBounds(112, 453, 117, 32);
        receiptPanel.add(lblNewLabel_10);
        
        JLabel lblNewLabel_7_1 = new JLabel("Reserve Date:");
        lblNewLabel_7_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_7_1.setBounds(112, 411, 117, 32);
        receiptPanel.add(lblNewLabel_7_1);
        
        JLabel lblNewLabel_7_1_1 = new JLabel("Total Payment:");
        lblNewLabel_7_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_7_1_1.setBounds(111, 369, 118, 32);
        receiptPanel.add(lblNewLabel_7_1_1);
        
        JLabel lblNewLabel_7_1_1_1 = new JLabel("Transaction ID:");
        lblNewLabel_7_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_7_1_1_1.setBounds(102, 327, 141, 29);
        receiptPanel.add(lblNewLabel_7_1_1_1);
        
        textField_3 = new JTextField();
        textField_3.setEditable(false);
        textField_3.setBounds(239, 239, 509, 33);
        receiptPanel.add(textField_3);
        textField_3.setColumns(10);
        
        textField_4 = new JTextField();
        textField_4.setEditable(false);
        textField_4.setColumns(10);
        textField_4.setBounds(239, 284, 509, 33);
        receiptPanel.add(textField_4);
        
        textField_5 = new JTextField();
        textField_5.setEditable(false);
        textField_5.setColumns(10);
        textField_5.setBounds(239, 327, 509, 33);
        receiptPanel.add(textField_5);
        
        textField_6 = new JTextField();
        textField_6.setEditable(false);
        textField_6.setColumns(10);
        textField_6.setBounds(239, 368, 509, 33);
        receiptPanel.add(textField_6);
        
        textField_7 = new JTextField();
        textField_7.setEditable(false);
        textField_7.setColumns(10);
        textField_7.setBounds(239, 413, 509, 33);
        receiptPanel.add(textField_7);
        
        textField_8 = new JTextField();
        textField_8.setEditable(false);
        textField_8.setColumns(10);
        textField_8.setBounds(239, 455, 509, 33);
        receiptPanel.add(textField_8);
        
        JLabel lblNewLabel_11 = new JLabel("Receipt ");
        lblNewLabel_11.setBackground(new Color(255, 248, 220));
        lblNewLabel_11.setFont(new Font("Lucida Calligraphy", Font.BOLD, 45));
        lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_11.setBounds(102, 125, 707, 104);
        receiptPanel.add(lblNewLabel_11);
        
        JPanel panel_7 = new JPanel();
        panel_7.setBackground(new Color(255, 228, 181));
        panel_7.setBounds(85, 0, 738, 84);
        receiptPanel.add(panel_7);
        
        JPanel panel_7_1 = new JPanel();
        panel_7_1.setBackground(new Color(255, 228, 181));
        panel_7_1.setBounds(820, 0, 86, 755);
        receiptPanel.add(panel_7_1);
        
        JPanel panel_7_2 = new JPanel();
        panel_7_2.setBackground(new Color(255, 228, 181));
        panel_7_2.setBounds(85, 671, 738, 84);
        receiptPanel.add(panel_7_2);
        
        JPanel panel_7_1_1 = new JPanel();
        panel_7_1_1.setBackground(new Color(255, 228, 181));
        panel_7_1_1.setBounds(0, 0, 86, 755);
        receiptPanel.add(panel_7_1_1);
        
         JPanel panel1_1 = new JPanel();
         panel1_1.setBounds(-9, 0, 303, 776);
         contentPane.add(panel1_1);
         panel1_1.setBackground(new Color(192, 192, 192));
         panel1_1.setLayout(null);
         
         JLabel lblNewLabel = new JLabel("");
         lblNewLabel.setBounds(101, 20, 72, 72);
         panel1_1.add(lblNewLabel);
         lblNewLabel.setIcon(new ImageIcon(RestaurantWebPage.class.getResource("/img/Users.png")));
         
         JLabel lblNewLabel_1 = new JLabel("Welcome Back," + user.getUsername());
         lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
         lblNewLabel_1.setBounds(48, 104, 186, 26);
         panel1_1.add(lblNewLabel_1);
         
         JButton menuButton = new JButton("Menu");
         menuButton.setBounds(31, 233, 232, 37);
         panel1_1.add(menuButton);
         menuButton.addActionListener(e -> cardLayout.show(cardsPanel, "menu"));
           
         JButton tableButton = new JButton("Table");
         tableButton.setBounds(31, 320, 232, 37);
         panel1_1.add(tableButton);
                   tableButton.addActionListener(e -> cardLayout.show(cardsPanel, "table"));
                   
                   JButton paymentButton = new JButton("Payment");
                   paymentButton.setBounds(31, 410, 232, 37);
                   panel1_1.add(paymentButton);
                   paymentButton.addActionListener(e -> cardLayout.show(cardsPanel, "payment"));
                   
                   JButton receiptButton = new JButton("Receipt");
                   receiptButton.setBounds(31, 505, 232, 37);
                   panel1_1.add(receiptButton);
                   receiptButton.addActionListener(e -> cardLayout.show(cardsPanel, "receipt"));
                   
                   // Add buttons to switch between cards
                   JButton dashboardButton = new JButton("Dashboard");
                   dashboardButton.setBounds(31, 151, 232, 37);
                   panel1_1.add(dashboardButton);
                   dashboardButton.addActionListener(e -> cardLayout.show(cardsPanel, "dashboard"));
                           
                   JButton logOutButton = new JButton("Log Out");
                   logOutButton.setBounds(84, 581, 122, 37);
                   panel1_1.add(logOutButton);
                   logOutButton.addActionListener(e -> System.exit(0));
                   
                   //menuPanel
                   JPanel menuPanel = new JPanel();
                   menuPanel.setBackground(Color.WHITE);
                   cardsPanel.add(menuPanel, "menu");
                   menuPanel.setLayout(null);
                   
                   // Assume these are your food item buttons
                   JButton gyudonButton = new JButton("Gyudon");
                   gyudonButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   gyudonButton.setBounds(41, 55, 109, 30); // Adjust bounds as needed
                   menuPanel.add(gyudonButton);
                   // Action listener for Gyudon button
                   gyudonButton.addActionListener(new ActionListener() {
                       public void actionPerformed(ActionEvent e) {
                           // Add Gyudon price to total and update totalLabel
                           totalPrice += 9.90; // Example price
                           updateTotalLabel();
                           // Add Gyudon details to foodDetails StringBuilder
                           foodDetails.append("Gyudon: RM 9.90\n");
                           // Update textField_9 with accumulated foodDetails
                           textField_9.setText(foodDetails.toString());
                           // Update the total food payment in textField_1
                           updateTotalFoodPayment();
                       }
                   });


                   JButton alaCarteButton = new JButton("Takoyaki");
                   alaCarteButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   alaCarteButton.setBounds(323, 55, 100, 30); // Adjust bounds as needed
                   menuPanel.add(alaCarteButton);
                   // Action listener for Ala Carte button
                   alaCarteButton.addActionListener(new ActionListener() {
                       public void actionPerformed(ActionEvent e) {
                           // Add Ala Carte price to total and update totalLabel
                           totalPrice += 5.90; // Example price
                           updateTotalLabel();
                        // Add Gyudon details to foodDetails StringBuilder
                           foodDetails.append("Takoyaki: \rRM 5.90\n");
                           // Update textField_9 with accumulated foodDetails
                           textField_9.setText(foodDetails.toString());
                           // Update the total food payment in textField_1
                           updateTotalFoodPayment();
                       }
                   });
                   

                   JButton ramenNoodlesButton = new JButton("Tokyo Ramen C");
                   ramenNoodlesButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   ramenNoodlesButton.setBounds(748, 55, 119, 30); // Adjust bounds as needed
                   menuPanel.add(ramenNoodlesButton);
                   ramenNoodlesButton.addActionListener(new ActionListener() {
                     	public void actionPerformed(ActionEvent e) {
                     		 totalPrice += 11.90; // Example price
                            updateTotalLabel();
                         // Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Tokyo Ramen C: RM 11.90\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                     	}
                     });

                   JButton teriyakiButton = new JButton("Chicken Ontama");
                   teriyakiButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   teriyakiButton.setBounds(462, 55, 109, 30); // Adjust bounds as needed
                   menuPanel.add(teriyakiButton);
                   teriyakiButton.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      		 totalPrice += 14.90; // Example price
                             updateTotalLabel();
                          // Add Gyudon details to foodDetails StringBuilder
                             foodDetails.append("Chicken Ontama: RM 14.90\n");
                             // Update textField_9 with accumulated foodDetails
                             textField_9.setText(foodDetails.toString());
                             // Update the total food payment in textField_1
                             updateTotalFoodPayment();
                      	}
                      });

                   JButton familySetButton = new JButton("Family Set 1");
                   familySetButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   familySetButton.setBounds(602, 55, 100, 30); // Adjust bounds as needed
                   menuPanel.add(familySetButton);
                   familySetButton.addActionListener(new ActionListener() {
                       public void actionPerformed(ActionEvent e) {
                           totalPrice += 39.90; // Example price
                           updateTotalLabel();
                        // Add Gyudon details to foodDetails StringBuilder
                           foodDetails.append("Family Set 1: RM 39.90\n");
                           // Update textField_9 with accumulated foodDetails
                           textField_9.setText(foodDetails.toString());
                           // Update the total food payment in textField_1
                           updateTotalFoodPayment();
                       }
                   });	
                  
                   JButton drinksButton = new JButton("Coca-Cola");
                   drinksButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   drinksButton.addActionListener(new ActionListener() {
                   	public void actionPerformed(ActionEvent e) {
                   	 totalPrice += 3.50; // Example price
                     updateTotalLabel();
                     // Add Gyudon details to foodDetails StringBuilder
                     foodDetails.append("Coca-Cola: RM 3.50\n");
                     // Update textField_9 with accumulated foodDetails
                     textField_9.setText(foodDetails.toString());
                     // Update the total food payment in textField_1
                     updateTotalFoodPayment();
                   	}
                   });
                   drinksButton.setBounds(180, 55, 119, 30); // Adjust bounds as needed
                   menuPanel.add(drinksButton);

                   // Assume these are your payment details labels
                   totalLabel = new JLabel("Total: RM 0");
                   totalLabel.setFont(new Font("Serif", Font.BOLD, 15));
                   totalLabel.setBounds(693, 528, 157, 30); // Adjust bounds as needed
                   menuPanel.add(totalLabel);

                   // Buttons for payment actions
                   JButton addToPaymentButton = new JButton("Proceed To Table");
                   addToPaymentButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   addToPaymentButton.setBounds(529, 601, 205, 33);
                   menuPanel.add(addToPaymentButton);
                   addToPaymentButton.addActionListener(new ActionListener() {
                	    public void actionPerformed(ActionEvent e) {
                	        // Set tablePanel visible and hide menuPanel
                	        tablePanel.setVisible(true);
                	        menuPanel.setVisible(false);
                	    }
                	});
                

                   JButton cancelOrderButton = new JButton("Cancel Order");
                   cancelOrderButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   cancelOrderButton.setBounds(124, 601, 205, 33);
                   menuPanel.add(cancelOrderButton);
                   // Action listener for Cancel Order button
                   cancelOrderButton.addActionListener(new ActionListener() {
                       public void actionPerformed(ActionEvent e) {
                           // Perform actions when Cancel Order button is clicked
                    	   // Action listener for Cancel Order button
                    	   cancelOrderButton.addActionListener(new ActionListener() {
                    	       public void actionPerformed(ActionEvent e) {
                    	           // Reset the total price to 0
                    	           totalPrice = 0.0;
                    	           // Update the total label
                    	           updateTotalLabel();
                    	           // Clear the foodDetails StringBuilder
                    	           foodDetails.setLength(0);
                                   // Update textField_9 with accumulated foodDetails
                                   textField_9.setText(foodDetails.toString());
                                   // Update the total food payment in textField_1
                                   updateTotalFoodPayment();
                    	       }
                    	   });
                       }
                   });
                   
                   JLabel GyudonSet = new JLabel("Gyudon Set\r\n");
                   GyudonSet.setFont(new Font("Segoe UI Black", Font.BOLD, 13));
                   GyudonSet.setForeground(SystemColor.menuText);
                   GyudonSet.setBackground(SystemColor.activeCaption);
                   GyudonSet.setHorizontalAlignment(SwingConstants.CENTER);
                   GyudonSet.setBounds(41, 15, 86, 30);
                   menuPanel.add(GyudonSet);
                   
                   JLabel DrinkSet = new JLabel("Drink Set");
                   DrinkSet.setFont(new Font("Segoe UI Black", Font.BOLD, 13));
                   DrinkSet.setHorizontalAlignment(SwingConstants.CENTER);
                   DrinkSet.setForeground(SystemColor.menuText);
                   DrinkSet.setBackground(SystemColor.activeCaption);
                   DrinkSet.setBounds(191, 15, 77, 30);
                   menuPanel.add(DrinkSet);
                   
                   JLabel AlaCarteSet = new JLabel("Ala Carte Set");
                   AlaCarteSet.setFont(new Font("Segoe UI Black", Font.BOLD, 13));
                   AlaCarteSet.setHorizontalAlignment(SwingConstants.CENTER);
                   AlaCarteSet.setForeground(SystemColor.menuText);
                   AlaCarteSet.setBackground(SystemColor.activeCaption);
                   AlaCarteSet.setBounds(323, 15, 100, 30);
                   menuPanel.add(AlaCarteSet);
                   
                   JLabel TeriyakiSet = new JLabel("Teriyaki Set");
                   TeriyakiSet.setFont(new Font("Segoe UI Black", Font.BOLD, 13));
                   TeriyakiSet.setHorizontalAlignment(SwingConstants.CENTER);
                   TeriyakiSet.setForeground(SystemColor.menuText);
                   TeriyakiSet.setBackground(SystemColor.activeCaption);
                   TeriyakiSet.setBounds(462, 15, 86, 30);
                   menuPanel.add(TeriyakiSet);
                   
                   JLabel FamilySet = new JLabel("Family Set\r\n");
                   FamilySet.setFont(new Font("Segoe UI Black", Font.BOLD, 13));
                   FamilySet.setHorizontalAlignment(SwingConstants.CENTER);
                   FamilySet.setForeground(SystemColor.menuText);
                   FamilySet.setBackground(SystemColor.activeCaption);
                   FamilySet.setBounds(602, 15, 86, 30);
                   menuPanel.add(FamilySet);
                   
                   JLabel RamenSet = new JLabel("Ramen Set\r\n");
                   RamenSet.setFont(new Font("Segoe UI Black", Font.BOLD, 13));
                   RamenSet.setHorizontalAlignment(SwingConstants.CENTER);
                   RamenSet.setForeground(SystemColor.menuText);
                   RamenSet.setBackground(SystemColor.activeCaption);
                   RamenSet.setBounds(764, 15, 86, 30);
                   menuPanel.add(RamenSet);
                   
                   JButton btnThreeCheeseGyudon = new JButton("Three Cheese \r\n");        
                   btnThreeCheeseGyudon.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnThreeCheeseGyudon.setBounds(41, 115, 109, 30);
                   menuPanel.add(btnThreeCheeseGyudon);
                   btnThreeCheeseGyudon.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      	 totalPrice += 12.90; // Example price
                        updateTotalLabel();
                        // Add Gyudon details to foodDetails StringBuilder
                        foodDetails.append("Three Cheese: RM 12.90\n");
                        // Update textField_9 with accumulated foodDetails
                        textField_9.setText(foodDetails.toString());
                        // Update the total food payment in textField_1
                        updateTotalFoodPayment();
                      	}
                      });
                   
                   JButton btnVolcanoChili = new JButton("Volcano Chili ");
                   btnVolcanoChili.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnVolcanoChili.setBounds(41, 182, 109, 30);
                   menuPanel.add(btnVolcanoChili);
                   btnVolcanoChili.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      	 totalPrice += 12.90; // Example price
                        updateTotalLabel();
                        // Add Gyudon details to foodDetails StringBuilder
                        foodDetails.append("Volcano Chili: RM 12.90\n");
                        // Update textField_9 with accumulated foodDetails
                        textField_9.setText(foodDetails.toString());
                        // Update the total food payment in textField_1
                        updateTotalFoodPayment();
                      	}
                      });
                   
                   
                   JButton btnOsakaOkonomi = new JButton("Osaka Okonomi");
                   btnOsakaOkonomi.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnOsakaOkonomi.setBounds(41, 252, 109, 30);
                   menuPanel.add(btnOsakaOkonomi);
                   btnOsakaOkonomi.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      	 totalPrice += 13.90; // Example price
                        updateTotalLabel();
                     // Add Gyudon details to foodDetails StringBuilder
                        foodDetails.append("Osaka Okonomi: RM 13.90\n");
                        // Update textField_9 with accumulated foodDetails
                        textField_9.setText(foodDetails.toString());
                        // Update the total food payment in textField_1
                        updateTotalFoodPayment();
                      	}
                      });
                   
                   JButton btnNegitama = new JButton("Negitama");
                   btnNegitama.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnNegitama.setBounds(41, 320, 109, 30);
                   menuPanel.add(btnNegitama);
                   btnNegitama.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      	 totalPrice += 13.90; // Example price
                        updateTotalLabel();
                     // Add Gyudon details to foodDetails StringBuilder
                        foodDetails.append("Negitama: RM 13.90\n");
                        // Update textField_9 with accumulated foodDetails
                        textField_9.setText(foodDetails.toString());
                        // Update the total food payment in textField_1
                        updateTotalFoodPayment();
                      	}
                      });
                   
                   JButton btnSpicyOntama = new JButton("Spicy Ontama");
                   btnSpicyOntama.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnSpicyOntama.setBounds(41, 390, 109, 30);
                   menuPanel.add(btnSpicyOntama);
                   btnSpicyOntama.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      	 totalPrice += 12.90; // Example price
                        updateTotalLabel();
                        // Add Gyudon details to foodDetails StringBuilder
                        foodDetails.append("Spicy Ontama: RM 12.90\n");
                        // Update textField_9 with accumulated foodDetails
                        textField_9.setText(foodDetails.toString());
                        // Update the total food payment in textField_1
                        updateTotalFoodPayment();
                      	}
                      });
                   
                   JButton btnSpicyDoubleOntama = new JButton("Spicy Double Ontama");
                   btnSpicyDoubleOntama.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnSpicyDoubleOntama.setBounds(29, 461, 131, 30);
                   menuPanel.add(btnSpicyDoubleOntama);
                   btnSpicyDoubleOntama.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      	 totalPrice += 15.10; // Example price
                        updateTotalLabel();
                        // Add Gyudon details to foodDetails StringBuilder
                        foodDetails.append("Spicy Double Ontama: RM 15.10\n");
                        // Update textField_9 with accumulated foodDetails
                        textField_9.setText(foodDetails.toString());
                        // Update the total food payment in textField_1
                        updateTotalFoodPayment();
                      	}
                      });
                   
                   JButton btnHojichaMilikTea = new JButton("Hojicha Milk Tea");
                   btnHojichaMilikTea.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnHojichaMilikTea.setBounds(180, 115, 119, 30);
                   menuPanel.add(btnHojichaMilikTea);
                   btnHojichaMilikTea.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      		totalPrice += 6.90;
                      		updateTotalLabel();
                      		// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Hojicha Milk Tea: RM 6.90\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                      	}
                      });
          
                   
                   JButton btnMilikTea = new JButton("Milik Tea");
                   btnMilikTea.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnMilikTea.setBounds(180, 182, 119, 30);
                   menuPanel.add(btnMilikTea);
                   btnMilikTea.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      		totalPrice += 6.50;
                      		updateTotalLabel();
                      		// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Milk Tea: RM 6.50\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                      	}
                      });
                   
                   JButton btnMatchaMilikTea = new JButton("Matcha Milk Tea");
                   btnMatchaMilikTea.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnMatchaMilikTea.setBounds(180, 252, 119, 30);
                   menuPanel.add(btnMatchaMilikTea);
                   btnMatchaMilikTea.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      		totalPrice += 8.90;
                      		updateTotalLabel();
                      	// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Matcha Milk Tea: RM 8.90\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                      	}
                      });
                   
                   JButton btnMilkTeaWith = new JButton("Milk Tea Brown");
                   btnMilkTeaWith.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnMilkTeaWith.setBounds(180, 320, 119, 30);
                   menuPanel.add(btnMilkTeaWith);
                   btnMilkTeaWith.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      		totalPrice += 7.90;
                      		updateTotalLabel();
                      		// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Milk Tea Brown: RM 7.90\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                      	}
                      });
                   
                   JButton btnPeachTea = new JButton("Peach Tea");
                   btnPeachTea.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnPeachTea.setBounds(180, 390, 119, 30);
                   menuPanel.add(btnPeachTea);
                   btnPeachTea.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      		totalPrice += 5.90;
                      		updateTotalLabel();
                      	// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Peach Tea: RM 5.90\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                      	}
                      });
                   
                   JButton btnAloeVeraJuice = new JButton("Aloe Vera Juice");
                   btnAloeVeraJuice.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnAloeVeraJuice.setBounds(180, 461, 119, 30);
                   menuPanel.add(btnAloeVeraJuice);
                   btnAloeVeraJuice.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      		totalPrice += 5.90;
                      		updateTotalLabel();
                      	// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Aloe Vera: RM 5.90\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                      	}
                      });
                   
                   JButton btnMeatBall = new JButton("Meat Ball");
                   btnMeatBall.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnMeatBall.setBounds(323, 115, 100, 30);
                   menuPanel.add(btnMeatBall);
                   btnMeatBall.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      		totalPrice += 6.50;
                      		updateTotalLabel();
                      		// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Meat Ball: RM6.50\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                      	}
                      });
                   
                   JButton btnChickenKatsu = new JButton("Chicken Katsu");
                   btnChickenKatsu.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnChickenKatsu.setBounds(323, 182, 100, 30);
                   menuPanel.add(btnChickenKatsu);
                   btnChickenKatsu.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      		totalPrice += 9.50;
                      		updateTotalLabel();
                      		// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Chicken Katsu: RM 9.50\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                      	}
                      });
                   
                   JButton btnEbiFry = new JButton("Ebi Fry");
                   btnEbiFry.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnEbiFry.setBounds(323, 252, 100, 30);
                   menuPanel.add(btnEbiFry);
                   btnEbiFry.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      		totalPrice += 12.60;
                      		updateTotalLabel();
                      		// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Ebi Fry: RM 12.60\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                      	}
                      });
                   
                   JButton btnUnasara = new JButton("Una-Sara");
                   btnUnasara.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnUnasara.setBounds(323, 320, 100, 30);
                   menuPanel.add(btnUnasara);
                   btnUnasara.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      		totalPrice += 27.00;
                      		updateTotalLabel();
                      		// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Una-Sara: RM27.00\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                      	}
                      });
                   
                   JButton btnTeriyakiMackerel = new JButton("Mackerel");
                   btnTeriyakiMackerel.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnTeriyakiMackerel.setBounds(323, 395, 100, 30);
                   menuPanel.add(btnTeriyakiMackerel);
                   btnTeriyakiMackerel.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      		totalPrice += 7.90;
                      		updateTotalLabel();
                      	// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Mackerel: RM 7.90\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                      	}
                      });
                   
                   JButton btnDeepFriedCg = new JButton("Deep Fried CG");
                   btnDeepFriedCg.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnDeepFriedCg.setBounds(323, 461, 100, 30);
                   menuPanel.add(btnDeepFriedCg);
                   btnDeepFriedCg.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      		totalPrice += 5.40;
                      		updateTotalLabel();
                      	// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Deep Fried CG: RM 5.40");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                      	}
                      });
                   
                   JButton btnChickenMayo = new JButton("Chicken Mayo");
                   btnChickenMayo.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnChickenMayo.setBounds(462, 115, 109, 30);
                   menuPanel.add(btnChickenMayo);
                   btnChickenMayo.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      		totalPrice += 15.50;
                      		updateTotalLabel();
                      	// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Chicken Mayo: RM 15.50\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                      	}
                      });
                   
                   JButton btnChickenDon = new JButton("Chicken Don");
                   btnChickenDon.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnChickenDon.setBounds(462, 182, 109, 30);
                   menuPanel.add(btnChickenDon);
                   btnChickenDon.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      		totalPrice += 12.90;
                      		updateTotalLabel();
                      		// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Chicken Don: RM 12.90\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                      	}
                      });
                   
                   JButton btnChickenDoubleOd = new JButton("Chicken Double");
                   btnChickenDoubleOd.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnChickenDoubleOd.setBounds(462, 252, 109, 30);
                   menuPanel.add(btnChickenDoubleOd);
                   btnChickenDoubleOd.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      		totalPrice += 17.10;
                      		updateTotalLabel();
                      		// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Chicken Double: RM 17.10\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                      	}
                      });
                   
                   JButton btnTeriyakiSet = new JButton("Teriyaki Set 1");
                   btnTeriyakiSet.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnTeriyakiSet.setBounds(462, 320, 109, 30);
                   menuPanel.add(btnTeriyakiSet);
                   btnTeriyakiSet.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      		totalPrice += 19.30;
                      		updateTotalLabel();
                      	// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Teriyaki Set 1: RM 19.30\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                      	}
                      });
                   
                   JButton btnTeriyakiSet_1 = new JButton("Teriyaki Set 2");
                   btnTeriyakiSet_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnTeriyakiSet_1.setBounds(462, 395, 109, 30);
                   menuPanel.add(btnTeriyakiSet_1);
                   btnTeriyakiSet_1.addActionListener(new ActionListener() {
                     	public void actionPerformed(ActionEvent e) {
                     		totalPrice += 17.30;
                     		updateTotalLabel();
                     		// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Teriyaki Set 2: RM 17.30\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                     	}
                     });
                   
                   JButton btnTeriyakiSet_2 = new JButton("Teriyaki Set 3");
                   btnTeriyakiSet_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnTeriyakiSet_2.setBounds(462, 461, 109, 30);
                   menuPanel.add(btnTeriyakiSet_2);
                   btnTeriyakiSet_2.addActionListener(new ActionListener() {
                     	public void actionPerformed(ActionEvent e) {
                     		totalPrice += 23.00;
                     		updateTotalLabel();
                     	// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Teriyaki Set 3: RM 23.00\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                     	}
                     });
                   
                   JButton btnFamilySet = new JButton("Family Set 2");
                   btnFamilySet.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnFamilySet.setBounds(602, 115, 100, 30);
                   menuPanel.add(btnFamilySet);
                   btnFamilySet.addActionListener(new ActionListener() {
                     	public void actionPerformed(ActionEvent e) {
                     		totalPrice += 69.90;
                     		updateTotalLabel();
                     	// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Family Set 2: RM 69.90\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                     	}
                     });
                   
                   JButton btnMiniGyudon = new JButton("Mini Gyudon");
                   btnMiniGyudon.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnMiniGyudon.setBounds(602, 182, 100, 30);
                   menuPanel.add(btnMiniGyudon);
                   btnMiniGyudon.addActionListener(new ActionListener() {
                     	public void actionPerformed(ActionEvent e) {
                     		totalPrice += 5.90;
                     		updateTotalLabel();
                     	// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Mini Gyudon: RM 6.90\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                     	}
                     });
                   
                   JButton btnMiniYakiniku = new JButton("Mini Yakiniku");
                   btnMiniYakiniku.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnMiniYakiniku.setBounds(602, 252, 100, 30);
                   menuPanel.add(btnMiniYakiniku);
                   btnMiniYakiniku.addActionListener(new ActionListener() {
                     	public void actionPerformed(ActionEvent e) {
                     		totalPrice += 6.90;
                     		updateTotalLabel();
                     	// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Mini Yakiniku: RM 6.90\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                     	}
                     });
                   
                   JButton btnMiniOntama = new JButton("Mini Ontama");
                   btnMiniOntama.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnMiniOntama.setBounds(602, 320, 100, 30);
                   menuPanel.add(btnMiniOntama);
                   btnMiniOntama.addActionListener(new ActionListener() {
                    	public void actionPerformed(ActionEvent e) {
                    		totalPrice += 5.90;
                    		updateTotalLabel();
                    		// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Mini Ontama: RM 5.90\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                    	}
                    });
                   
                   JButton btnMiniTeriyaki = new JButton("Mini Teriyaki");
                   btnMiniTeriyaki.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnMiniTeriyaki.setBounds(602, 395, 100, 30);
                   menuPanel.add(btnMiniTeriyaki);
                   btnMiniTeriyaki.addActionListener(new ActionListener() {
                    	public void actionPerformed(ActionEvent e) {
                    		totalPrice += 6.90;
                    		updateTotalLabel();
                    		// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Mini Teriyaki: RM 6.90\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                    	}
                    });
                   
                   JButton btnMiniRamen = new JButton("Mini Ramen");
                   btnMiniRamen.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnMiniRamen.setBounds(602, 466, 100, 30);
                   menuPanel.add(btnMiniRamen);
                   btnMiniRamen.addActionListener(new ActionListener() {
                    	public void actionPerformed(ActionEvent e) {
                    		totalPrice += 6.90;
                    		updateTotalLabel();
                    		// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Mini Ramen: RM 6.90\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                    		
                    	}
                    });
                   
                   JButton btnTokyoAjitama = new JButton("Tokyo Ajitama ");
                   btnTokyoAjitama.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnTokyoAjitama.setBounds(748, 115, 119, 30);
                   menuPanel.add(btnTokyoAjitama);
                   btnTokyoAjitama.addActionListener(new ActionListener() {
                    	public void actionPerformed(ActionEvent e) {
                    		totalPrice += 12.60;
                    		updateTotalLabel();
                    		// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Tokyo Ajitama: RM 12.60\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                    	}
                    });
                   JButton btnChickenKatsu_1 = new JButton("Chicken Katsu");
                   btnChickenKatsu_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnChickenKatsu_1.setBounds(748, 182, 119, 30);
                   menuPanel.add(btnChickenKatsu_1);
                   btnChickenKatsu_1.addActionListener(new ActionListener() {
                   	public void actionPerformed(ActionEvent e) {
                   		totalPrice += 16.40;
                   		updateTotalLabel();
                   		// Add Gyudon details to foodDetails StringBuilder
                        foodDetails.append("Chicken Katsu: RM 16.40\n");
                        // Update textField_9 with accumulated foodDetails
                        textField_9.setText(foodDetails.toString());
                        // Update the total food payment in textField_1
                        updateTotalFoodPayment();
                   	}
                   });
                   
                   JButton btnVolcanoChili_1 = new JButton("Volcano Chili ");
                   btnVolcanoChili_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnVolcanoChili_1.setBounds(748, 252, 119, 30);
                   menuPanel.add(btnVolcanoChili_1);
                   btnVolcanoChili_1.addActionListener(new ActionListener() {
                   	public void actionPerformed(ActionEvent e) {
                   		totalPrice += 14.30;
                   		updateTotalLabel();
                   	// Add Gyudon details to foodDetails StringBuilder
                        foodDetails.append("Volcano Chili: RM 14.30\n");
                        // Update textField_9 with accumulated foodDetails
                        textField_9.setText(foodDetails.toString());
                        // Update the total food payment in textField_1
                        updateTotalFoodPayment();
                   	}
                   });
                   
                   JButton btnVolcanoChiliKatsu = new JButton("Volcano Chili Katsu");
                   btnVolcanoChiliKatsu.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnVolcanoChiliKatsu.setBounds(748, 320, 119, 30);
                   menuPanel.add(btnVolcanoChiliKatsu);
                   btnVolcanoChili_1.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      		totalPrice += 14.30;
                      		updateTotalLabel();
                      	// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Volcano Chili Katsu: RM 14.30\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                      	}
                      });
                   
                   JButton btnUnagiDon = new JButton("Unagi Don");
                   btnUnagiDon.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnUnagiDon.setBounds(748, 395, 119, 30);
                   menuPanel.add(btnUnagiDon);
                   btnUnagiDon.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      		totalPrice += 27.80;
                      		updateTotalLabel();
                      	// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Unagi Don: RM 27.80\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                      	}
                      });
                   
                   JButton btnUnagyu = new JButton("Una-Gyu");
                   btnUnagyu.setFont(new Font("Tahoma", Font.PLAIN, 10));
                   btnUnagyu.setBounds(748, 466, 119, 30);
                   menuPanel.add(btnUnagyu);
                   btnUnagyu.addActionListener(new ActionListener() {
                      	public void actionPerformed(ActionEvent e) {
                      		totalPrice += 32.00;
                      		updateTotalLabel();
                      	// Add Gyudon details to foodDetails StringBuilder
                            foodDetails.append("Una-Gyu: RM 32.00\n");
                            // Update textField_9 with accumulated foodDetails
                            textField_9.setText(foodDetails.toString());
                            // Update the total food payment in textField_1
                            updateTotalFoodPayment();
                      	}
                      });

  }
    				protected boolean updateCurrentBalanceInDatabase(int id, BigDecimal currentBalance) {
		// TODO Auto-generated method stub
		return false;
	}

					private void updateTotalLabel() {
    				double totalFoodPayment = calculateTotalFoodPayment();
			        totalLabel.setText("Total: RM " + String.format("%.2f", totalFoodPayment));
			        textField_1.setText("RM " + String.format("%.2f", totalFoodPayment));
    				}
    				
    				private void updateTotalFoodPayment() {
    				    // Calculate the total food payment based on the chosen food items
    				    double totalFoodPayment = calculateTotalFoodPayment();
    				    textField_1.setText("RM " + String.format("%.2f", totalFoodPayment)); // Update textField_1
    				}

    				
    				 private double calculateTotalFoodPayment() {
    				        // Calculate total food payment based on chosen food items
    				        // Example:
    				        // double totalFoodPayment = priceOfGyudon + priceOfAlaCarte + priceOfRamenNoodles + ...;
    				        // Calculate totalFoodPayment based on the prices of chosen items
    				        return totalPrice;
				}
}
   