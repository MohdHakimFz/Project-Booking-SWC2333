package Gui;

import javax.swing.SwingUtilities;

public class AppLauncher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
               
                
                // If you want to launch LoginPage instead, uncomment the following line:
                   new LoginPage().setVisible(true);
                
                // If you want to launch RegisterPage instead, uncomment the following line:
                  //new RegisterPage().setVisible(true);
                 
                 // Launch RestaurantWebPage instead of LoginPage
                 //new RestaurantWebPage().setVisible(true);
            }
        });
    }
}
