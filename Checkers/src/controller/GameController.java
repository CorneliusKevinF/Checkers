package controller;

import model.*;
import view.*;
import java.awt.event.*;


public class GameController implements MouseListener {
	GameView gameView;
	Game game;
	
	public GameController () {
		
	}
	
	public void setGameView(GameView gameView) {
		this.gameView = gameView;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Event Detected!");
		if(e.getSource() instanceof PositionPanel) {
			PositionPanel positionPanel = (PositionPanel) e.getSource();
			System.out.println("Mouse Clicked on Position: (" + positionPanel.getXPosition() + ", " + positionPanel.getYPosition() + ".");
		} else if(e.getSource() instanceof BoardPanel) {
			System.out.println("Mouse Clicked on Board.");
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
