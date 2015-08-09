package graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.swing.JPanel;


@SuppressWarnings("serial")
class Surface extends JPanel {

    private void doDrawing(Graphics g) {    
    	
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(127, 0, 127));
        g2d.fillRect(5, 5, 290, 190);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
}