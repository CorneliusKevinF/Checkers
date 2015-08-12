package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PositionPanel extends JPanel {
	int xPosition, yPosition, sideLength;
	PiecePanel piecePanel;
	Color color;
	
	PositionPanel(int x, int y, int sideLength, Color color) {
		this.xPosition = x; 
		this.yPosition = y;
		this.sideLength = sideLength;
		this.color = color;
		piecePanel = null;
		
		System.out.println("PP: Construction Complete! [x: " + x + ", y: " + y + ", sideLength: " + sideLength + ", color: " + color.toString() + "]");
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		
		//System.out.println("painting position at: (" + x + ", " + y + "). Sidelength: " + sideLength);
		graphics.setColor(color);
		graphics.fillRect(xPosition, yPosition, sideLength, sideLength);
		if(piecePanel != null) {
			 piecePanel.paintComponent(graphics);
		}
	}
	
	public void addPiece(Color color) {
		//System.out.println("Adding a piece at position (" + x + ", " + y + ").");
		
		piecePanel = new PiecePanel((int) (xPosition + (sideLength * .1)), (int) (yPosition + (sideLength * .1)), (int) (sideLength * .8), color);
	}

	public void removePiece() {
		piecePanel = null;
	}
	
	public int getXPosition() {
		return xPosition;
	}
	
	public int getYPosition() {
		return yPosition;
	}
	
	
	@Override 
	public void addMouseListener(MouseListener controller) {
		super.addMouseListener(controller);
		
		System.out.println("Mouse listener added successfully at (" + xPosition + ", " + yPosition + ")");
	}
}
