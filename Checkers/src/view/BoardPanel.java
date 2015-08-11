package view;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel {
	private int sideLength;
	private PositionPanel[][] positionPanels;
	
	public BoardPanel(Insets insets) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		
		if (screenWidth < screenHeight) {
			sideLength = (screenWidth - insets.left - insets.right);
		} else {
			sideLength = (screenHeight - insets.top - insets.bottom);
		}
		
		setMinimumSize(new Dimension(sideLength, sideLength));
		setMaximumSize(new Dimension (sideLength, sideLength));
		setSize(new Dimension(sideLength, sideLength));
		
		
		positionPanels = new PositionPanel[8][8];
		int positionSideLength = (int) (sideLength / 9);
		int x, y; 
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				x = (int) ((i * positionSideLength) + (positionSideLength / 2));
				y = (int) ((j * positionSideLength) + (positionSideLength / 2));
				positionPanels[i][j] = new PositionPanel(x, y, positionSideLength);
			}
		}
	}
	
	public void update(int x, int y) {
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		
		graphics.setColor(Color.CYAN);
		graphics.fillRect(0, 0, sideLength, sideLength);
	}
}
