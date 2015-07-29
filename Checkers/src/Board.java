/**
 * The Board class is an 8x8 array of Positions. It does nothing more than hold pieces.
 * @author Kevin Cornelius
 */
public class Board {
	Position[][] squares;

	public Board() {
		squares = new Position[8][8];
		
		for(int i = 0; i < squares.length; i++) {
			for(int j = 0; j < squares[0].length; i++) {
				squares[i][j] = new Position(i,j);
			}
		}
	}

	/**
	 * @param piece	The Piece to be moved.
	 * @param positions	An array of positions for the given Piece to traverse.
	 */
	public void movePiece(Position startingPosition, Position endingPosition) throws InvalidMoveException {
		if(startingPosition.hasPiece() && !endingPosition.hasPiece()) {
			endingPosition.addPiece(startingPosition.getPiece());
			startingPosition.removePiece();
		} else {
			throw new InvalidMoveException("Invalid move.");
		}
	}
	
	public Position getPosition(int x, int y) {
		return squares[x][y];
	}
	
	/*
	// Setting up the board will be handled by the Game class, so that we can reuse the Board for other games if we want.
	public void Start(){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(i==0 && j%2==0){
					squares[i][j]=0;
				}
				else if(i==1 && j%2==1){
					squares[i][j]=0;
				}
				else if(i==6 && j%2==0){
					squares[i][j]=1;
				}
				else if(i==7 && j%2==1){
					squares[i][j]=1;
				}
				else{
					squares[i][j]=-1;
				}
				System.out.print("[" + squares[i][j] + "]"); //Don't worry, this is for testing only.
			}
			System.out.println(""); //I will remove as soon as I'm sure the board has correctly created.
		}
	}
	*/

}
