package graphics;

import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * 
 * @author Kevin
 *
 */
// @SuppressWarnings("serial")
public class Game {
	JFrame frame;
	Board board;
	
	public Game() throws HeadlessException {
	        EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {

		frame = new JFrame();
		frame.setTitle("CD Checkers");
		frame.setLayout(null);
		
		board = new Board();
		
		frame.add(board);
		
		// TODO Fix: This is meant to resize the window to fit it's contents exactly
		frame.pack();
		frame.getContentPane().setSize(frame.getComponent(0).getSize());
		System.out.println("JFrame Dimensions\nHeight: " + frame.getHeight() + "\nWidth: " + frame.getWidth());

		//setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//TODO Remove this test code.
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                board.getTimer().stop();
            }
        });
        
	            }
	        });
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public Board getBoard() {
		System.out.println("Getting and casting the Board.");
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
	

}
