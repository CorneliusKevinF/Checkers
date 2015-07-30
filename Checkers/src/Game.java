import java.util.ArrayList;

/**
 * The Game class is responsible for handling the logic for moving, capturing, and promoting pieces. 
 * Additionally, this class manages the state of the game. 
 * @author Kevin Cornelius
 */
 public class Game {
	private Board board;
	private Player player1, player2, activePlayer;
	
	/**
	 * 
	 * @param numberOfPlayers
	 */
	public Game() {
		this.board = new Board();
		this.player1 = new Player(Color.BLACK);
		this.player2 = new Player(Color.RED);
		this.activePlayer = player1;
	}
	
	public void stageBoard() throws InvalidPositionException {
		// Set the BLACK Pieces.
		for(int i = 0; i < 8; i += 2) {
			for(int j = 0; j < 3; j++) { 
				this.board.getPosition(i + (j % 2), j).addPiece(new Piece(Color.BLACK));
			}
		}
		
		// Set the RED Pieces.
		for(int i = 0; i < 8; i += 2) {
			for(int j = 5; j < 8; j++) { 
				this.board.getPosition(i + (j % 2), j).addPiece(new Piece(Color.RED));
			}
		}
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
	public void move(Player player, ArrayList<Position> route) throws InvalidMoveException, InvalidPositionException {
		/*
		 * Debug Code: Can be commented out or deleted later.
		 */
		/*
		System.out.println("Active player requesting move: " + (activePlayer.getID() == player.getID()));
		System.out.println("Starting position contains a piece: " + startingPosition.hasPiece());
		System.out.println("Ending position unoccupied: " + !endingPosition.hasPiece());
		System.out.println("Active player owns piece to be moved: " + (startingPosition.getPiece().getColor() == player.getColor()));
		*/
		
		Position startingPosition = route.get(0);
		
		if(activePlayer.getID() == player.getID() 
				&& startingPosition.hasPiece()
				&& (startingPosition.getPiece().getColor() == player.getColor())
				&& (route.size() >= 2)) {

			if(startingPosition.getPiece().isKing()) {
				if((route.size() == 2) && isValidKingMove(route) && !route.get(1).hasPiece()) {
					
					System.out.println("King move is valid.");
					
					board.movePiece(startingPosition, route.get(1));
					changeActivePlayer();
				} else if(isValidKingJump(board, route)) {
					
					System.out.println("King jump is valid.");
					
					ArrayList<Position> jumpedPositions = getJumpedPositions(this.board, route);

					for (Position jumpedPosition : jumpedPositions) {
						jumpedPosition.removePiece();			
					}			
					
					this.board.movePiece(startingPosition, route.get(route.size() - 1));
				}
			} else {
				if((route.size() == 2) && isValidPawnMove(route) && !route.get(1).hasPiece()) {
					
					System.out.println("Pawn move is valid.");
					
					board.movePiece(startingPosition, route.get(1));
					changeActivePlayer();
				} else if (isValidPawnJump(board, route)) {
					
					System.out.println("Pawn jump is valid.");
					
					ArrayList<Position> jumpedPositions = getJumpedPositions(this.board, route);
					
					for (Position jumpedPosition : jumpedPositions) {
						jumpedPosition.removePiece();
					}
					
					this.board.movePiece(startingPosition, route.get(route.size() - 1));
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
	private boolean isValidPawnMove(ArrayList<Position> route) {
		int directionModifier;
		Position currentPosition = route.get(0);
		Position nextPosition = route.get(1);
		
		if(currentPosition.getPiece().getColor() == Color.BLACK) {
			directionModifier = 1;
		} else {
			directionModifier = -1;
		}
		
		return ((nextPosition.getY() == (currentPosition.getY() + directionModifier)) 
				&& ((nextPosition.getX() == (currentPosition.getX() + 1))
				|| (nextPosition.getX() == (currentPosition.getX() - 1))));
	}
	
	/**
	 * Checks if the given start and end positions for a move are valid for a King.
	 */
	private boolean isValidKingMove(ArrayList<Position> route) {
		Position currentPosition = route.get(0);
		Position nextPosition = route.get(1);
		
		return (((nextPosition.getX() == (currentPosition.getX() + 1)) 
				|| (nextPosition.getX() == (currentPosition.getX() - 1)))
				&& ((nextPosition.getY() == (currentPosition.getY() + 1))
				|| (nextPosition.getY() == (currentPosition.getY() - 1))));
	}
	
	/**
	 * Checks if the Board and the given start and end Positions for a jump are valid for a Pawn.
	 * @param board The Board on which the jump is being attempted.
	 * @param startingPosition The starting Position of the Piece making the jump.
	 * @param endingPosition The ending Position of the Piece making the jump.
	 * @return Tells whether the proposed jump is valid for a Pawn.
	 */
	private boolean isValidPawnJump(Board board, ArrayList<Position> route) throws InvalidPositionException {
		int directionModifier;
		Position currentPosition, nextPosition, jumpedPosition;
		boolean validity = false;
		
		if(route.get(0).getPiece().getColor() == Color.BLACK) {
			directionModifier = 1;
		} else {
			directionModifier = -1;
		}
		
		
		for(int i = 0; i < (route.size() - 1); i++) {
			currentPosition = route.get(i);
			nextPosition = route.get(i + 1);

			if((nextPosition.getY() == (currentPosition.getY() + (2 * directionModifier))) 
				&& ((nextPosition.getX() == (currentPosition.getX() + 2)) 
				|| (nextPosition.getX() == (currentPosition.getX() - 2)))) {
				
				jumpedPosition = getJumpedPosition(board, currentPosition, nextPosition);
				validity = (jumpedPosition.hasPiece() && (jumpedPosition.getPiece().getColor() != currentPosition.getPiece().getColor()));
			}
			
			if(!validity) return validity;
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
	private boolean isValidKingJump(Board board, ArrayList<Position> route) throws InvalidPositionException {
		Position currentPosition, nextPosition, jumpedPosition;
		boolean validity = false;

		for(int i = 0; i < (route.size() - 1); i++) {
			currentPosition = route.get(i);
			nextPosition = route.get(i + 1);

			if(((nextPosition.getY() == (currentPosition.getY() + 2))
					|| (nextPosition.getY() == (currentPosition.getY() - 2)))
				&& ((nextPosition.getX() == (currentPosition.getX() + 2)) 
				|| (nextPosition.getX() == (currentPosition.getX() - 2)))) {
				
				jumpedPosition = getJumpedPosition(board, currentPosition, nextPosition);
				validity = (jumpedPosition.hasPiece() && (jumpedPosition.getPiece().getColor() != currentPosition.getPiece().getColor()));
			
				if(!validity) return validity;
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
	private ArrayList<Position> getJumpedPositions(Board board, ArrayList<Position> route) throws InvalidPositionException {
		Position currentPosition, nextPosition;
		ArrayList<Position> jumpedPositions = new ArrayList<Position>();
		
		for(int i = 0; i < (route.size() -1); i++) {
			currentPosition = route.get(i);
			nextPosition = route.get(i + 1);
			
			jumpedPositions.add(i, board.getPosition(((nextPosition.getX() - currentPosition.getX()) / 2) + currentPosition.getX(), 
								(((nextPosition.getY() - currentPosition.getY()) / 2) + currentPosition.getY())));
		}
		
		return jumpedPositions;
	}
	
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
	
	public Player getActivePlayer() {
		return this.activePlayer;
	}
	
	public Board getBoard() {
		return this.board;
	}
}
