package view;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameView extends JFrame {
	BoardView boardView;
	
	public GameView() {
		super("CD Checkers");
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		boardView = new BoardView();
		add(boardView);
		pack();
		setVisible(true);
	}
}
