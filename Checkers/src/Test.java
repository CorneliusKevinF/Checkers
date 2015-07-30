/**
 * 
 * @author Kevin
 *
 */
public class Test {

	public static void main(String[] args) {
		Game game = new Game();
		Piece piece = new Piece(Color.RED);
		Board gameBoard = game.getBoard(), board = new Board();
		Player kevin = new Player("Kevin", PlayerType.HUMAN, Color.RED);
		Player bobby = new Player("Bobby", PlayerType.HUMAN, Color.BLACK);
		
		/*
		System.out.println(kevin.getName() + "'s ID: " + kevin.getID());
		System.out.println(bobby.getName() + "'s ID: " + bobby.getID());
		System.out.println("Game Player1's ID: " + game.getPlayer1().getID());
		System.out.println("Game Player2's ID: " + game.getPlayer2().getID());
		*/
		
		try {
			board.getPosition(1, 1).addPiece(piece);
			board.getPosition(1, 1).removePiece();
			
			/*
			for(int i = 0; i < 8; i+=2) { 
				gameBoard.getPosition(i, 0).addPiece(new Piece(Color.RED));
			}
			
			for(int i = 0; i < 8; i+=2) { 
				gameBoard.getPosition(i, 7).addPiece(new Piece(Color.RED));
			}
			*/
			
			gameBoard.getPosition(2, 7).addPiece(new Piece(Color.RED));
			gameBoard.getPosition(2, 0).addPiece(new Piece(Color.BLACK));
			gameBoard.getPosition(3, 1).addPiece(new Piece(Color.RED));
			
			
			printBoard(gameBoard);
	
			if(board.getPosition(1, 1).hasPiece()) System.out.println("Color: " + board.getPosition(1,1).getPiece().getColor());
			
			/*
			 * Test moving various pieces around the board.
			 */
			game.move(game.getPlayer1(), gameBoard.getPosition(2, 0), gameBoard.getPosition(1, 1));
			game.move(game.getPlayer2(), gameBoard.getPosition(2, 7), gameBoard.getPosition(3, 6));
			printBoard(gameBoard);
			game.move(game.getPlayer1(), gameBoard.getPosition(1, 1), gameBoard.getPosition(2, 0));
			gameBoard.getPosition(1, 1).getPiece().promote();
			game.move(game.getPlayer1(), gameBoard.getPosition(1, 1), gameBoard.getPosition(2, 0));
			game.move(game.getPlayer2(), gameBoard.getPosition(3, 6), gameBoard.getPosition(4, 5));
			printBoard(gameBoard);
			game.move(game.getPlayer1(), gameBoard.getPosition(2, 0), gameBoard.getPosition(3, 1));
			printBoard(gameBoard);
			game.move(game.getPlayer1(), gameBoard.getPosition(2, 0), gameBoard.getPosition(4, 2));
			printBoard(gameBoard);

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Prints a text based representation of the given board.
	 * @param board
	 * @throws InvalidPositionException
	 */
	private static void printBoard(Board board) throws InvalidPositionException{
		Position tempPosition;
		
		System.out.println("Printing Boad:\n -------------------------------- ");
		
		for(int i = 7; i >= 0; i--) {
			for(int j = 0; j <= 7; j++) { 
				tempPosition = board.getPosition(j, i);
				if(tempPosition.hasPiece()) {
					Piece pieceToMove = tempPosition.getPiece();
					switch(pieceToMove.getColor()) {
						case BLACK:
							if(pieceToMove.isKing()) {
								System.out.print("| B ");
							} else {
								System.out.print("| b ");
							}
							break;
						case RED:
							if(pieceToMove.isKing()) {
								System.out.print("| R ");
							} else {
								System.out.print("| r ");
							}
							break;
						default:
							break;
					}
				} else {
					System.out.print("|   ");
				}
						
				// System.out.println("(" + tempPosition.getX() + ", " + tempPosition.getY() + ") COLOR: " + board.getPosition(i, j).getPiece().getColor());

			}
			System.out.println("|\n -------------------------------- ");
		}		
	}
}
