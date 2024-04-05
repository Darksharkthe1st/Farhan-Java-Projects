import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class DrawingCanvas extends JComponent {
	
	//The width and height of the graphics window
	private int width;
	private int height;
	
	//We initialize the drawing canvas
	public DrawingCanvas(int w, int h) {
		width = w; height = h;
	}
	
	//Runs the Graphics code - called by Java behind the scenes business
	protected void paintComponent(Graphics g) {
		//We make a Graphics2D version of our graphic window because Graphics2D has more methods for Graphic stuff
		Graphics2D g2d = (Graphics2D)g;
		
		//The rectangle exists, but it was never drawn onto the graphics window
		Rectangle2D r = new Rectangle2D.Double(0,0,width,height);
		
		g2d.setColor(Color.blue);
		g2d.fill(r);
		
	}
	
}
