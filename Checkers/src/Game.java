/**
 * The Game class is responsible for handling the logic for moving, capturing, and promoting pieces. 
 * Additionally, this class manages the state of the game. 
 * @author Kevin Cornelius
 */
 public class Game {
	private Board board;
	private int numberOfPlayers;
	private Player player1, player2;
	private Player activePlayer;
	private Piece activePiece;
	
	/**
	 * 
	 * @param numberOfPlayers
	 */
	Game(int numberOfPlayers) {
		this.board = new Board();
	}
	
	/**
	 * Attempts a move requested by a player. 
	 * @param player
	 * @param piece
	 * @param position
	 * @return <code>void</code>
	 * @throws InvalidMoveException
	 */
	void move(Player player, Position startingPosition, Position endingPosition) throws InvalidMoveException {
		/* If the player presented is the active one and the move is valid
		 * update the position of the piece, resolve any side effects, and change the active player
		 * return boolean indicating success or failure.
		 */
		if(activePlayer == player) {
			
		}
	}
}
