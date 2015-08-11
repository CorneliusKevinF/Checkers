package temp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.swing.JPanel;


@SuppressWarnings("serial")
class Surface extends JPanel {

    private void doDrawing(Graphics g) {    
    	
        Graphics2D g2d = (Graphics2D) g;
        
        for(int i = 0; i < 8; i++) {
        	
        	for(int j = 0; j < 8; j++) {
        		
        		if(((i + j) % 2) == 0) {
        			g2d.setColor(Color.RED);
        		} else {
        			g2d.setColor(Color.BLACK);
        		}
        	
        		g2d.fillRect(i * 50, j * 50, 50, 50);
        	}
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
}