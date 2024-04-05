import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JPanel;

public class rectangularPrism extends JPanel {
	int size, x, y, z, yaw, pitch, roll;
	Color[] colors = new Color[6];

	public rectangularPrism(int size, int x, int y, int z) {
		super();
		this.size = size;
		this.x = x;
		this.y = y;
		this.z = z;
		for (int i = 0; i < colors.length; i++) {
			colors[i] = randColor();
		}

	}

	public int getsize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
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

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int counter = 0;
		g.setColor(colors[counter++]);
		g.fillRect(x, y, size, size);

		g.setColor(colors[counter++]);
		g.fillRect(x + 30, y + 30, size, size);
		{

			g.setColor(colors[counter++]);
			int[] xpoints = { x + size, x + size, x + size + 30, x + size + 30, x + size };
			int[] ypoints = { y, y + size, y + size + 30, y + 30, y };
			int npoints = xpoints.length;
			Polygon p = new Polygon(xpoints, ypoints, npoints);
			g.fillPolygon(p);
		}
		{

			g.setColor(colors[counter++]);
			int[] xpoints = { x, x + 30, x + size + 30, x + size, x + 30 };
			int[] ypoints = { y + size, y + size + 30, y + size + 30, y + size, y + size };
			int npoints = xpoints.length;
			Polygon p = new Polygon(xpoints, ypoints, npoints);
			g.fillPolygon(p);
		}
		{

			g.setColor(colors[counter++]);
			int[] xpoints = {x, x + 30, x + 30, x, x};
			int[] ypoints = {y, y + 30, y + size + 30, y + size, y};
			int npoints = xpoints.length;
			Polygon p = new Polygon(xpoints, ypoints, npoints);
			g.fillPolygon(p);
		}
		{

			g.setColor(colors[counter++]);
			int[] xpoints = {x, x + 30, x + size + 30, x + size, x};
			int[] ypoints = {y, y + 30, y + 30, y, y};
			int npoints = xpoints.length;
			Polygon p = new Polygon(xpoints, ypoints, npoints);
			g.fillPolygon(p);
		}
	}
	
	public Color randColor() {
		return (new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255), 100));
	}
}
