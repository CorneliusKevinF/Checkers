public class Game {
	private Board board;
	private int numberOfPlayers;
	private Player player1, player2;
	private Player activePlayer;
	
	Game(int numberOfPlayers) {
		this.board = new Board();
	}

	void move(Player player, Piece piece, Position position) {
		/* If the player presented is the active one and the move is valid
		 * update the position of the piece, resolve any side effects, and change the active player
		 * return boolean indicating success or failure.
		 */
	}
}
