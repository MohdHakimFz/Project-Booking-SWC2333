package Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import db_objs.MyJDBC;
import db_objs.User;

public class LoginPage extends BaseFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Constructor
    public LoginPage() {
        super("Restaurant Sukiya Login"); // Calls constructor of BaseFrame
        getContentPane().setBackground(new Color(255, 255, 255)); // Sets background color
        addGuiComponents(); // Call method to add GUI components
    }
    
    // Method to add GUI components
    @Override
    protected void addGuiComponents() {
        JPanel panel = new JPanel(); // Create panel to hold components
        panel.setBackground(new Color(192, 192, 192)); // Set panel background color
        panel.setBounds(0, 10, 487, 516); // Set panel bounds
        getContentPane().add(panel); // Add panel to content pane
        panel.setLayout(null); // Set layout to null
        
        // Restaurant Sukiya Label
        JLabel LoginPageLabel = new JLabel("Restaurant Sukiya");
        LoginPageLabel.setBounds(55, 115, 325, 42); // Set label bounds
        LoginPageLabel.setFont(new Font("Dialog", Font.BOLD, 32)); // Set label font
        LoginPageLabel.setHorizontalAlignment(SwingConstants.CENTER); // Set alignment
        panel.add(LoginPageLabel); // Add label to panel
        
        // Password Field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(23, 310, 412, 40); // Set field bounds
        panel.add(passwordField); // Add field to panel
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 28)); // Set font
        
        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(23, 276, 154, 24); // Set label bounds
        panel.add(passwordLabel); // Add label to panel
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20)); // Set font
        
        // Username Field
        JTextField usernameField = new JTextField();
        usernameField.setBounds(23, 226, 403, 40); // Set field bounds
        panel.add(usernameField); // Add field to panel
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 28)); // Set font
        
        // Username Label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(23, 186, 165, 30); // Set label bounds
        panel.add(usernameLabel); // Add label to panel
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20)); // Set font
        
        // Users Icon
        JLabel label2 = new JLabel("");
        Image img1 = new ImageIcon(LoginPage.class.getResource("/img/Users.png")).getImage();
        label2.setIcon(new ImageIcon(img1));
        label2.setBounds(178, 38, 72, 79); // Set icon bounds
        panel.add(label2); // Add icon to panel
        
        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(23, 389, 412, 42); // Set button bounds
        panel.add(loginButton); // Add button to panel
        loginButton.setFont(new Font("Dialog", Font.BOLD, 20)); // Set font
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get username and password
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                // Validate login
                User user = MyJDBC.validateLogin(username, password);
                if (user != null) {
                    // If login is successful
                    LoginPage.this.dispose(); // Close login page
                    RestaurantWebPage restaurantWebPage = new RestaurantWebPage(user); // Open restaurant page
                    restaurantWebPage.setVisible(true);
                    JOptionPane.showMessageDialog(restaurantWebPage, "Login Successfully!"); // Show success message
                } else {
                    // If login fails
                    JOptionPane.showMessageDialog(LoginPage.this, "Login Failed..."); // Show error message
                }
            }
        });
        
        // Register Label
        JLabel registerLabel = new JLabel("<html><a href=\"#\" style=\"color: blue; text-decoration: underline;\">Don't have an account? Register here</a></html>");
        registerLabel.setBounds(0, 447, 467, 30); // Set label bounds
        registerLabel.setFont(new Font("Dialog", Font.PLAIN, 20)); // Set font
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER); // Set alignment
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open register page
                LoginPage.this.dispose(); // Close login page
                new RegisterPage().setVisible(true); // Open register page
            }
        });
        panel.add(registerLabel); // Add label to panel
        
        // Login Image
        JLabel label1 = new JLabel("");
        Image img2 = new ImageIcon(LoginPage.class.getResource("/img/Login (2).png")).getImage();
        label1.setIcon(new ImageIcon(img2));
        label1.setBounds(576, 10, 400, 500); // Set image bounds
        getContentPane().add(label1); // Add image to content pane
    }
}
