package graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Position extends JPanel {
	int x, y;
	Piece piece;
	int timesDrawn;
	
	public Position(int x, int y) {
		super();
		setLayout(null);
		this.x = x;
		this.y = y;
		piece = null;
		setOpaque(true);
		timesDrawn = 0;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//super.paintComponent(g);
		
		Graphics2D graphics = (Graphics2D) g;
		
		if(((x + y) % 2) == 0) {
			if(timesDrawn%2==0){
				graphics.setColor(Color.BLUE);
			}
			else{
				graphics.setColor(Color.RED);
			}
		} else {
			graphics.setColor(Color.BLACK);
		}
		
		graphics.fillRect((50 * x) + 25, (50 * y) + 25, 50, 50);
		
		if(piece != null) {
			System.out.println("Drawing a Piece at: (" + x + ", " + y + ")");
			piece.paintComponent(graphics, (50 * x) + 25 + 5, (50 * y) + 25 + 5);
		}
		timesDrawn++;
	}
	
	//TODO not working, can't paint the piece.
	public void setPiece(Piece piece) {
		this.piece = piece;
		repaint();
	}
	
	public void removePiece() {
		this.piece = null;
		repaint();
	}
	
	public boolean hasPiece() {
		return !(piece == null);
	}
	
	public Piece getPiece() {
		return piece;
	}
}
