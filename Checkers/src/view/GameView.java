package view;

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class GameView implements Observer {
	BoardPanel boardPanel;
	
	public GameView() {
		JFrame frame = new JFrame("CD Chekcers");
		frame.setLayout(null);

        Insets insets = frame.getInsets();
        
		boardPanel = new BoardPanel(insets);
		
		frame.add(boardPanel);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension contentSize = boardPanel.getSize();
        
        frame.setVisible(true);
        
        frame.setSize(new Dimension((int) contentSize.getWidth() + insets.left + insets.right, (int) contentSize.getHeight() + insets.top + insets.bottom));
        frame.setLocation((int) (frame.getLocation().getX() - (frame.getWidth() / 2)), (int) (frame.getLocation().getY() - (frame.getHeight() / 2)));
	}

	@Override
	public void update(Observable observerable, Object observed) {
		model.Position position = (model.Position) observed;
		
		boardPanel.update(position.getX(), position.getY());
		
	}
}
