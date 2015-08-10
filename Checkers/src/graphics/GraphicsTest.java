package graphics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.BorderFactory;

public class GraphicsTest {
	public static void createAndShowFrame() {
		
		/*
		// Create a new frame and configure the close button.
		JFrame frame = new JFrame("Exit");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		*/
		
		
		/* Create a menu bar.
		JMenuBar menuBar = new JMenuBar();
		menuBar.setOpaque(true);
		menuBar.setBackground(new Color(255, 0, 255));
		menuBar.setPreferredSize(new Dimension(175, 20));
		*/
		
		/*
		// Create a label.
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setPreferredSize(new Dimension(175, 100));
		
		// frame.setJMenuBar(menuBar);
		frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
		
		// Display the frame.
		frame.pack();
		frame.setVisible(true);
		
		frame.toFront(); 
		*/
	}
	
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
            	Game game = new Game();
            	/*
                BasicGraphic bg = new BasicGraphic();
                bg.setVisible(true);
				*/
				
				//createAndShowFrame();
            	
            	game.getBoard().addPiece(1, 1, Color.BLACK);
            }
        });
    }
}