package view;

import javax.swing.*;
import java.awt.*;
import model.Position;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel {
	private int sideLength;
	private PositionPanel[][] positionPanels;
	
	public BoardPanel(Insets insets) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		
		if (screenWidth < screenHeight) {
			sideLength = (int) (.9 * (screenWidth - insets.left - insets.right));
		} else {
			sideLength = (int) (.9 * (screenHeight - insets.top - insets.bottom));
		}
		
		setMinimumSize(new Dimension(sideLength, sideLength));
		setMaximumSize(new Dimension (sideLength, sideLength));
		setSize(new Dimension(sideLength, sideLength));
		
		
		positionPanels = new PositionPanel[8][8];
		int positionSideLength = (int) (sideLength / 9);
		int x, y; 
		Color color;
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				x = (int) ((i * positionSideLength) + (positionSideLength / 2));
				y = (int) ((j * positionSideLength) + (positionSideLength / 2));
				
				if(((i + j) % 2) == 0) {
					color = Color.RED;
				} else {
					color = Color.BLACK;
				}
				
				positionPanels[i][j] = new PositionPanel(x, y, positionSideLength, color);
			}
		}
	}

	public void update(Position position) {
		PositionPanel positionPanel = positionPanels[position.getX()][7 - position.getY()];
		
		if(position.hasPiece()) {
			positionPanel.addPiece(position.getPiece().getColor());
		} else {
			positionPanel.removePiece();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, sideLength, sideLength);
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				positionPanels[i][j].paintComponent(graphics);
			}
		}
	}
}
