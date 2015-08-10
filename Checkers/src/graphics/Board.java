package graphics;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Board extends JPanel {
	private int sideLength;
	
	public Board() {
			super();
			
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		
		if (screenWidth > screenHeight) {
			sideLength = screenWidth;
		} else {
			sideLength = screenHeight;
		}
		
		setSize(sideLength, sideLength);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, sideLength, sideLength);
	}
}
