import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JPanel;

public class rectangularPrism extends JPanel {
	int x, y, z, length, height, ax, ay;
	Color[] colors = new Color[6];

	public rectangularPrism(int x, int y, int z, int ax, int ay, int length, int height) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.ax = ax;
		this.ay = ay;
		this.length = length;
		this.height = height;
		for (int i = 0; i < colors.length; i++) {
			colors[i] = randColor();
		}

	}

	public int getXPos() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getYPos() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getHeighty() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getAx() {
		return ax;
	}

	public void setAx(int ax) {
		this.ax = ax;
	}

	public int getAy() {
		return ay;
	}

	public void setAy(int ay) {
		this.ay = ay;
	}

	public Color[] getColors() {
		return colors;
	}

	public void setColors(Color[] colors) {
		this.colors = colors;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int counter = 0;
//		int ax = 200;
//		int ay = 50;
//		int length = size / 2;
//		int height = size;
		{
			g.setColor(colors[counter++]);
			int[] xpoints = { x, x + length, x + length, x, x };
			int[] ypoints = { y, y, y + height, y + height, y };
			int npoints = xpoints.length;
			Polygon p = new Polygon(xpoints, ypoints, npoints);
			g.fillPolygon(p);
		}
		
		{

			g.setColor(colors[counter++]);
			int[] xpoints = { x + ax, x + length + ax, x + length + ax, x + ax, x + ax };
			int[] ypoints = { y + ay, y + ay, y + height + ay, y + height + ay, y + ay };
			int npoints = xpoints.length;
			Polygon p = new Polygon(xpoints, ypoints, npoints);
			g.fillPolygon(p);
		}
		
		{

			g.setColor(colors[counter++]);
			int[] xpoints = { x + length, x + length, x + length + ax, x + length + ax, x + length };
			int[] ypoints = { y, y + height, y + height + ay, y + ay, y };
			int npoints = xpoints.length;
			Polygon p = new Polygon(xpoints, ypoints, npoints);
			g.fillPolygon(p);
		}
		{

			g.setColor(colors[counter++]);
			int[] xpoints = { x, x + ax, x + length + ax, x + length, x + ax };
			int[] ypoints = { y + height, y + height + ay, y + height + ay, y + height, y + height };
			int npoints = xpoints.length;
			Polygon p = new Polygon(xpoints, ypoints, npoints);
			g.fillPolygon(p);
		}
		{

			g.setColor(colors[counter++]);
			int[] xpoints = {x, x + ax, x + ax, x, x};
			int[] ypoints = {y, y + ay, y + height + ay, y + height, y};
			int npoints = xpoints.length;
			Polygon p = new Polygon(xpoints, ypoints, npoints);
			g.fillPolygon(p);
		}
		{

			g.setColor(colors[counter++]);
			int[] xpoints = {x, x + ax, x + length + ax, x + length, x};
			int[] ypoints = {y, y + ay, y + ay, y, y};
			int npoints = xpoints.length;
			Polygon p = new Polygon(xpoints, ypoints, npoints);
			g.fillPolygon(p);
		}
	}
	
	public Color randColor() {
		return (new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255), 100));
	}
}
