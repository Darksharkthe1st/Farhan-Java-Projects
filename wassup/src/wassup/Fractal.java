package wassup;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Arrays;

import javax.swing.JPanel;

public class Fractal extends JPanel {
	int level;
	int sides;
	int x, y, radius;
	private double heightRatio;
	private double widthRatio;
	double angleOffset = 0;

	public Fractal(int level, int sides, int x, int y, int radius, double heightRatio, double widthRatio) {
		super();
		this.level = level;
		this.sides = sides;
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.heightRatio = heightRatio;
		this.widthRatio = widthRatio;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
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

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public int getSides() {
		return sides;
	}

	public void setSides(int sides) {
		this.sides = sides;
	}

	public double getHeightRatio() {
		return heightRatio;
	}

	public void setHeightRatio(double heightRatio) {
		this.heightRatio = heightRatio;
	}

	public double getWidthRatio() {
		return widthRatio;
	}

	public void setWidthRatio(double widthRatio) {
		this.widthRatio = widthRatio;
	}
	
	public double getAngleOffset() {
		return angleOffset;
	}

	public void setAngleOffset(double angleOffset) {
		this.angleOffset = angleOffset;
	}

	public void paintComponent(Graphics g) {
		Hexagon h = new Hexagon(x,y,radius,sides);
		innerDraw(g,h,1);
	}
	
	public void innerDraw(Graphics g, Hexagon h, int lev) {
		if (lev > level)
			return;
		g.setColor(Color.red);
		h.drawTo(g);
		g.setColor(Color.blue);
		int[] newXPoints1 = new int[h.xpoints.length];
		int[] newYPoints1 = new int[h.xpoints.length];
		
		Polygon p;
		for (int i = 0; i < h.xpoints.length; i++) {
			p = h.makeSideTriangle(i);
			g.fillPolygon(p);
			newXPoints1[i] = p.xpoints[p.xpoints.length - 1];
			newYPoints1[i] = p.ypoints[p.xpoints.length - 1];
		}
		
		g.setColor(Color.black);
		innerDraw(g, new Hexagon(newXPoints1, newYPoints1), lev + 1);
	}
	
	public class Hexagon {
		int[] xpoints, ypoints;
		double sideLength;
		Point center;
		
		public Hexagon(int x, int y, int radius, int n) {
			xpoints = new int[n];
			ypoints = new int[n];
			for (int i = 0; i < n; i++) {
				xpoints[i] = (int)(x + radius * Math.cos(2*Math.PI*i/n + angleOffset));
				ypoints[i] = (int)(y + radius * Math.sin(2*Math.PI*i/n + angleOffset));
			}
			sideLength = Math.sqrt(Math.pow(xpoints[1]-xpoints[0],2) + Math.pow(ypoints[1]-ypoints[0], 2));
		}
		
		public Hexagon(int[] xpoints, int[] ypoints) {
			super();
			this.xpoints = xpoints;
			this.ypoints = ypoints;
			sideLength = Math.sqrt(Math.pow(xpoints[1]-xpoints[0],2) + Math.pow(ypoints[1]-ypoints[0], 2));
		}

		public void drawTo(Graphics g) {
			g.setColor(Color.black);
			g.drawPolygon(xpoints, ypoints, xpoints.length);
//			g.setColor(Color.red);
//			g.fillPolygon(xpoints, ypoints, xpoints.length);
			}

		public int[] getXpoints() {
			return xpoints;
		}

		public void setXpoints(int[] xpoints) {
			this.xpoints = xpoints;
		}

		public int[] getYpoints() {
			return ypoints;
		}

		public void setYpoints(int[] ypoints) {
			this.ypoints = ypoints;
		}
		
		//Side 0 is pts 0-1, 1 is 1-2, etc. etc
		public Polygon makeSideTriangle(int side) {
			Point start, end;
			if (side == xpoints.length - 1) {
				start = new Point(xpoints[xpoints.length - 1],ypoints[xpoints.length - 1]);
				end = new Point(xpoints[0], ypoints[0]);
			}
			else {
				start = new Point(xpoints[side],ypoints[side]);
				end = new Point(xpoints[side+1], ypoints[side+1]);
			}
			
			int[] triXPoints = new int[3];
			int[] triYPoints = new int[3];
			
			//First point is 1/3rd from the first vertice of the side
			triXPoints[0] = (int)(start.x * widthRatio + end.x * (1 - widthRatio));
			triYPoints[0] = (int)(start.y * widthRatio + end.y * (1 - widthRatio));
			
			//Second point is 1/3rd from the second vertice of the side
			triXPoints[1] = (int)(start.x  * (1 - widthRatio) + end.x * widthRatio);
			triYPoints[1] = (int)(start.y  * (1 - widthRatio) + end.y * widthRatio);
			
			//Think of the above two as a weighted average. 2/3rds the way to one, 1/3rds the way to the other.
			
			Point midpoint = new Point((start.x + end.x) / 2, (start.y + end.y) / 2);
			
			//Another weighted average using the variable ratio.
			getCenter();
			triXPoints[2] = (int)(midpoint.x * heightRatio + center.x * (1 - heightRatio));
			triYPoints[2] = (int)(midpoint.y * heightRatio + center.y * (1 - heightRatio));
			return new Polygon(triXPoints, triYPoints, 3);
		}
		
		public Point getCenter() {
			if (center != null)
				return center;
			center = new Point(0,0);
			for (int i = 0; i < xpoints.length; i++) {
				center.x += xpoints[i];
				center.y += ypoints[i];
			}
			center.x /= xpoints.length;
			center.y /= xpoints.length;
			return center;
		}
	}
}