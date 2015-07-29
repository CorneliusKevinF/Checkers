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
		this.numberOfPlayers = numberOfPlayers;
		this.board = new Board();
		activePiece = null;
		this.player1 = new Player();
		this.player2 = new Player();
		activePlayer = player1;
	}
	
	/**
	 * Attempts a move requested by a player. 
	 * @param player The Player attempting the move.
	 * @param startingPosition The starting position of the Piece to be moved.
	 * @param endingPosition The ending position of the Piece to be moved.
	 * @return 
	 * @throws InvalidMoveException
	 */
	public void move(Player player, Position startingPosition, Position endingPosition) throws InvalidMoveException {
		/* If the player presented is the active one and the move is valid
		 * update the position of the piece, resolve any side effects, and change the active player
		 * return boolean indicating success or failure.
		 */
		boolean moveSuccessful = false;
		
		/* This conditional ensures the player making the move is the active one and the piece they 
		 * wish to move is his or hers. It also makes sure the desired destination for the piece is unoccupied.
		 */
		if(activePlayer.getID() == player.getID() 
				&& startingPosition.hasPiece() 
				&& !endingPosition.hasPiece() 
				&& (startingPosition.getPiece().getColor() == player.getColor())) {

			if(startingPosition.getPiece().isKing()) {
				if(isValidKingMove(startingPosition, endingPosition) && (activePiece == null)) {
					moveSuccessful = true;
				} else if(isValidKingJump(board, startingPosition, endingPosition)) {
					moveSuccessful = true;
					this.activePiece = startingPosition.getPiece();
				}
			} else {
				if(isValidPawnMove(startingPosition, endingPosition) && (activePiece == null)) {
					moveSuccessful = true;
				} else if (isValidPawnJump(board, startingPosition, endingPosition)) {
					moveSuccessful = true;
					this.activePiece = startingPosition.getPiece();
				}
			}
		}
		
	}

	/**
	 * Checks if the given start and end positions for a move are valid for a Pawn.
	 */
	private boolean isValidPawnMove(Position startingPosition, Position endingPosition) {
		int directionModifier;
		
		if(startingPosition.getPiece().getColor() == Color.BLACK) {
			directionModifier = 1;
		} else {
			directionModifier = -1;
		}
		
		return ((endingPosition.getY() == (startingPosition.getY() + directionModifier)) 
				&& ((endingPosition.getX() == (startingPosition.getX() + 1))
				|| (endingPosition.getX() == (startingPosition.getX() - 1))));
	}
	
	/**
	 * Checks if the given start and end positions for a move are valid for a King.
	 */
	private boolean isValidKingMove(Position startingPosition, Position endingPosition) {
		return (((endingPosition.getX() == (startingPosition.getX() + 1)) 
				|| (endingPosition.getX() == (startingPosition.getX() - 1)))
				&& ((endingPosition.getY() == (startingPosition.getY() + 1))
				|| (endingPosition.getY() == (startingPosition.getY() - 1))));
	}
	
	/**
	 * Checks if the board and the given start and end positions for a jump are valid for a Pawn.
	 */
	private boolean isValidPawnJump(Board board, Position startingPosition, Position endingPosition) {
		int directionModifier;
		int startX = startingPosition.getX();
		int startY = startingPosition.getY();
		int endX = endingPosition.getX();
		int endY = endingPosition.getY();
		Position jumpedPosition;
		boolean validity = false;
		
		if(startingPosition.getPiece().getColor() == Color.BLACK) {
			directionModifier = 1;
		} else {
			directionModifier = -1;
		}
		
		if((endY == (startY + (2 * directionModifier))) 
				&& ((endX == (startX + 2)) || (endX == (startX - 2)))) {
			jumpedPosition = getJumpedPosition(board, startingPosition, endingPosition);
			if(jumpedPosition.hasPiece() && (jumpedPosition.getPiece().getColor() != startingPosition.getPiece().getColor())) {
				validity = true;
			}
		}
		
		return validity;
	}
	
	/**
	 * Checks if the board and the given start and end positions for a jump are valid for a King.
	 */
	private boolean isValidKingJump(Board board, Position startingPosition, Position endingPosition) {
		int startX = startingPosition.getX();
		int startY = startingPosition.getY();
		int endX = endingPosition.getX();
		int endY = endingPosition.getY();
		Position jumpedPosition;
		boolean validity = false;
		
		if(((endX == (startX + 2)) || (endX == (startX - 2))) 
				&& ((endY == (startY + 2)) || (endY == (startY - 2)))) {
			jumpedPosition = getJumpedPosition(board, startingPosition, endingPosition);
			if(jumpedPosition.hasPiece() && (jumpedPosition.getPiece().getColor() != startingPosition.getPiece().getColor())) {
				validity = true;
			}
		}
		
		return validity;
	}
	
	private Position getJumpedPosition(Board board, Position startingPosition, Position endingPosition) {
		return board.getPosition(((endingPosition.getX() - startingPosition.getX()) / 2) + startingPosition.getX(), 
								(((endingPosition.getY() - startingPosition.getY()) / 2) + startingPosition.getY()));
	}
	
	/*
	 * Gettters and Setters
	 */
	public Player getPlayer1() {
		return player1;
	}
	
	public Player getPlayer2() {
		return player2;
	}
	
	public Board getBoard() {
		return this.board;
	}
}
