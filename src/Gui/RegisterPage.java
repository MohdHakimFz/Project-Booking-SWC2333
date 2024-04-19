
package Gui;

import javax.swing.*;
import db_objs.MyJDBC;
import java.awt.*;
import java.awt.event.*;

public class RegisterPage extends BaseFrame {

    private static final long serialVersionUID = 1L;

    // Constructor
    public RegisterPage() {
        super("Restaurant Sukiya Register"); // Call constructor of BaseFrame
        getContentPane().setBackground(new Color(255, 255, 255)); // Set background color
        addGuiComponents(); // Call method to add GUI components
    }

    // Method to add GUI components
    protected void addGuiComponents() {

        // Panel for registration form
        JPanel panel_1 = new JPanel();
        panel_1.setForeground(SystemColor.menu);
        panel_1.setBackground(new Color(233, 150, 122));
        panel_1.setBounds(0, 67, 443, 451);
        getContentPane().add(panel_1);
        panel_1.setLayout(null);

        // Username Label and Field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 10, 295, 30);
        panel_1.add(usernameLabel);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        JTextField usernameField = new JTextField();
        usernameField.setBounds(20, 44, 295, 40);
        panel_1.add(usernameField);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 28));

        // Password Label and Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 116, 295, 30);
        panel_1.add(passwordLabel);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20, 159, 295, 40);
        panel_1.add(passwordField);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 28));

        // Re-password Label and Field
        JLabel rePasswordLabel = new JLabel("Re-type Password:");
        rePasswordLabel.setBounds(20, 219, 295, 40);
        panel_1.add(rePasswordLabel);
        rePasswordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        JPasswordField repasswordField = new JPasswordField();
        repasswordField.setBounds(20, 269, 295, 40);
        panel_1.add(repasswordField);
        repasswordField.setFont(new Font("Dialog", Font.PLAIN, 28));

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setForeground(new Color(0, 0, 0));
        registerButton.setBackground(new Color(230, 230, 250));
        registerButton.setBounds(20, 347, 295, 40);
        panel_1.add(registerButton);
        registerButton.setFont(new Font("Dialog", Font.BOLD, 20));

        // Login Label
        JLabel loginLabel = new JLabel("<html><a href=\"#\">Have an account? Sign-in here</a></html>");
        loginLabel.setBounds(68, 400, 317, 41);
        panel_1.add(loginLabel);
        loginLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open login page
                RegisterPage.this.dispose();
                new LoginPage().setVisible(true);
            }
        });

        // Top Panel with Restaurant Sukiya title
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 105, 180));
        panel.setBounds(0, 0, 1050, 67);
        getContentPane().add(panel);
        panel.setLayout(null);
        JLabel RestaurantRegister = new JLabel("Restaurant Sukiya");
        RestaurantRegister.setBounds(349, 15, 325, 42);
        panel.add(RestaurantRegister);
        RestaurantRegister.setFont(new Font("Dialog", Font.BOLD, 32));
        RestaurantRegister.setHorizontalAlignment(SwingConstants.CENTER);

        // Logo Image
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon(RegisterPage.class.getResource("/img/Sukiyalogo.png")));
        lblNewLabel_1.setBounds(294, 0, 71, 67);
        getContentPane().add(lblNewLabel_1);

        // Sign up Image
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(RegisterPage.class.getResource("/img/sign up.png")));
        lblNewLabel.setBounds(443, 67, 607, 451);
        getContentPane().add(lblNewLabel);

        // Register Button Action Listener
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get username, password, and re-typed password
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String rePassword = String.valueOf(repasswordField.getPassword());

                // Validate user input
                if (validateUserInput(username, password, rePassword)) {
                    // Attempt to register the user to the database
                    if (MyJDBC.register(username, password)) {
                        // Register success
                        // Dispose of this GUI
                        RegisterPage.this.dispose();

                        // Launch the login GUI
                        LoginPage loginGui = new LoginPage();
                        loginGui.setVisible(true);

                        // Create a result dialog
                        JOptionPane.showMessageDialog(loginGui, "Registered Account Successfully!");
                    } else {
                        // Register failed (username already taken)
                        JOptionPane.showMessageDialog(RegisterPage.this, "Error: Username already taken");
                    }
                } else {
                    // Invalid user input
                    JOptionPane.showMessageDialog(RegisterPage.this, "Error: Username must be at least 6 characters\n" +
                            "and/or Passwords must match!");
                }
            }
        });
    }

    // Method to validate user input
    private boolean validateUserInput(String username,String password, String rePassword) {
        // All fields must have a value
        if(username.length() == 0 || password.length() == 0 || rePassword.length() == 0) return false;

        // Username has to be at least 6 characters long
        if(username.length() < 6) return false;

        // Password and rePassword must match
        if(!password.equals(rePassword)) return false;

        // Passes validation
        return true;
    }
}
