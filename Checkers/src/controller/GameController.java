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
		//TODO work on keybindings
		/*
		gameView.getFrame().getRootPane().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "exit");
		gameView.getFrame().getRootPane().getActionMap().put("exit", new exitGame());
		*/
	}
	/*
	class exitGame extends AbstractAction {
		public exitGame(){}

	    public void actionPerformed(ActionEvent e) {
				gameView.getFrame().dispatchEvent(new WindowEvent(gameView.getFrame(), WindowEvent.WINDOW_CLOSING));
	    }
	}
	*/
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() instanceof PositionPanel) {
			PositionPanel positionPanel = (PositionPanel) e.getSource();
			try {
				game.move(game.getBoard().getPosition(positionPanel.getXPosition(), 7 - positionPanel.getYPosition()));
				//gameView.getFrame().revalidate();
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
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
