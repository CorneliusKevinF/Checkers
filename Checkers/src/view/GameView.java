package view;

import javax.swing.*;
import java.util.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.*;

public class GameView implements Observer {
	BoardPanel boardPanel;
	JFrame frame;

	public GameView() {
		frame = new JFrame("CD Checkers");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		
		if(screenWidth > screenHeight) {
			frame.setMinimumSize(new Dimension(screenHeight - 100, screenHeight - 100));
		} else {
			frame.setMinimumSize(new Dimension(screenWidth - 100, screenWidth - 100));
		}
		
		Insets insets = frame.getInsets();
		JLayeredPane contentPane = new JLayeredPane();
		contentPane.setSize(new Dimension(frame.getWidth() - insets.left - insets.right, frame.getHeight() - insets.top - insets.bottom));
		frame.setContentPane(contentPane);
		
		boardPanel = new BoardPanel(contentPane.getHeight());
		
		frame.getContentPane().add(boardPanel, new Integer(1));
		boardPanel.setPositionPanels();
		
		frame.pack();
		frame.setVisible(true);
	}

	// This method seems to be slightly dependent on the model.
	@Override
	public void update(Observable observable, Object observed) {

		//System.out.println("Position: (" + position.getX() + ", " + position.getY() + ") updating...");
		
		
		// Not sure if the view should be calling these methods. I think it breaks some MVC rules.
		boardPanel.update(observed);
	}
	
	public void addController(MouseListener controller) {
		boardPanel.addMouseListener(controller);
	}
	
	public JFrame getFrame() {
		return frame;
	}
}
