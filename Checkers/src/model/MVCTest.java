package model;

import view.*;

public class MVCTest {

	public static void main(String[] args) {
		Game game = new Game();
		GameView gameView = new GameView();
		
		game.addObserver(gameView);
	}

}
