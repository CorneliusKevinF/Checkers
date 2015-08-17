package controller;

import model.*;
import view.*;
import java.awt.event.*;


public class GameController extends MouseAdapter {
	GameView gameView;
	Game game;
	PiecePanel activePiece;
	
	public GameController () {
		
	}
	
	public void setGameView(GameView gameView) {
		this.gameView = gameView;
		//TODO work on keybindings
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource() instanceof PositionPanel) {
			PositionPanel positionPanel = (PositionPanel) e.getSource();
			try {
				game.move(game.getBoard().getPosition(positionPanel.getXPosition(), 7 - positionPanel.getYPosition()));
			} catch (InvalidMoveException exc) {
				System.out.println(exc.getMessage());
			} catch (InvalidPositionException exc) {
				System.out.println(exc.getMessage());
			}
			
		} else if(e.getSource() instanceof BoardPanel) {
		} else if(e.getSource() instanceof PiecePanel) {
			PiecePanel panel = (PiecePanel) e.getSource();
			try { 
				game.setActivePosition(game.getBoard().getPosition(panel.getXPosition(), 7 - panel.getYPosition()));
			} catch (InvalidPositionException exc) {
				System.out.println(exc.getMessage());
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
}

