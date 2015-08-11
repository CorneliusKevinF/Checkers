package view;

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class GameView implements Observer {
	BoardPanel boardPanel;
	JFrame frame;
	
	public GameView() {
		//TODO Position and size of Frame still aren't perfect. Learn how to better manipulate JFrames.
		
		frame = new JFrame("CD Checkers");
		frame.setLayout(null);

        Insets insets = frame.getInsets();
        
		boardPanel = new BoardPanel(insets);
		
		frame.add(boardPanel);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension contentSize = boardPanel.getSize();
        
        frame.setVisible(true);
        
        frame.setSize(new Dimension((int) contentSize.getWidth() + insets.left + insets.right, (int) contentSize.getHeight() + insets.top + insets.bottom));
		
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		
        frame.setLocation((int) ((screenWidth - frame.getWidth()) / 2), (int) ((screenHeight - frame.getHeight()) / 2));
	}

	@Override
	public void update(Observable observerable, Object observed) {
		model.Position position = (model.Position) observed;
		
		System.out.println("Position: (" + position.getX() + ", " + position.getY() + ") updating...");
		
		boardPanel.update(position.getX(), position.getY());
		
	}
	
	public JFrame getFrame() {
		return frame;
	}
}
