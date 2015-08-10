package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {
   
	Position[][] positions;
	static int ID = 1;
	
	
	//TODO Remove test code. 
    private final int DELAY = 150;
	Timer timer;
	
	
	public Board() {
		super();

		setLayout(null);
		setSize(new Dimension(450, 450));

		System.out.println("Board #" + ID + "'s Dimensions\nHeight: " + getHeight() + "\nWidth: " + getWidth());
		
		setOpaque(true);
		
		positions = new Position[8][8];
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				positions[i][7 - j] = new Position(i, j);
				add(positions[i][7 - j]);
			}
		}
		

		System.out.println("Board #" + ID + "'s Dimensions\nHeight: " + getHeight() + "\nWidth: " + getWidth());
		
		ID++;
		
		
		
		//TODO Remove test code.
        timer = new Timer(DELAY, this);
        timer.start();
		
        
		
	}

	@Override
	public void paintComponent(Graphics g) {
		//super.paintComponent(g);
		
		Graphics2D graphics = (Graphics2D) g;
		
		graphics.setColor(Color.WHITE);
		System.out.println("Board #" + ID + "'s Dimensions\nHeight: " + getHeight() + "\nWidth: " + getWidth());
		graphics.fillRect(0, 0, getWidth(), getHeight());
		
		//Container container = SwingUtilities.windowForComponent(this);
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				positions[i][j].paintComponent(graphics);
			}
		}
	}
	
	public void addPiece(int x, int y, Color color) {
		System.out.println("Adding a new Piece");
		positions[x][y].setPiece(new Piece(color));
	}
	
	public void removePiece(int x, int y) {
		System.out.println("Removing a Piece");
		positions[x][y].removePiece();
	}
	
	public Position getPosition(int x, int y) {
		return positions[x][y];
	}
	
	
	//TODO TBR
	public Timer getTimer() {
		return timer;
	}
	
	// TODO Remove after testing.
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
