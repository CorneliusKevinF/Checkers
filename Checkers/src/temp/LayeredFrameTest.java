package temp;


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class LayeredFrameTest {
	private JFrame frame;
	
	public LayeredFrameTest() {
		initGUI();
	}

	private void initGUI() {
		frame = new JFrame("LayeredPane Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setContentPane(new JLayeredPane());
		
		JPanel background = new JPanel();
		background.setSize(new Dimension(400, 350));
		background.setBackground(Color.BLUE);
		
		frame.getContentPane().add(background, new Integer(0));
		
		frame.pack();
	}
	
	public static void main(String[] args) {
		LayeredFrameTest lft = new LayeredFrameTest();
	}
}
