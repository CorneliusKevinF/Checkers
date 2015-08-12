package model;

import java.awt.Color;

/**
 * Piece Class
 * @author Robert Dvorscak
 * @author Kevin Cornelius
 */
public class Piece {
	private Color color;
	private boolean isKing;
	/**
	 * Makes a new 'pawn' with the given color
	 * @param color The color describing which team the Piece will be on.
	 */
	public Piece(Color color) {
		this.color = color;
		this.isKing = false;
	}

//Getters and Setters Below ***********
	/**
	 * Promotes the piece from a 'pawn' to a 'king.'
	 */
	public void promote() {
		this.isKing = true;
	}
	
	/**
	 * Returns whether or not the Piece is a 'king' in the form of <code>true</code> or <code>false</code>.
	 * @return Whether or not the Piece is a 'king.'
	 */
	public boolean isKing() {
		return this.isKing;
	}
	
	/**
	 * 
	 * @return The Piece's color.
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * Set's the Piece's color.
	 * @param color The new color for the Piece.
	 */
	public void setColor(Color color) {
		this.color = color;
	}
}
