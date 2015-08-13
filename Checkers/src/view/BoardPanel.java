package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.Position;
import model.Update;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel {
	private int sideLength;
	protected PositionPanel[][] positionPanels;
	
	public BoardPanel(int sideLength) {
		this.sideLength = sideLength;
		initGUI();
		// System.out.println("BP: Construction Complete! [sideLength: " + sideLength + "]");
	}
	
	private void initGUI() {
		setSize(new Dimension(sideLength, sideLength));
		System.out.println("Board is " + sideLength + " x " + sideLength + ".");
	}

	public void update(Object observed) {
		Update update = (Update) observed;
		Position position, endingPosition;
		
		position = (Position) update.get(0);
	
		PositionPanel positionPanel = positionPanels[position.getX()][7 - position.getY()];
		
		if(update.get(1) instanceof String) {
			String action = (String) update.get(1);
			
			switch (action) {
				case "add":
					MouseListener[] listeners = getMouseListeners();
					positionPanel.addPiece(position.getPiece().getColor());
					for(int i = 0; i < listeners.length; i++) {
						positionPanel.piecePanel.addMouseListener(listeners[i]);
					}
					break;
				case "remove":
					positionPanel.deletePiece();
					break;
				case "promote":
					positionPanel.promote();
					break;
				default:
					
					break;
			}
		} else {
			endingPosition = (Position) update.get(1);
			
			PositionPanel endingPositionPanel = positionPanels[endingPosition.getX()][7 - endingPosition.getY()];
			
			endingPositionPanel.addPiece(positionPanel.piecePanel);
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
	
	protected void setPositionPanels() {
		positionPanels = new PositionPanel[8][8];
		int positionSideLength = (int) (sideLength / 9);
		
		PositionPanel positionPanel;
		JLayeredPane contentPane = (JLayeredPane) getParent();
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				positionPanel = new PositionPanel(i, j, positionSideLength);
				//positionPanel = new PositionPanel(x, y, positionSideLength, color);
				positionPanels[i][j] = positionPanel;
				contentPane.add(positionPanel, new Integer(2));
			}
		}
	}
	public void printPositionPanels() {
		System.out.println("Print Locations for Position Panels...");
		PositionPanel positionPanel;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				positionPanel = positionPanels[i][j];
				if(positionPanel != null) {
					System.out.println("BP: Position: (" + positionPanel.getXPosition() + ", " + positionPanel.getYPosition() + ") at (" + i + ", " + j + ")");
				} else {
					System.out.println("BP: No valid PositionPanel at (" + i + ", " + j + ")");
				}
			}
		}
		
		System.out.println("Printing Complete.");
	}
	
	@Override 
	public void addMouseListener(MouseListener controller) {
		super.addMouseListener(controller);
		PositionPanel positionPanel;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				positionPanel = positionPanels[i][j];
				positionPanel.addMouseListener(controller);
			}
		}
	}
}
