package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PositionPanel extends JPanel {
	int x, y;
	
	PositionPanel(int x, int y, int sidelength) {
		this.x = x; 
		this.y = y;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		
		//TODO  paint.
	}
}
