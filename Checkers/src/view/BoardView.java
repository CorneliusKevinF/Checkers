package view;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class BoardView extends JPanel {
	private int sideLength;
	
	public BoardView() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		
		if (screenWidth < screenHeight) {
			sideLength = screenWidth;
		} else {
			sideLength = screenHeight;
		}
		
		setMinimumSize(new Dimension(sideLength, sideLength));
		setMaximumSize(new Dimension (sideLength, sideLength));
		setSize(new Dimension(sideLength, sideLength));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		
		graphics.setColor(Color.CYAN);
		graphics.fillRect(0, 0, sideLength, sideLength);
	}
}
