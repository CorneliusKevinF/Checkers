
public class Position {
	private int x, y;
	private Piece piece;
	
	Position(int x, int y) {
		this.x = x;
		this.y = y;
		piece = null;
	}
	
	void addPiece(Piece piece) {
		this.piece = piece;
	}
	
	void removePiece() { 
		this.piece = null;
	}
	
	boolean hasPiece() {
		return !(piece == null);
	}
	
	int getX() {
		return x;
	}
	
	int getY() {
		return y;
	}
}
