package graphics;

import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class BasicGraphic extends JFrame {

    public BasicGraphic() {
        initUI();
    }

    private void initUI() {

        add(new Surface());

        setTitle("Simple Java 2D example");
        
        // Set's the size of the window to be the size of the screen. 
        // TODO Set the window to fullscreen mode.
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(size);
        
        // This disallows the window from being resized.
        setMinimumSize(size);
        setMaximumSize(size);
        
        // Set's the position of the window on the screen. A null value centers the window.
        setLocationRelativeTo(null);
        
        // Set's the behavior of the close button (the 'X' in the top right corner) for the window.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
