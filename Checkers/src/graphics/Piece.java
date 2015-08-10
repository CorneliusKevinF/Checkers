package graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Piece extends JPanel {
	Color color;
	
	public Piece(Color color) {
		super();
	
		this.color = color;
		setOpaque(false);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		
		graphics.setColor(color);
//		g.fillOval(x, y, width, height);
	}
	
	public void paintComponent(Graphics g, int x, int y) {
		Graphics2D graphics = (Graphics2D) g;
		
		graphics.setColor(color);
		graphics.fillOval(x, y, 40, 40);
		graphics.setColor(Color.WHITE);
		graphics.drawOval(x, y, 40, 40);
		System.out.println("Piece Drawn!");
	}
}
