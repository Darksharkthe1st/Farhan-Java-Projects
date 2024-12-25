import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Renderer extends JPanel {
    int x = 0;
    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRect((int)(Math.sin(x * 0.01) * Graphy3Driver.getWidth() / 2) + Graphy3Driver.getWidth() / 2 - 50, 0, 100, 100);
    }

    public void drawPolygons() {
        x++;
    }
}
