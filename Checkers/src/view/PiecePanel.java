package view;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class PiecePanel extends JPanel {
	int x, y, diameter;
	Color color;
	
	public PiecePanel(int x, int y, int diameter, Color color) {
		this.x = x;
		this.y = y;
		this.diameter = diameter;
		this.color = color;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		
		graphics.setColor(color);
		graphics.fillOval(x, y, diameter, diameter);		
		graphics.setColor(Color.WHITE);
		graphics.drawOval(x, y, diameter, diameter);
	}
}
