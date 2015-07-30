/**
 * The Game class is responsible for handling the logic for moving, capturing, and promoting pieces. 
 * Additionally, this class manages the state of the game. 
 * @author Kevin Cornelius
 */
 public class Game {
	private Board board;
	private Player player1, player2, activePlayer;
	private Piece activePiece;
	
	/**
	 * 
	 * @param numberOfPlayers
	 */
	public Game() {
		this.board = new Board();
		this.activePiece = null;
		this.player1 = new Player(Color.BLACK);
		this.player2 = new Player(Color.RED);
		this.activePlayer = player1;
	}
	
	/**
	 * If the player presented is the active one and the move is valid
	 * update the position of the piece, resolve any side effects, and change the active player
	 * return boolean indicating success or failure.
	 * @param player The Player attempting the move.
	 * @param startingPosition The starting position of the Piece to be moved.
	 * @param endingPosition The ending position of the Piece to be moved.
	 * @return 
	 * @throws InvalidMoveException
	 */
	public void move(Player player, Position startingPosition, Position endingPosition) throws InvalidMoveException, InvalidPositionException {
		/*
		 * Debug Code: Can be commented out or deleted later.
		 */
		/*
		System.out.println("Active player requesting move: " + (activePlayer.getID() == player.getID()));
		System.out.println("Starting position contains a piece: " + startingPosition.hasPiece());
		System.out.println("Ending position unoccupied: " + !endingPosition.hasPiece());
		System.out.println("Active player owns piece to be moved: " + (startingPosition.getPiece().getColor() == player.getColor()));
		*/

		if(activePlayer.getID() == player.getID() 
				&& startingPosition.hasPiece() 
				&& !endingPosition.hasPiece() 
				&& (startingPosition.getPiece().getColor() == player.getColor())) {

			if(startingPosition.getPiece().isKing()) {
				if(isValidKingMove(startingPosition, endingPosition) && (activePiece == null)) {
					
					System.out.println("King move is valid.");
					
					board.movePiece(startingPosition, endingPosition);
					changeActivePlayer();
				} else if(isValidKingJump(board, startingPosition, endingPosition)) {
					
					System.out.println("King jump is valid.");
					
					Position jumpedPosition = getJumpedPosition(this.board, startingPosition, endingPosition);
					
					this.board.movePiece(startingPosition, endingPosition);
					jumpedPosition.removePiece();
					
					/*
					 * This is meant to allow additional moves for the piece which just jumped. Doesn't work ATM.
					 */
					this.activePiece = startingPosition.getPiece();
				}
			} else {
				if(isValidPawnMove(startingPosition, endingPosition) && (activePiece == null)) {
					
					System.out.println("Pawn move is valid.");
					
					board.movePiece(startingPosition, endingPosition);
					changeActivePlayer();
				} else if (isValidPawnJump(board, startingPosition, endingPosition)) {
					
					System.out.println("Pawn jump is valid.");
					
					Position jumpedPosition = getJumpedPosition(this.board, startingPosition, endingPosition);
					
					this.board.movePiece(startingPosition, endingPosition);
					jumpedPosition.removePiece();
					
					/*
					 * This is meant to allow additional moves for the piece which just jumped. Doesn't work ATM.
					 */
					this.activePiece = startingPosition.getPiece();
				}
			}
		} else {
			/*
			 * Debug Code: This else branch can be removed later.
			 */
			System.out.println("Move is invalid.");
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
	 * Checks if the Board and the given start and end Positions for a jump are valid for a Pawn.
	 * @param board The Board on which the jump is being attempted.
	 * @param startingPosition The starting Position of the Piece making the jump.
	 * @param endingPosition The ending Position of the Piece making the jump.
	 * @return Tells whether the proposed jump is valid for a Pawn.
	 */
	private boolean isValidPawnJump(Board board, Position startingPosition, Position endingPosition) throws InvalidPositionException {
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
	 * Checks if the Board and the given start and end Positions for a jump are valid for a King.
	 * @param board The Board on which the jump is being attempted.
	 * @param startingPosition The starting Position of the Piece making the jump.
	 * @param endingPosition The ending Position of the Piece making the jump.
	 * @return Tells whether the proposed jump is valid for a King.
	 */
	private boolean isValidKingJump(Board board, Position startingPosition, Position endingPosition) throws InvalidPositionException {
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
	
	/**
	 * This method exists only to simplify code elsewhere.
	 * @param board the Board on which the jump is being attempted
	 * @param startingPosition The starting Position of the Piece making the jump.
	 * @param endingPosition The ending Position of the Piece making the jump.
	 * @return The Position jumped when a jump from/to the given Positions is made.
	 */
	private Position getJumpedPosition(Board board, Position startingPosition, Position endingPosition) throws InvalidPositionException {
		return board.getPosition(((endingPosition.getX() - startingPosition.getX()) / 2) + startingPosition.getX(), 
								(((endingPosition.getY() - startingPosition.getY()) / 2) + startingPosition.getY()));
	}
	
	/**
	 * Changes the active player, so the game knows who to accept moves from.
	 */
	private void changeActivePlayer() {
		if(this.player1.getID() == this.activePlayer.getID()) {
			this.activePlayer = this.player2;
		} else {
			this.activePlayer = this.player1;
		}
		
		/*
		 * Debug Section: Will comment out or delete later.
		 */
		System.out.println("Acitive player has ID: " + this.activePlayer.getID());
	}
	
	/*
	 * Getters and Setters
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
