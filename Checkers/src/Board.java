/**
 * The Board class is an 8x8 array of Positions. It does nothing more than hold pieces.
 * @author Kevin Cornelius
 */
public class Board {
	Position[][] squares;

	public Board() {
		squares = new Position[8][8];
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				squares[i][j] = new Position(i,j);
			}
		}
	}

	/**
	 * Moves a Piece on the Board from one Position to another.
	 * @param startingPosition	The starting Position of the Piece to be moved.
	 * @param endingPosition The Position for the Piece to be moved to.
	 */
	public void movePiece(Position startingPosition, Position endingPosition) throws InvalidMoveException {
			endingPosition.addPiece(startingPosition.getPiece());
			startingPosition.removePiece();
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
			return squares[x][y];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InvalidPositionException("(" + x + ", " + y + ") is not a valid Position.", e);
		}
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
