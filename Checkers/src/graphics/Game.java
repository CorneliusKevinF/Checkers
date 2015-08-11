package graphics;

import java.awt.*;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 
 * @author Kevin
 *
 */
// @SuppressWarnings("serial")
public class Game {
	Board board;
	
	public Game() {

		JFrame frame = new JFrame("Checkers");
		frame.setLayout(null);
		
		board = new Board();
		
		frame.getContentPane().setSize(board.getSize());
		frame.add(board);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//TODO Remove this test code.
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                board.getTimer().stop();
            }
            
        });

        Dimension contentSize = board.getSize();
        Insets insets = frame.getInsets();
        
        frame.setVisible(true);
        
        frame.setSize(new Dimension((int) contentSize.getWidth() + insets.left + insets.right, (int) contentSize.getHeight() + insets.top + insets.bottom));
        frame.setLocation((int) (frame.getLocation().getX() - (frame.getWidth() / 2)), (int) (frame.getLocation().getY() - (frame.getHeight() / 2)));
	}


	public Board getBoard() {
		System.out.println("Getting and casting the Board.");
		
		if(board == null){
			System.out.println("!!!BOARD IS NULL!!!");//TODO real error catch here.
		}
		return board;
	}
	
	public void updateBoard(logic.Board board) {
		logic.Position logicPosition;
		Position position;
		
		try {
			for (int i = 0; i < 8; i++) {
				for(int j = 0; i < 8; i++) {
					logicPosition = board.getPosition(i, j);
					position = this.board.getPosition(i, j);
					if (logicPosition.hasPiece()) {
						if(!(position.hasPiece() && (position.getPiece().getColor() == logicPosition.getPiece().getColor()))) {
							this.board.addPiece(i, j, logicPosition.getPiece().getColor());
						}
					} else {
						position.removePiece();
					}
				}
			}
		} catch (logic.InvalidPositionException e) {
			
		}
	}
	
	public void addPiece(int x, int y, Color color){
		board.addPiece(x, y, color);
	}
	
	public void newGame(){
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				if(i<2 && i+j%2 ==0){
					board.addPiece(i, j, Color.BLACK);
				}
				else if(i>5 && i+j%2 ==0){
					board.addPiece(i, j, Color.RED);
				}
			}
			
		}
		
	}
	

}
