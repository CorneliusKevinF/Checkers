
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
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
}
