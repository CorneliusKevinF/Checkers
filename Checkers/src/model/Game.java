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
		if(activePosition == null) {
			setActivePosition(position);
			return;
		}
		
		// TODO Right now the activePostion is set under strict conditions that guarantee the presence of a Piece of the
		// activePlayer's Color on the request Position so this code does nothing except lookout for bugs.
		if(!activePosition.hasPiece()) throw new InvalidMoveException("Can't move without a Piece selected.");
		
		ArrayList<Move> availableMoves = getAvailableMoves(activePosition);
		int numberOfMoves = availableMoves.size();
		System.out.println("m: There are " + numberOfMoves + " available");
		
		if(numberOfMoves == 0) throw new InvalidMoveException("There are no moves available for the Piece selected.");
		
		Position endingPosition;
		
		for(int i = 0; i < numberOfMoves; i++) {
			endingPosition = availableMoves.get(i).getEndingPosition();
			
			if(endingPosition.equals(position)) {
				System.out.println("m: (" + position.getX() + ", " + position.getY() + ") is a valid Move.");
				board.movePiece(activePosition, endingPosition);
				
				// Model notifying View
				setChanged();
				notifyObservers(activePosition);
				setChanged();
				notifyObservers(endingPosition);
				
				clearActivePosition();
				changeActivePlayer();
				
				if(availableMoves.get(i).isJump()) {
					Position jumpedPosition = availableMoves.get(i).getJumpedPosition();
					board.removePiece(jumpedPosition);
					
					// Model notifying View
					setChanged();
					notifyObservers(jumpedPosition);
					
					//TODO remove doubling of code. changing the active player excessively could affect something like
					// a turn counter later on in development.
					changeActivePlayer();
					setActivePosition(endingPosition);
				}
				
				promote(endingPosition);
				
				return;
			}
		}
		
		setActivePosition(position);
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

	//TODO Finish switching the Jump model to a multi-step turn, fix overloaded methods.
	public void setActivePosition(int x, int y) {
		try { 
		Position position = board.getPosition(x, y);
		setActivePosition(position);
		} catch (InvalidPositionException e) {
			System.out.println("Position is invalid. Piece cannot be selected.");
			clearActivePosition();
			return;
		}
	}
	
	public void setActivePosition(Position position) {
		//TODO Rewrote this for debugging. Old (shorter) version commented out below.
		if(!position.hasPiece()) {
			System.out.println("sAP: No Piece at the requested location.");
			clearActivePosition();
		}
		else if(position.getPiece().getColor() != activePlayer.getColor()) {
			System.out.println("sAP: The Piece on the requested Position is of the wrong Color.");
			clearActivePosition();
		} else {
			System.out.println("sAP: Setting activePosition to (" + position.getX() + ", " + position.getY() + ").");
			this.activePosition = position;
		}
		/*
		if(position.hasPiece() && (position.getPiece().getColor() == activePlayer.getColor())) {
				this.activePosition = position;
		} else {
			clearActivePosition();
		}
		*/
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
	
	public Position getActivePosition() {
		return activePosition;
	}
	
	public boolean hasActivePosition() {
		return (activePosition != null);
	}
	
	public void clearActivePosition() {
		System.out.println("cAP: The activePosition has been cleared.");
		activePosition = null;
	}
	
	public ArrayList<Move> getAllAvailableMoves(Color color) { 
		ArrayList<Move> moves = new ArrayList<Move>();
		Position position;
		try { 
			for(int i = 0; i < 8; i++) { 
				for(int j = 0; j < 8; j++) {
					position = board.getPosition(i, j);
					if(position.hasPiece() && (position.getPiece().getColor() == color)) {
						moves.addAll(getAvailableMoves(position));	
					}
				}
			}
		} catch (InvalidPositionException e) {
			System.out.println("gAAM: Board is not of standard size.");
		}
		
		return moves;
	}
	
	public ArrayList<Move> getJumps(ArrayList<Move> moves) { 
		ArrayList jumps = new ArrayList<Move>();
		int numberOfMoves = moves.size();
		for(int i = 0; i < numberOfMoves; i++) {
			
		}
		return jumps;
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
