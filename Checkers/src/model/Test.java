package model;
import java.util.*;
import java.util.regex.*;
import java.awt.*;

//TODO Find out why I keep getting error when trying to run. Unsupported major.minor
/**
 * Running this program will result in an infinite loop terminated by typ "EXIT" into the console. 
 * To make a move, type into the console a list of coordinate pairs (e.g. (1, 3) (2, 4) (9, 3)). 
 * The move will only occur if it's the appropriate players turn and the move is legal.
 * @author Kevin
 *
 */
public class Test {

	public static void main(String[] args) {
		
		
		Game game = new Game();
		Piece piece = new Piece(Color.RED);
		Board gameBoard = game.getBoard(), board = new Board();

		try {
			board.getPosition(1, 1).addPiece(piece);
			board.getPosition(1, 1).removePiece();

			gameBoard.getPosition(2, 7).addPiece(new Piece(Color.RED));
			gameBoard.getPosition(2, 0).addPiece(new Piece(Color.BLACK));
			gameBoard.getPosition(3, 1).addPiece(new Piece(Color.RED));
			
			
			printBoard(gameBoard);
	
			if(board.getPosition(1, 1).hasPiece()) System.out.println("Color: " + board.getPosition(1,1).getPiece().getColor());
			
			/*
			 * Test moving various pieces around the board.
			 */
			
			// Move BLACK Pawn forwards.
			game.move(game.getPlayer1(), createRoute(gameBoard.getPosition(2, 0), gameBoard.getPosition(1, 1)));
			
			// Move RED Pawn forwards.
			game.move(game.getPlayer2(), createRoute(gameBoard.getPosition(2, 7), gameBoard.getPosition(3, 6)));
			
			printBoard(gameBoard);
			
			// Attempt to move BLACK Pawn backwards.
			game.move(game.getPlayer1(), createRoute(gameBoard.getPosition(1, 1), gameBoard.getPosition(2, 0)));
			printBoard(gameBoard);
			
			// Promote BLACK Pawn.
			gameBoard.getPosition(1, 1).getPiece().promote();
			
			//Move BLACK King backwards.
			game.move(game.getPlayer1(), createRoute(gameBoard.getPosition(1, 1), gameBoard.getPosition(2, 0)));
			
			//Move RED Pawn forward.
			game.move(game.getPlayer2(), createRoute(gameBoard.getPosition(3, 6), gameBoard.getPosition(4, 5)));
			
			printBoard(gameBoard);
			
			// Attempt to move a King to a position occupied by enemy Pawn. (BUGGED)
			game.move(game.getPlayer1(), createRoute(gameBoard.getPosition(2, 0), gameBoard.getPosition(3, 1)));
			
			printBoard(gameBoard);
			
			// Attempt to capture a RED Pawn with BLACK King.
			game.move(game.getPlayer1(), createRoute(gameBoard.getPosition(2, 0), gameBoard.getPosition(4, 2)));
			
			printBoard(gameBoard);
			
			gameBoard.clear();
			
			game.stageBoard();
			
			printBoard(gameBoard);
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		try {
			Game testGame = new Game();
			Board testBoard = testGame.getBoard();
			Position inputPosition;
			ArrayList<Position> route;
			
			String input = "test";
			Scanner scanner = new Scanner(System.in);
			Pattern movePattern = Pattern.compile("\\(([0-7]), ([0-7])\\)");
			Matcher moveMatcher;
			testGame.stageBoard();
			
			testGame.setActivePosition(testBoard.getPosition(2, 2));
			testGame.move(testBoard.getPosition(3, 3));
			while (!input.equals("EXIT")) {
				route = new ArrayList<Position>();
				printBoard(testBoard);
				
				System.out.println("\nEnter a Move.");
				
				input = scanner.nextLine();
				moveMatcher = movePattern.matcher(input);
				
				while(moveMatcher.find()) {
						inputPosition = testBoard.getPosition(Integer.parseInt(moveMatcher.group(1)), Integer.parseInt(moveMatcher.group(2)));
					
						route.add(inputPosition);
				}
				
				if(route.size() >= 2) {
					printAvailableMoves(testGame.getAvailableMoves(route.get(0)));
					testGame.move(testGame.getActivePlayer(), route);
				}
			}
			
			scanner.close();
			
		} catch (InvalidMoveException e) {
			System.out.println("Error.");
		} catch (InvalidPositionException e) {
			
		}
	}
	
	private static void printAvailableMoves(ArrayList<Move> availableMoves) {
		int numberOfMoves = availableMoves.size();
		Move move;
		Position endingPosition;
		
		if(availableMoves.size() > 0) {
			System.out.println("Valid Moves Starting at (" + availableMoves.get(0).getStartingPosition().getX() + ", " + availableMoves.get(0).getStartingPosition().getY() + "):" );
		} else {
			return;
		}
		
		for(int i = 0; i < numberOfMoves; i++) {
			move = availableMoves.get(i);
			endingPosition = move.getEndingPosition();
			System.out.print("(" + endingPosition.getX() + ", " + endingPosition.getY() + ") ");
		}
		
		System.out.println();
	}
	
	/**
	 * Prints a text based representation of the given board.
	 * @param board
	 * @throws InvalidPositionException
	 */
	private static void printBoard(Board board) throws InvalidPositionException{
		Position tempPosition;
		
		try {
			String os = System.getProperty("os.name");
			
			if(os.contains("Windows")) {
				Runtime.getRuntime().exec("cls");
			} else {
				Runtime.getRuntime().exec("clear");
			}
		} catch (Exception e) {
			
		}
		
		System.out.println("  --------------------------------- ");
		
		for(int i = 7; i >= 0; i--) {
			System.out.print(i + " ");
			for(int j = 0; j <= 7; j++) { 
				tempPosition = board.getPosition(j, i);
				if(tempPosition.hasPiece()) {
					Piece pieceToMove = tempPosition.getPiece();
					Color c = pieceToMove.getColor();
					if (c == Color.BLACK) {
							if(pieceToMove.isKing()) {
								System.out.print("| B ");
							} else {
								System.out.print("| b ");
							}
					} else if (c == Color.RED) {
							if(pieceToMove.isKing()) {
								System.out.print("| R ");
							} else {
								System.out.print("| r ");
							}
					} else {
							System.out.print("| E ");
							break;
					}
				} else {
					System.out.print("|   ");
				}
						
				// System.out.println("(" + tempPosition.getX() + ", " + tempPosition.getY() + ") COLOR: " + board.getPosition(i, j).getPiece().getColor());

			}
			System.out.println("|\n  --------------------------------- ");
		}		
		
		System.out.println("    0   1   2   3   4   5   6   7");
	}

	private static ArrayList<Position> createRoute(Position...positions) {
		ArrayList<Position> route = new ArrayList<Position>();
		
		for(Position position : positions) {
			route.add(position);
		}
		
		System.out.println("Route length: " + route.size());
		return route;
	}
}
