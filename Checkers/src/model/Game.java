package model;
import java.util.ArrayList;
import java.util.Hashtable;
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
	//TODO Implement victory conditions.
	Hashtable<Position, ArrayList<Position>> availableMoves;
	private Position activePosition;
	private boolean midJump;
	
	public Game() {
		this.board = new Board();
		this.player1 = new Player(Color.BLACK);
		this.player2 = new Player(Color.RED);
		this.activePlayer = player1;
		availableMoves = new Hashtable<Position, ArrayList<Position>>();
	}
	
	/* 
	 * Core Game Mechanics:
	 */
	/**
	 * Sets up the Board for a standard Checkers game.
	 */
	public void stageBoard() throws InvalidPositionException {
		Position position;
		try {
		// Set the BLACK Pieces.
		for(int i = 0; i < 8; i += 2) {
			for(int j = 0; j < 3; j++) { 
				position = this.board.getPosition(i + (j % 2), j);
				position.addPiece(new Piece(Color.BLACK));
				
				setChanged();				
				notifyObservers(new Update(position, "add"));
			}
		}
		
		// Set the RED Pieces.
		for(int i = 0; i < 8; i += 2) {
			for(int j = 5; j < 8; j++) { 
				position = this.board.getPosition(i + (j % 2), j);
				position.addPiece(new Piece(Color.RED));
				
				setChanged();
				notifyObservers(new Update(position, "add"));
			}
		}
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		}
		
		updateAvailableMoves();
	}
	
	public void move(Position destination) throws InvalidMoveException, InvalidPositionException {
		// A few if statements to see if the desired move is invalid.
		if(activePosition == null) throw new InvalidMoveException("No piece has been selected to move");
		if(!availableMoves.containsKey(activePosition)) throw new InvalidMoveException("No moves available move the selected piece");
		
		ArrayList<Position> possibleMoves = availableMoves.get(activePosition);
		
		if(!possibleMoves.contains(destination)) throw new InvalidMoveException("The desired move is not valid");

		board.movePiece(activePosition, destination);
		
		// Model notifying View
		setChanged();
		notifyObservers(new Update(activePosition, destination));
		
		Position jumpedPosition = getJumpedPosition(activePosition, destination);
		
		// Moves/remove the pertinent pieces.
		if(jumpedPosition != null) {
			board.removePiece(jumpedPosition);
			
			// Model notifying View
			setChanged();
			notifyObservers(new Update(jumpedPosition, "remove"));

			activePosition = destination;
			
			midJump = true;
		} else {
			clearActivePosition();
			changeActivePlayer();
			midJump = false;
		}
		
		promote(destination);
		
		// TODO consider changing the behavior of updateAvailableMoves to only show the moves that can be made in that instant,
		// instead of computing all available moves.
		
		// optimize this to only update moves for unaffected pieces
		availableMoves.clear();
		updateAvailableMoves();

		// This is the code that makes multiple jumps mandatory.
		// if updateAvailableMoves is changed as mentioned above, all of this code should be moved to that method.
		if(midJump) { 
			// TODO Fix Bug: The piece in the middle of a jump sequence is allowed to make moves that aren't jumps.
			ArrayList<Position> jumps = getJumps();

			if(jumps.size() > 0) {
				availableMoves.remove(activePosition);
				availableMoves.put(activePosition, jumps);
			} else {
				clearActivePosition();
				changeActivePlayer();
				midJump = false;
			}
			
		}
		
		return;
	}		
	 
	private ArrayList<Position> getJumps() {
		ArrayList<Position> jumps = new ArrayList<Position>();
		System.out.println("Getting Jumps for Position (" + activePosition.getX() + ", " + activePosition.getY() + ")...");
		if(availableMoves.containsKey(activePosition)) {
			System.out.println("Getting Jumps for Position (" + activePosition.getX() + ", " + activePosition.getY() + ")...");
			
			ArrayList<Position> moves = availableMoves.get(activePosition);
			
			for(int i = 0; i < moves.size(); i++) {
				if(isJump(activePosition, moves.get(i))) jumps.add(moves.get(i));
			}
		}
		
		return jumps;
	}
	
	private boolean isJump(Position start, Position end) {
		int xDisplacement = start.getX() - end.getX();
		int yDisplacement = start.getY() - end.getY();
		
		return((Math.abs(xDisplacement) == 2) && (Math.abs(yDisplacement) == 2));
	}

	private Position getJumpedPosition(Position beginning, Position destination) throws InvalidPositionException {
		int xDisplacement = destination.getX() - beginning.getX();
		int yDisplacement = destination.getY() - beginning.getY();
		
		if((Math.abs(xDisplacement) == 2) && (Math.abs(yDisplacement) == 2)) {
			System.out.println("Move is a jump!");
			return board.getPosition(beginning.getX() + (xDisplacement / 2), beginning.getY() + (yDisplacement / 2));
		} else {
			return null;
		}
	}
	
	/**
	public void move(Position position) throws InvalidMoveException {
		// If there is no Piece selected before the move is requested, throw an Exception.
		if((activePosition == null) || !activePosition.hasPiece()) throw new InvalidMoveException("Can't move without a Piece selected.");
		
		// Get the available moves
		ArrayList<Move> availableMoves = getAvailableMoves(activePosition);
		int numberOfMoves = availableMoves.size();
		System.out.println("m: There are " + numberOfMoves + " available");
		
		// If a jump was just completed remove all non-'jump' Moves from the list of available Moves.
		if(midJump) {
			availableMoves = removeNonJumps(availableMoves);
		}
		// If there are no Moves available, throw an Exception.
		if(numberOfMoves == 0) {
			midJump = false;
			changeActivePlayer();
			throw new InvalidMoveException("There are no moves available for the Piece selected.");
		}
		
		Position endingPosition;
		
		// Check if the Position requested to move to, is one of the available Moves.
		for(int i = 0; i < numberOfMoves; i++) {
			endingPosition = availableMoves.get(i).getEndingPosition();
			
			if(endingPosition.equals(position)) {
				System.out.println("m: (" + position.getX() + ", " + position.getY() + ") is a valid Move.");
				board.movePiece(activePosition, endingPosition);
				
				// Model notifying View
				setChanged();
				notifyObservers(new Update(activePosition, endingPosition));
	
				// If the move requested is a jump, handle additional work.
				if(availableMoves.get(i).isJump()) {
					Position jumpedPosition = availableMoves.get(i).getJumpedPosition();
					board.removePiece(jumpedPosition);
					
					// Model notifying View
					setChanged();
					notifyObservers(new Update(jumpedPosition, "remove"));

					setActivePosition(endingPosition);
					// fixed multi-jump bug?
					midJump = true;
				} else {
					clearActivePosition();
					changeActivePlayer();
					midJump = false;
				}
				
				promote(endingPosition);
				
				availableMoves = getAvailableMoves();
				
				return;
			}
		}
	}
	*/
	
	/**
	 * Changes the active player, so the game knows who to accept moves from.
	 */
	private void changeActivePlayer() {
		if(this.player1.getID() == this.activePlayer.getID()) {
			this.activePlayer = this.player2;
		} else {
			this.activePlayer = this.player1;
		}
	}

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
		//TODO Rewrote this for debugging. Old (shorter) version commented out below.
		// This might be useful for giving guidance to a player making incorrect moves.
		if(!position.hasPiece()) {
			System.out.println("sAP: No Piece at (" + position.getX() + ", " + position.getY() + ")");
		}
		else if(position.getPiece().getColor() != activePlayer.getColor()) {
			System.out.println("sAP: The Piece on the requested Position is of the wrong Color.");
		} else if (!midJump) {
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
			notifyObservers(new Update(position, "promote"));
		}
	}

	public void clearActivePosition() {
		System.out.println("cAP: The activePosition has been cleared.");
		activePosition = null;
	}

	private ArrayList<Position> getAvailableMoves(Position startingPosition, int directionModifier, boolean secondIteration) {
		ArrayList<Position> availableMoves = new ArrayList<Position>();
		Position destination, jumpedPosition;
		int startingX = startingPosition.getX();
		int startingY = startingPosition.getY();
		Color activeColor = startingPosition.getPiece().getColor();
		
		// Check the move forward and to the left.
		try {
			destination = board.getPosition(startingX - 1, startingY + directionModifier);
			if(!destination.hasPiece()) availableMoves.add(destination);
		} catch (InvalidPositionException e) {}
		
		// Check the move forward and to the right.
		try {
			destination = board.getPosition(startingX + 1, startingY + directionModifier);
			if(!destination.hasPiece()) availableMoves.add(destination);
		} catch (InvalidPositionException e) {}
		
		// Check the jump forward and to the left.
		try {
			destination = board.getPosition(startingX - 2, startingY + (2 * directionModifier));		
			jumpedPosition = board.getPosition(startingX - 1, startingY + directionModifier);
			if(jumpedPosition.hasPiece() && (jumpedPosition.getPiece().getColor() != activeColor) && !destination.hasPiece()) availableMoves.add(destination);
		} catch (InvalidPositionException e) {}

		// Check the jump forward and to the right.
		try { 
			destination = board.getPosition(startingX + 2, startingY + (2 * directionModifier));
			jumpedPosition = board.getPosition(startingX + 1, startingY + directionModifier);
			if(jumpedPosition.hasPiece() && (jumpedPosition.getPiece().getColor() != activeColor) && !destination.hasPiece()) availableMoves.add(destination);
		} catch (InvalidPositionException e) {}
		
		// If Piece is king check backwards jumps and moves as well.
		// This is done by switching the direction modifier and running the method again.
		if(startingPosition.getPiece().isKing() && !secondIteration) {
			ArrayList<Position> kingMoves = getAvailableMoves(startingPosition, (directionModifier * -1), true);
			availableMoves.addAll(kingMoves);
		}
		
		return availableMoves;
	}
	
	/**
	 * Returns all the legal moves from the given Position for the active Player.
	 * @param startingPosition The position from which the returned Moves will start.
	 * @return All the possible Moves starting from the given Position for the active Player.
	 */
	public ArrayList<Position> getAvailableMoves(Position startingPosition) { 
		ArrayList<Position> availableMoves = new ArrayList<Position>();
		if(!startingPosition.hasPiece()) return availableMoves;
		int directionModifier = 1;
		if(startingPosition.getPiece().getColor() == Color.RED) directionModifier = -1;
		return getAvailableMoves(startingPosition, directionModifier, false);
	}

	public void updateAvailableMoves() throws InvalidPositionException { 
		Position startingPosition;
		ArrayList<Position> moves = new ArrayList<Position>();
		// iterate over the black spaces on the board.
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j+=2) { 
				startingPosition = board.getPosition(i, j + (i % 2));
				moves = getAvailableMoves(startingPosition);
				
				if(moves.size() > 0) {
					availableMoves.put(startingPosition, moves);		
					System.out.println("Positon (" + startingPosition.getX() + ", " + startingPosition.getY() + ") has " + moves.size() + " available moves.");
				}
			}
		}
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
	
	public Position getActivePosition() {
		return activePosition;
	}
	
	public Player getActivePlayer() {
		return this.activePlayer;
	}
	
	public Board getBoard() {
		return this.board;
	}

 }