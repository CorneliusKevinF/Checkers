package view;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class PiecePanel extends JPanel {
	private int xPosition, yPosition, diameter;
	Color color;
	boolean isKing;
	
	public PiecePanel(int xPosition, int yPosition, int xOrigin, int yOrigin, int sideLength, Color color) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.isKing = false;
		
		this.diameter = (int) (.8 * sideLength);
		this.color = color;
		setSize(sideLength, sideLength);
		setLocation(xOrigin, yOrigin);
		setOpaque(false);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		int offset = (int) ((getWidth() - diameter) / 2);
		//System.out.println("Painting a piece at : (" + x + ", " + y + ").");
		
		graphics.setColor(color);
		graphics.fillOval(offset, offset, diameter, diameter);		
		graphics.setColor(Color.WHITE);
		graphics.drawOval(offset, offset, diameter, diameter);

		if(isKing) {
			graphics.drawOval(offset * 3, offset *3, (int) (diameter / 2), (int) (diameter / 2));
		}
	}
	
	
	public void setKing() {
		isKing = true;
	}
	
	public void setPosition(int xPosition, int yPosition) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}

	public int getXPosition() {
		return xPosition;
	}
	
	public int getYPosition() {
		return yPosition;
	}

}
