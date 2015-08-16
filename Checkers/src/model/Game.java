package model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Observable;
import java.awt.Color;

/**
 * <p>
 * The Game class is responsible for handling the Players and the Board.
 * </p>
 * @author Kevin Cornelius
 */
 public class Game extends Observable {
	private Board board;
	private Player player1, player2, activePlayer;
	Hashtable<Position, ArrayList<Position>> availableMoves;
	private Position activePosition;
	private boolean midJump, jumpMandatory;
	
	public Game() {
		this.board = new Board();
		this.player1 = new Player(Color.BLACK);
		this.player2 = new Player(Color.RED);
		this.activePlayer = player1;
		availableMoves = new Hashtable<Position, ArrayList<Position>>();
		jumpMandatory = false;
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
		
		crown(destination);
		
		availableMoves.clear();
		updateAvailableMoves();

		// This is the code that makes multiple jumps mandatory.
		// if updateAvailableMoves is changed as mentioned above, all of this code should be moved to that method.
		if(midJump && (availableMoves.size() == 0)) {
			clearActivePosition();
			changeActivePlayer();
			midJump = false;

			updateAvailableMoves();
		}
		
		if(availableMoves.size() == 0) {
			endGame();
		}
	}
	
	private void endGame() {
		Player winner;
		
		if(activePlayer.getID() == player1.getID()) { 
			winner = player2;
		} else {
			winner = player1;
		}
		
		System.out.println("Congratulations, " + winner.getName() + "! You won!");
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
		}
	}
	
	public void setActivePosition(Position position) {
		// This might be useful for giving guidance to a player making incorrect moves.
		if(!position.hasPiece()) {
			System.out.println("sAP: No Piece at (" + position.getX() + ", " + position.getY() + ")");
		}
		else if(position.getPiece().getColor() != activePlayer.getColor()) {
			System.out.println("sAP: The Piece on the requested Position is of the wrong Color.");
		} else if (midJump && (position != activePosition)) {
			System.out.println("sAP: Cannot switch active Position in the middle of a jump.");
		}else if (!midJump) {
			System.out.println("sAP: Setting activePosition to (" + position.getX() + ", " + position.getY() + ").");
			this.activePosition = position;
		} 
	}

	public void crown(Position position) {
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

	public void updateAvailableMoves() throws InvalidPositionException {
		Position startingPosition;
		// This variable is used to signal the discovery of the first possible Jump. It tells the function to ignore all moves found before now.
		boolean ignorePreviousMoves = !jumpMandatory;
		ArrayList<Position> moves = new ArrayList<Position>();
		availableMoves.clear();
		
		if(!midJump) {
			// iterate over the black spaces on the board.
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j+=2) { 
					startingPosition = board.getPosition(i, j + (i % 2));
					moves = getAvailableMoves(startingPosition);
					
					if(moves.size() > 0) {
						if(jumpMandatory && ignorePreviousMoves) {
							availableMoves.clear();
							ignorePreviousMoves = false;
						}
						
						availableMoves.put(startingPosition, moves);		
						System.out.println("Positon (" + startingPosition.getX() + ", " + startingPosition.getY() + ") has " + moves.size() + " available moves.");
					}
				}
			}
		} else {
			jumpMandatory = true;
			moves = getAvailableMoves(activePosition);
			if(moves.size() > 0) availableMoves.put(activePosition, moves);
		}
		
		jumpMandatory = false;
	}

	private ArrayList<Position> getAvailableMoves(Position start) {
		ArrayList<Position> moves = new ArrayList<Position>();
		if(!start.hasPiece() || (start.getPiece().getColor() != activePlayer.getColor())) return moves;
		moves = getJumps(start);
		if(moves.size() > 0) jumpMandatory = true;
		if(!jumpMandatory) moves = getMoves(start);		
		return moves;
	}
	
	/**
	 * Helper function for getAvailableMoves.
	 * Decided to eliminate the parameter which flagged the piece as a king. this makes the function roughly twice the size.
	 * Will consider rewriting again.
	 * @param start The position from which to look for jumps. This Position is guaranteed to have a Piece on it.
	 * @return An ArrayList of valid jump Positions.
	 */
	private ArrayList<Position> getJumps(Position start) {
		ArrayList<Position> jumps = new ArrayList<Position>();
		Position destination, jumpedPosition;
		int startingX = start.getX();
		int startingY = start.getY();
		int direction = 1;
		
		Color activeColor = start.getPiece().getColor();
		if(activeColor == Color.RED) direction = -1;
		
		// The below section of code can be shortened greatly using tricky loops, but it makes the code far less readable.
		// Check forward jumps
		try {
			destination = board.getPosition(startingX - 2, startingY + (2 * direction));		
			jumpedPosition = board.getPosition(startingX - 1, startingY + direction);
			if(jumpedPosition.hasPiece() && (jumpedPosition.getPiece().getColor() != activeColor) && !destination.hasPiece()) jumps.add(destination);
		} catch (InvalidPositionException e) {}

		try { 
			destination = board.getPosition(startingX + 2, startingY + (2 * direction));
			jumpedPosition = board.getPosition(startingX + 1, startingY + direction);
			if(jumpedPosition.hasPiece() && (jumpedPosition.getPiece().getColor() != activeColor) && !destination.hasPiece()) jumps.add(destination);
		} catch (InvalidPositionException e) {}
		
		// If King, check backward jumps
		if(start.getPiece().isKing()) {
			direction *= -1;
			
			try {
				destination = board.getPosition(startingX - 2, startingY + (2 * direction));		
				jumpedPosition = board.getPosition(startingX - 1, startingY + direction);
				if(jumpedPosition.hasPiece() && (jumpedPosition.getPiece().getColor() != activeColor) && !destination.hasPiece()) jumps.add(destination);
			} catch (InvalidPositionException e) {}

			try { 
				destination = board.getPosition(startingX + 2, startingY + (2 * direction));
				jumpedPosition = board.getPosition(startingX + 1, startingY + direction);
				if(jumpedPosition.hasPiece() && (jumpedPosition.getPiece().getColor() != activeColor) && !destination.hasPiece()) jumps.add(destination);
			} catch (InvalidPositionException e) {}
		}
		
		return jumps;
	}
	
	/**
	 * Helper function of getAvailableMoves
	 * @param start The Position from which to look for moves.
	 * @return An ArrayList of Positions to move to.
	 */
	private ArrayList<Position> getMoves(Position start) {
		ArrayList<Position> moves = new ArrayList<Position>();
		
		Position destination;
		int startingX = start.getX();
		int startingY = start.getY();
		int direction = 1;
		
		Color activeColor = start.getPiece().getColor();
		if(activeColor == Color.RED) direction = -1;
		
		// Check forward moves
		try {
			destination = board.getPosition(startingX - 1, startingY + direction);
			if(!destination.hasPiece()) moves.add(destination);
		} catch (InvalidPositionException e) {}
		
		// Check the move forward and to the right.
		try {
			destination = board.getPosition(startingX + 1, startingY + direction);
			if(!destination.hasPiece()) moves.add(destination);
		} catch (InvalidPositionException e) {}
		
		// If King, check backwards jumps
		if(start.getPiece().isKing()) {
			direction *= -1;
		
			try {
				destination = board.getPosition(startingX - 1, startingY + direction);
				if(!destination.hasPiece()) moves.add(destination);
			} catch (InvalidPositionException e) {}
			
			// Check the move forward and to the right.
			try {
				destination = board.getPosition(startingX + 1, startingY + direction);
				if(!destination.hasPiece()) moves.add(destination);
			} catch (InvalidPositionException e) {}
		}
		
		return moves;
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