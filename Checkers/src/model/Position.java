package model;
/**
 * 
 * @author Kevin
 * @author Javadocs Bobby
 * 
 * {@literal meaning of x is horizontal value}
 * {@literal meaning of y is vertical value}
 *
 */
public class Position {
	private int x, y;
	private Piece piece;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
		piece = null;
	}
	
	public Piece getPiece() {
		return this.piece;
	}
	
	public void addPiece(Piece piece) {
		this.piece = piece;
	}
	
	public void removePiece() { 
		this.piece = null;
	}
	
	public boolean hasPiece() {
		return !(piece == null);
	}
	
	/**
	 * 
	 * @return horizontal value
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * 
	 * @return vertical value
	 */
	public int getY() {
		return this.y;
	}
}
