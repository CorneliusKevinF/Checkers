package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;
import java.awt.event.*;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PositionPanel extends JPanel {
	int xPosition, yPosition, sideLength;
	PiecePanel piecePanel;
	Color color;

	
	
	public PositionPanel(int i, int j, int sideLength) {
		xPosition = i;
		yPosition = j;
		this.sideLength = sideLength;
		
		int x, y;
		
		x = (int) ((i * sideLength) + (sideLength / 2));
		y = (int) ((j * sideLength) + (sideLength / 2));
		
		if(((xPosition + yPosition) % 2 ) == 0) {
			color = Color.RED;
		} else {
			color = Color.BLACK;
		}
		
		piecePanel = null;
		setLocation(x, y);
		setSize(sideLength, sideLength);
	}
	/*
	PositionPanel(int xOrigin, int yOrigin, int sideLength, Color color) {
		this.xPosition = xOrigin; 
		this.yPosition = yOrigin;
		this.sideLength = sideLength;
		this.color = color;
		piecePanel = null;
		setLocation(xOrigin, yOrigin);
		setSize(sideLength, sideLength);
		//System.out.println("PP: Construction Complete! [x: " + x + ", y: " + y + ", sideLength: " + sideLength + ", color: " + color.toString() + "]");
	}
	*/
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		
		//System.out.println("painting position at: (" + x + ", " + y + "). Sidelength: " + sideLength);
		graphics.setColor(color);
		graphics.fillRect(0, 0, sideLength, sideLength);
		if(piecePanel != null) {
			 piecePanel.paintComponent(graphics);
		}
	}
	
	public void addPiece(PiecePanel piecePanel) {
		this.piecePanel = piecePanel;
		piecePanel.setLocation(getX(), getY());
		piecePanel.setPosition(xPosition, yPosition);
	}
	
	public void addPiece(Color color) {
		//System.out.println("Adding a piece at position (" + x + ", " + y + ").");
		JLayeredPane contentPane = (JLayeredPane) getParent();
		piecePanel = new PiecePanel(xPosition, yPosition, getX(), getY(), sideLength, color);
		contentPane.add(piecePanel, new Integer(3));
	}

	public void deletePiece() {
		JLayeredPane parent = (JLayeredPane) getParent();
		parent.remove(piecePanel);	
		piecePanel = null;
		repaint();
	}
	
	public void removePiece() {
		piecePanel = null;
	}
	
	public void promote() {
		piecePanel.setKing();
	}
	
	public int getXPosition() {
		return xPosition;
	}
	
	public int getYPosition() {
		return yPosition;
	}

}
