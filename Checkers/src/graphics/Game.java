package graphics;

import java.awt.HeadlessException;
import javax.swing.JFrame;

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
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public Board getBoard() {
		System.out.println("Getting and casting the Board.");
		return board;
	}
	
	public void updateBoard(logic.Board board) {
		
	}
}
