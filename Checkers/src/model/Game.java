package model;
import java.util.ArrayList;
import java.util.Observable;
import java.awt.Color;

/**
 * <p>
 * The Game class is responsible for handling the Players and the Board. Currently, it implements rules for moving and capturing pieces.
 * </p>
 * <p>
 * <b>Features to Add:</b>
 * <ul>
 * <li>Promotion ("Kinging") Logic</li>
 * <li>Forced Multiple Jumps</li>
 * <li>Victory Conditions (No Moves Remaining)</li>
 * </ul>
 * <p>
 * @author Kevin Cornelius
 */
 public class Game extends Observable {
	private Board board;
	private Player player1, player2, activePlayer;
	
	// TODO Right now there is no requirement that the activePosition matches the Color of the activePlayer.
	// This might lead us to display possible moves for the opposing team. Easily fixed if needed.
	private Position activePosition;
	private ArrayList<Move> validMoves;
	private boolean midJump;
	
	public Game() {
		this.board = new Board();
		this.player1 = new Player(Color.BLACK);
		this.player2 = new Player(Color.RED);
		this.activePlayer = player1;
	}
	
	/* 
	 * Core Game Mechanics:
	 */
	/**
	 * Sets up the Board for a standard Checkers game.
	 */
	public void stageBoard() {
		Position position;
		try {
		// Set the BLACK Pieces.
		for(int i = 0; i < 8; i += 2) {
			for(int j = 0; j < 3; j++) { 
				position = this.board.getPosition(i + (j % 2), j);
				position.addPiece(new Piece(Color.BLACK));
				System.out.println("Attempting to notify observers...");
				setChanged();
				notifyObservers(position);
			}
		}
		
		// Set the RED Pieces.
		for(int i = 0; i < 8; i += 2) {
			for(int j = 5; j < 8; j++) { 
				position = this.board.getPosition(i + (j % 2), j);
				position.addPiece(new Piece(Color.RED));
				System.out.println("Attempting to notify observers...");
				setChanged();
				notifyObservers(position);
			}
		}
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		}
	}
	
	public void move(Position position) throws InvalidMoveException {
		// TODO Considering changing this conditional to redirect the request to setActivePosition().
		// This would insure clicking on a position is always productive and the controller doesn't need to 
		// worry about whether there is an acitvePosition or not. This class would then handle all events that 
		// select a single position. If it is a valid move, the move will be made. Otherwise an attempt to select
		// a position will be made.
		if((activePosition != null) && !activePosition.hasPiece()) throw new InvalidMoveException("Can't move without a Piece selected.");
		
		ArrayList<Move> availableMoves = getAvailableMoves(activePosition);
		int numberOfMoves = availableMoves.size();
		
		if(numberOfMoves == 0) throw new InvalidMoveException("There are no moves available for the Piece selected.");
		
		Position endingPosition;
		
		for(int i = 0; i < numberOfMoves; i++) {
			endingPosition = availableMoves.get(i).getEndingPosition();
			
			if(endingPosition.equals(position)) {
				
				board.movePiece(activePosition, endingPosition);
				
				// Model notifying View
				setChanged();
				notifyObservers(activePosition);
				setChanged();
				notifyObservers(endingPosition);
				
				clearActivePosition();
				
				if(availableMoves.get(i).isJump()) {
					Position jumpedPosition = availableMoves.get(i).getJumpedPosition();
					board.removePiece(jumpedPosition);
					
					// Model notifying View
					setChanged();
					notifyObservers(jumpedPosition);
					
					setActivePosition(endingPosition);
				}
				
				promote(endingPosition);
				
				return;
			}
		}
		
		setActivePosition(position);
	}
	
	/**
	 * If the player presented is the active one and the move is valid
	 * update the position of the piece, resolve any side effects, and change the active player
	 * return boolean indicating success or failure.
	 * @param player The Player attempting the move.
	 * @param route An ArrayList of Positions to be traversed.
	 * @throws InvalidMoveException Indicates an invalid move.
	 * @throws InvalidPositionException Indicates and invalid position as accessed.
	 */
	public void move(Player player, ArrayList<Position> route) throws InvalidMoveException, InvalidPositionException {
		
		Position startingPosition = route.get(0);
		
		if(activePlayer.getID() == player.getID() 
				&& startingPosition.hasPiece()
				&& (startingPosition.getPiece().getColor() == player.getColor())
				&& (route.size() >= 2)) {
			
			
			if(startingPosition.getPiece().isKing()) {
				if(isValidKingMove(route)) {
					
					board.movePiece(startingPosition, route.get(1));
					changeActivePlayer();
					
					//TODO Finish Observable implementation
					setChanged();
					notifyObservers(startingPosition);
					setChanged();
					notifyObservers(route.get(1));
				} else
					// This tests 
					if(isValidKingJump(board, route)) {
					
					ArrayList<Position> jumpedPositions = getJumpedPositions(this.board, route);

					for (Position jumpedPosition : jumpedPositions) {
						jumpedPosition.removePiece();
						
						//TODO Finish Observable implementation
						setChanged();
						notifyObservers(jumpedPosition);
					}			
					
					this.board.movePiece(startingPosition, route.get(route.size() - 1));
					changeActivePlayer();

					//TODO Finish Observable implementation
					setChanged();
					notifyObservers(startingPosition);
					setChanged();
					notifyObservers(route.get(route.size() - 1));
					
				}
			} else if(isValidPawnMove(route)) {
					board.movePiece(startingPosition, route.get(1));
					changeActivePlayer();

					//TODO Finish Observable implementation
					setChanged();
					notifyObservers(startingPosition);
					setChanged();
					notifyObservers(route.get(1));
					
				} else if (isValidPawnJump(board, route)) {
					
					ArrayList<Position> jumpedPositions = getJumpedPositions(this.board, route);
					
					for (Position jumpedPosition : jumpedPositions) {
						jumpedPosition.removePiece();
						
						//TODO Finish Observable implementation
						setChanged();
						notifyObservers(jumpedPosition);
					}
					
					this.board.movePiece(startingPosition, route.get(route.size() - 1));
					changeActivePlayer();

					//TODO Finish Observable implementation
					setChanged();
					notifyObservers(startingPosition);
					setChanged();
					notifyObservers(route.get(route.size() -1));
			}
		} else {
			throw new InvalidMoveException("Move is not valid.");
		}
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
		
		//TODO Remove after debugging
		// System.out.println("Active player has ID: " + this.activePlayer.getID());
	}
	
	/*
	 * Helper Functions:
	 */
	/**
	 * Checks if the given start and end positions for a move are valid for a Pawn.
	 * @param route An ArrayList of Positions to be traversed.
	 * @return A boolean indicating the validity of the move.
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
		
		return ((route.size() == 2)
				&& !route.get(1).hasPiece()
				&& ((nextPosition.getY() == (currentPosition.getY() + directionModifier)) 
				&& ((nextPosition.getX() == (currentPosition.getX() + 1))
				|| (nextPosition.getX() == (currentPosition.getX() - 1)))));
	}
	
	/**
	 * Checks if the given start and end positions for a move are valid for a King.
	 * @param route An ArrayList of Positions to be traversed.
	 * @return A boolean indicating the validity of the move.
	 */
	private boolean isValidKingMove(ArrayList<Position> route) {
		Position currentPosition = route.get(0);
		Position nextPosition = route.get(1);
		
		return ((route.size() == 2)
				&& !route.get(1).hasPiece()
				&& (((nextPosition.getX() == (currentPosition.getX() + 1)) 
				|| (nextPosition.getX() == (currentPosition.getX() - 1)))
				
				&& ((nextPosition.getY() == (currentPosition.getY() + 1))
				|| (nextPosition.getY() == (currentPosition.getY() - 1)))));
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
				validity = (jumpedPosition.hasPiece() && (jumpedPosition.getPiece().getColor() != activePlayer.getColor()));
			}
			
			if(!validity) return validity;
		}
		
		// TODO Finish Mandatory Jump Code 
		/* This needs to be a seperate function.
		currentPosition = route.get(route.size() - 1);
		try { 
			nextPosition = board.getPosition(currentPosition.getX() + 2, currentPosition.getY() + (2 * directionModifier));
			
			ArrayList<Position> manditoryJumpRoute = new ArrayList<Position>();
			manditoryJumpRoute.add(currentPosition);
			manditoryJumpRoute.add(nextPosition);
			
			validity = !isValidPawnJump(board, manditoryJumpRoute);
		} catch (InvalidPositionException e) {}
		
		try {
			nextPosition = board.getPosition(currentPosition.getX() - 2, currentPosition.getY() + (2 * directionModifier));
			
			ArrayList<Position> manditoryJumpRoute = new ArrayList<Position>();
			manditoryJumpRoute.add(currentPosition);
			manditoryJumpRoute.add(nextPosition);
			
			validity = !isValidPawnJump(board, manditoryJumpRoute);
		} catch (InvalidPositionException e) {}
		*/
		
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
	
	/**
	 * 
	 * @param board - The Board on which the jump is being attempted
	 * @param startingPosition - The starting position of the piece making the jump
	 * @param endingPosition - The ending position of the piece making the jump
	 * @return Position - The new position of the jumping piece
	 * @throws InvalidPositionExceptionf
	 */
	private Position getJumpedPosition(Board board, Position startingPosition, Position endingPosition) throws InvalidPositionException {
		try{
			return board.getPosition(((endingPosition.getX() - startingPosition.getX()) / 2) + startingPosition.getX(), 
					(((endingPosition.getY() - startingPosition.getY()) / 2) + startingPosition.getY()));
		}
		catch(InvalidPositionException e){
			throw new InvalidPositionException ("This is not a valid Position.", e);
		}//TODO I'm really not sure if this does anything. I'm pretty sure re-throwing the Exception is pointless.
		
	}

	//TODO Finish switching the Jump model to a multi-step turn, fix overloaded methods.
	public void setActivePosition(int x, int y) {
		try { 
		Position position = board.getPosition(x, y);
		setActivePosition(position);
		} catch (InvalidPositionException e) {
			System.out.println("Position is invalid. Piece cannot be selected.");
			return;
		}
	}
	
	public void setActivePosition(Position position) {
		if(position.hasPiece() && (position.getPiece().getColor() == activePlayer.getColor())) {
				this.activePosition = position;
		}
	}

	public void promote(Position position) {
		if (!position.hasPiece()) return;
		
		Piece piece = position.getPiece();
		Color color = piece.getColor();
		if(!piece.isKing() &&
			(((color == Color.RED) && (position.getY() == 0))
			|| ((color == Color.BLACK) && (position.getY() == 7)))) {
			piece.promote();
			
			// Model notifying View.
			setChanged();
			notifyObservers(position);
		}
	}
	
	public void clearActivePosition() {
		activePosition = null;
	}
	
	private ArrayList<Move> getAvailableMoves(Position startingPosition, int directionModifier, ArrayList<Move> availableMoves) {
		Position nextPosition, jumpedPosition;
		int startingX = startingPosition.getX();
		int startingY = startingPosition.getY();
		Color activeColor = startingPosition.getPiece().getColor();
		
		
		if(!midJump) {
			// Check the move forward and to the left.
			try {
				nextPosition = board.getPosition(startingX - 1, startingY + directionModifier);
				if(!nextPosition.hasPiece()) availableMoves.add(new Move(startingPosition, nextPosition));
			} catch (InvalidPositionException e) {}
			
			// Check the move forward and to the right.
			try {
				nextPosition = board.getPosition(startingX + 1, startingY + directionModifier);
				if(!nextPosition.hasPiece()) availableMoves.add(new Move(startingPosition, nextPosition));
			} catch (InvalidPositionException e) {}
		}
		
		// Check the jump forward and to the left.
		try {
			nextPosition = board.getPosition(startingX - 2, startingY + (2 * directionModifier));		
			jumpedPosition = board.getPosition(startingX - 1, startingY + directionModifier);
			if(jumpedPosition.hasPiece() && (jumpedPosition.getPiece().getColor() != activeColor) && !nextPosition.hasPiece()) availableMoves.add(new Move(startingPosition, nextPosition, jumpedPosition));
		} catch (InvalidPositionException e) {}

		// Check the jump forward and to the right.
		try { 
			nextPosition = board.getPosition(startingX + 2, startingY + (2 * directionModifier));
			jumpedPosition = board.getPosition(startingX + 1, startingY + directionModifier);
			if(jumpedPosition.hasPiece() && (jumpedPosition.getPiece().getColor() != activeColor) && !nextPosition.hasPiece()) availableMoves.add(new Move(startingPosition, nextPosition, jumpedPosition));
		} catch (InvalidPositionException e) {}
		
		// If Piece is king check backwards jumps and moves as well.
		// This is done by switching the direction modifier and running the method again.
		if(startingPosition.getPiece().isKing()) {
			//TODO fix this nonsense.
			// I think this might break everything. But I'm also not sure if Lists can have duplicate items, so maybe it won't break
			// even though it probably should.
			// EDIT: I think this assignment is unnecessary.
			availableMoves = getAvailableMoves(startingPosition, (directionModifier * -1), availableMoves);
		}
		
		return availableMoves;
		
	}
	
	/**
	 * Returns all the legal moves from the given Position for the active Player.
	 * @param startingPosition The position from which the returned Moves will start.
	 * @return All the possible Moves starting from the given Position for the active Player.
	 */
	public ArrayList<Move> getAvailableMoves(Position startingPosition) { 
		ArrayList<Move> availableMoves = new ArrayList<Move>();
		if(!startingPosition.hasPiece()) return availableMoves;
		int directionModifier = 1;
		if(startingPosition.getPiece().getColor() == Color.RED) directionModifier = -1;
		return getAvailableMoves(startingPosition, directionModifier, availableMoves);
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
