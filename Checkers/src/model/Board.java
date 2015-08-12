package model;

import java.awt.Color;

/**
 * The Board class is an 8x8 array of Positions. It does nothing more than hold pieces.
 * @author Kevin Cornelius
 */
public class Board {
	Position[][] positions;

	public Board() {
		positions = new Position[8][8];
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				positions[i][j] = new Position(i,j);
			}
		}
	}

	/**
	 * Moves a Piece on the Board from one Position to another.
	 * @param startingPosition The starting Position of the Piece to be moved.
	 * @param endingPosition The Position for the Piece to be moved to.
	 */
	public void movePiece(Position startingPosition, Position endingPosition) {
			endingPosition.addPiece(startingPosition.getPiece());
			startingPosition.removePiece();
	}

	public void addPiece(int x, int y, Piece piece) throws InvalidPositionException {
		try {
			positions[x][y].addPiece(piece);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InvalidPositionException("(" + x + ", " + y + ") is not a valid Position.", e);
		}
	}
	
	public void addPiece(int x, int y, Color color) throws InvalidPositionException {
		addPiece(x, y, new Piece(color));
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @throws InvalidPositionException
	 */
	public void removePiece(int x, int y) throws InvalidPositionException {
		try {
			positions[x][y].removePiece();
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InvalidPositionException("(" + x + ", " + y + ") is not a valid Position.", e);
		}
	}
	public void removePiece(Position position) {
		position.removePiece();
	}
	/**
	 * Retrieves a position from the Board.
	 * @param x The x-line on which the desired Position rests.
	 * @param y The y-line on which the desired Position rests.
	 * @return The specified Position on the Board.
	 * @throws InvalidPositionException
	 */
	public Position getPosition(int x, int y) throws InvalidPositionException {
		try {
			return positions[x][y];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InvalidPositionException("(" + x + ", " + y + ") is not a valid Position.", e);
		}
	}
	
	/**
	 * Removes all Pieces from the board.
	 */
	public void clear() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				positions[i][j].removePiece();
			}
		}
	}
}
