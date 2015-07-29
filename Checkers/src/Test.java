
public class Test {

	public static void main(String[] args) {
		Game game = new Game(2);
		Piece piece = new Piece(Color.RED);
		Board gameBoard = game.getBoard(), board = new Board();
		Player player1 = new Player("Kevin", PlayerType.HUMAN, Color.RED);
		Player player2 = new Player("Bobby", PlayerType.HUMAN, Color.BLACK);
		
		System.out.println(player1.getName() + "'s ID: " + player1.getID());
		System.out.println(player2.getName() + "'s ID: " + player2.getID());
		System.out.println("Game Player1's ID: " + game.getPlayer1().getID());
		System.out.println("Game Player2's ID: " + game.getPlayer2().getID());
		
		board.getPosition(1, 1).addPiece(piece);
		board.getPosition(1,1).removePiece();
		
		for(int i = 0; i < 8; i+=2) { 
			gameBoard.getPosition(i, 0).addPiece(new Piece(Color.RED));
		}
		
		Position tempPosition;
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) { 
				tempPosition = gameBoard.getPosition(i, j);
				if(tempPosition.hasPiece()) System.out.println("(" + tempPosition.getX() + ", " + tempPosition.getY() + ") COLOR: " + gameBoard.getPosition(i, j).getPiece().getColor());
			}
		}
		
		if(board.getPosition(1, 1).hasPiece()) System.out.println("Color: " + board.getPosition(1,1).getPiece().getColor());
		
		try {
			game.move(game.getPlayer1(), gameBoard.getPosition(2, 0), gameBoard.getPosition(3, 1));
		} catch (InvalidMoveException e) {
			System.out.println(e.getMessage());
		} finally {
			
		}
		
		}

}
