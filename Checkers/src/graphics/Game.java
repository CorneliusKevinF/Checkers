package graphics;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Game extends JFrame {
	Board board;
	
	public Game() {
		super("CD Checkers");
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		board = new Board();
		add(board);
		pack();
		setVisible(true);
	}
}
