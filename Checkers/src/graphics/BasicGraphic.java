package graphics;

import javax.swing.JFrame;
import java.awt.Frame;
import java.awt.Dimension;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class BasicGraphic extends JFrame {

    public BasicGraphic() {
        initUI();
    }

    private void initUI() {
    	Toolkit toolkit = Toolkit.getDefaultToolkit();
    	Dimension size; 
    	
        add(new Surface());

        setTitle("Simple Java 2D example");
        
        // Make the window the size of the screen.
        size = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new Dimension(400, 400));
        
        size = getSize();
        
        /*
        // Maximize the window.
    	if(toolkit.isFrameStateSupported(Frame.MAXIMIZED_BOTH)) {
    		setExtendedState(Frame.MAXIMIZED_BOTH);
    	}
		*/
        
        System.out.println("Height: " + size.getHeight() + "\nWidth: " + size.getWidth());
        
        
        // This disallows the window from being resized.
        setMinimumSize(size);
        setMaximumSize(size);
        
        // Set's the position of the window on the screen. A null value centers the window.
        setLocationRelativeTo(null);
        
        // Set's the behavior of the close button (the 'X' in the top right corner) for the window.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
