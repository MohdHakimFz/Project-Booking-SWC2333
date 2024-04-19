package Gui;

//Import statement to bring in the JFrame class from the javax.swing package.
import javax.swing.JFrame;

//BaseFrame is an abstract class that extends JFrame, serving as a base for creating GUI frames.
public abstract class BaseFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	// Abstract method to be implemented by subclasses for custom initialization.
    // This method allows subclasses to customize frame initialization according to their specific needs.
    public BaseFrame(String title) {
        initialize(title);
    }

    private void initialize(String title) {
        // Instantiate JFrame properties and add a title to the bar
        setTitle(title);

        // Set size (in pixels)
        // (widith, height)
        setSize(1060, 553);

        // Terminate program when the frame is closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Set layout to null to have absolute layout which allows us to manually specify
        // the size and position of each GUI component
        getContentPane().setLayout(null);

        // Prevent GUI from being resized
        setResizable(false);

        // Launch the GUI in the center of the screen
        setLocationRelativeTo(null);

        // Call on the subclass' addGuiComponents()
        addGuiComponents();
    }

    // This method is defined as abstract, so subclasses must implement it
    protected abstract void addGuiComponents();
}
