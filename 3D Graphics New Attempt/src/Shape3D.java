import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JPanel;

//This is the inherited class for all shapes used by ShapeList.
//Provides as a template to do the heavy lifting for the 3D Graphics business
public abstract class Shape3D {
	protected double x,  y,  z;
	protected double cx, cy, cz;
	protected int faces;
	protected Point3D[][] points;
	protected Point3D[] allPoints;
	protected Color[] colors;
	public Color outlineColor;
	protected Polygon3D[] polygons;
	protected String name; //Name variable just to keep track of which is which when debugging
	public int index;
	
	protected Shape3D(double x, double y, double z, int faces) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.faces = faces;
		this.name = "Shape";
		colors = new Color[faces];
		points = new Point3D[faces][];
		polygons = new Polygon3D[faces];
		setColors();
		setAllPoints();
		setPoints();
		makePolygons();
		setCenter();
	}
	
	//This is only for objShape, all of the shape stuff is handled in there
	//The separate constructor for objShape is so it can be made without knowing faces in advance
	protected Shape3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.name = "Shape";
	}
	
	public double getXPos() {
		return x;
	}

	public final void setX(double x) {
		moveBy(x - this.x, 0, 0);
	}

	public double getYPos() {
		return y;
	}

	public void setY(double y) {
		moveBy(0, y - this.y,0);
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		moveBy(0, 0, z-this.z);
	}
	
	public void setPosition(Point3D p) {
		moveBy(p.x - x, p.y - y, p.z - z);
	}
	
	public void setPosition(double x, double y, double z) {
		moveBy(x - this.x, y - this.y, z - this.z);
	}
	
	//Set values of cx, cy, and cz (center of shape) for use in rotation 
	protected abstract void setCenter();
	
	public Color[] getColors() {
		return colors;
	}

	public void setColors(Color[] colors) {
		this.colors = colors;
	}
	
	public Color getOutlineColor() {
		return outlineColor;
	}

	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
		for (Polygon3D p : polygons) {
			p.setOutlineColor(outlineColor);
		}
	}

	public int getFaces() {
		return faces;
	}
	
	//Used for keeping track of where it is in the polygons array in ShapeList3D
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String toString() {
		String output = name + ": ";
		for (int i = 0; i < points.length; i++) {
			output += "Face " + (i + 1) + ": {";
			for (Point3D point : points[i]) {
				output += point + ", ";
			}
			output += "}\n";
		}
		return output;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		setPolygonNames();
	}

	//Leave it up to the following class to set its colors cmd
	protected abstract void setColors();
	
	//Leave it up to the inheriting class to set its points.
	//Points must be made in counterclockwise order for vector normalization to work right
	protected abstract void setPoints();
	
	protected abstract void setAllPoints();
	
	//Makes the polygon from the points. Final so it cannot be changed.
	protected void makePolygons() {
		setCenter();
		polygons = new Polygon3D[faces];
		for (int i = 0; i < faces; i++) {
			polygons[i] = new Polygon3D(colors[i], points[i]);
			polygons[i].setOutlineColor(outlineColor);
		}
		setPolygonNames();
	}
	
	//Sets the names of the polygons for testing
	protected void setPolygonNames() {
		for (int i = 0; i < faces; i++) {
			polygons[i].setName(name + (i + 1));
		}
		
	}
	
	//Paints directly on the graphics window for quick testing
	public void paintMe(Graphics g) {
		for (int face = 0; face < points.length; face++) {
			if (polygons[face] != null && polygons[face].getAvgZ() > 0) {
				g.setColor(colors[face]);
				g.fillPolygon(polygons[face].getPolygon());
			}
		}
	}
	
	//Rotates the shape about the specified axis by theta degrees, where axis is x, y, or z.
	public void rotateAbout(char axis, double theta) {
		Matrix rotation = Point3D.rotationMatrix(theta, axis);
		for (Point3D point : allPoints) {
			point.setX(point.getX() - cx);
			point.setY(point.getY() - cy);
			point.setZ(point.getZ() - cz);
			
			double dist1;
			
			if (axis == 'z') dist1 = Math.sqrt(Math.pow(point.getX(), 2) + Math.pow(point.getY(), 2));
			else if (axis == 'x') dist1 = Math.sqrt(Math.pow(point.getZ(), 2) + Math.pow(point.getY(), 2));
			else dist1 = Math.sqrt(Math.pow(point.getX(), 2) + Math.pow(point.getZ(), 2));
			point.matrixTransform(rotation);
			double dist2;
			
			if (axis == 'z') dist2 = Math.sqrt(Math.pow(point.getX(), 2) + Math.pow(point.getY(), 2));
			else if (axis == 'x') dist2 = Math.sqrt(Math.pow(point.getZ(), 2) + Math.pow(point.getY(), 2));
			else dist2 = Math.sqrt(Math.pow(point.getX(), 2) + Math.pow(point.getZ(), 2));
			if (axis != 'x')
				point.setX(point.getX() * dist1 / dist2);
			if (axis != 'y')
				point.setY(point.getY() * dist1 / dist2);
			if (axis != 'z')
				point.setZ(point.getZ() * dist1 / dist2);
			point.setX(point.getX() + cx);
			point.setY(point.getY() + cy);
			point.setZ(point.getZ() + cz);
		}
		makePolygons();
	}
	
	public void moveBy(double dx, double dy, double dz) {
		x+= dx;
		y+= dy;
		z+= dz;
		Matrix transMatrix = Point3D.translationMatrix(dx, dy, dz);
		for (Point3D[] facePoints : points) {
			for (Point3D p : facePoints) {
				p.matrixTransform(transMatrix);
			}
		}
		makePolygons();
		setCenter();
	}
	
	public int countVisible() {
		int count = 0;
		for (Polygon3D p : polygons) {
			if (p.checkNormal() && Math.abs(p.getAvgX()) < GraphicDriver.screenWidth/2 && Math.abs(p.getAvgY()) < GraphicDriver.screenHeight/2)
				count++;
		}
		return count;
	}
	
	//Adds to the existing list of Polygon3Ds
	public int addTo(Polygon3D[] list, int index) {
		
		if (polygons == null) makePolygons();
		for (Polygon3D p : polygons) {
			//If in bounds and facing the viewer, draw it
			if (p.checkNormal() && Math.abs(p.getAvgX()) < GraphicDriver.screenWidth/2 && Math.abs(p.getAvgY()) < GraphicDriver.screenHeight/2)
				list[index++] = p;
		}
		return index;
	}
	
	
	
}
