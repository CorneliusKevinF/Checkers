package view;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel {
	private int sideLength;
	private PositionPanel[] positionPanels;
	
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
