package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PositionPanel extends JPanel {
	int x, y, sideLength;
	PiecePanel piecePanel;
	Color color;
	
	PositionPanel(int x, int y, int sideLength, Color color) {
		this.x = x; 
		this.y = y;
		this.sideLength = sideLength;
		this.color = color;
		piecePanel = null;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		
		System.out.println("painting position at: (" + x + ", " + y + "). Sidelength: " + sideLength);
		graphics.setColor(color);
		graphics.fillRect(x, y, sideLength, sideLength);
		if(piecePanel != null) {
			 piecePanel.paintComponent(graphics);
		}
	}
	
	public void addPiece(Color color) {
		piecePanel = new PiecePanel((int) (x + (sideLength / 2)), (int) (y + (sideLength / 2)), (int) (sideLength * .8), color);
	}

	public void removePiece() {
		piecePanel = null;
	}
}
