package temp;

import java.awt.*;

import controller.GameController;
import model.*;
import view.*;

public class GraphicsTest {	
    public static void main(String[] args) {
        
    	/*
    	EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
            	GameView gameView = new GameView();

        		Game game = new Game();
            	game.addObserver(gameView);
            	
            	try {
            		System.out.println("Staging the board...");
            		game.stageBoard();
                } catch (InvalidPositionException e) {
            		
            	}
            	
            	gameView.getFrame().repaint();
            }
        });
        */
    	
    	GameView gameView = new GameView();
    	
    	try {
    	Thread.sleep(3000);
    	} catch (InterruptedException e) {
    		System.out.println("Sleep interrupted.");
    	}
		Game game = new Game();

		GameController gameController = new GameController();
		gameController.setGame(game);
		gameController.setGameView(gameView);
		
    	game.addObserver(gameView);
    	
    	gameView.addController(gameController);
    		game.stageBoard();
    	
    	gameView.getFrame().revalidate();
    	//gameView.getFrame().repaint();
    }
    	
    	
}