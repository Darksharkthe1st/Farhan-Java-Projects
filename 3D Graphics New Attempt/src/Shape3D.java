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
	protected Point3D center;
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
		center = new Point3D(0,0,0);
		colors = new Color[faces];
		points = new Point3D[faces][];
		polygons = new Polygon3D[faces];
		setup();
	}

	protected void setup() {
		setColors();
		setAllPoints();
		setPoints();
		makePolygons();
		setCenter();
		centerInAllPoints();
	}
	
	//This is only for objShape, all of the shape stuff is handled in there
	//The separate constructor for objShape is so it can be made without knowing faces in advance
	protected Shape3D(double x, double y, double z) {
		center = new Point3D(0,0,0);
		this.x = x;
		this.y = y;
		this.z = z;
		this.name = "Shape";
	}
	
	public double getXPos() {
		return x;
	}

	public  void setX(double x) {
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
	
	public double getCx() {
		return center.x;
	}

	public double getCy() {
		return center.y;
	}

	public double getCz() {
		return center.z;
	}

	public void setPosition(Point3D p) {
		moveBy(p.x - x, p.y - y, p.z - z);
	}
	
	public void setPosition(double x, double y, double z) {
		moveBy(x - this.x, y - this.y, z - this.z);
	}
	
	//Set values of cx, cy, and cz (center of shape) for use in rotation 
	protected abstract void setCenter();
	
	protected final void centerInAllPoints() {
		if (!allPoints[allPoints.length - 1].equals(center)) {
			Point3D[] newAllPoints = new Point3D[allPoints.length + 1];
			for (int i = 0; i < allPoints.length; i++) {
				newAllPoints[i] = allPoints[i];
			}
			newAllPoints[newAllPoints.length - 1] = center;
			allPoints = newAllPoints;
			System.out.println("NEW CENTER");
		}
	}
	
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
	
	//Makes all the points. Shape3Ds will have the center added to allPoints if not done already.
	protected abstract void setAllPoints();
	
	//Makes the polygon from the points.  so it cannot be changed.
	protected final void makePolygons() {
		setCenter();
		polygons = new Polygon3D[faces];
		for (int i = 0; i < faces; i++) {
			polygons[i] = new Polygon3D(colors[i], points[i]);
			polygons[i].setOutlineColor(outlineColor);
		}
		setPolygonNames();
	}
	
	protected final void refreshPolygons() {
		for (int i = 0; i < faces; i++) {
			polygons[i].refresh();
		}
	}
	
	//Sets the names of the polygons for testing
	protected void setPolygonNames() {
		for (int i = 0; i < faces; i++) {
			polygons[i].setName(name + (i + 1));
		}
		
	}
	
	//Paints directly on the graphics window for quick testing
	public final void paintMe(Graphics g) {
		for (int face = 0; face < points.length; face++) {
			if (polygons[face] != null && polygons[face].getAvgZ() > 0) {
				g.setColor(colors[face]);
				g.fillPolygon(polygons[face].getPolygon());
			}
		}
	}
	
	//Rotates the shape about the specified axis by theta degrees, where axis is x, y, or z.
	public final void rotateAbout(char axis, double theta) {
		//System.out.println("C1: " + center);
		//setCenter();
		//System.out.println("My Center: " + center);
		//System.out.println("C2: " + center);
		Matrix rotation = Point3D.rotationMatrix(theta, axis);
		for (Point3D point : allPoints) {
			if (point.equals(center)) continue;
			//System.out.println("CENTERE: " + point.x + ", " + point.y + ", " + point.z);
			if (axis != 'x')
				point.setX(point.getX() - center.x);
			if (axis != 'y')
				point.setY(point.getY() - center.y);
			if (axis != 'z')
				point.setZ(point.getZ() - center.z);
			
			//System.out.println("NEWP: " + point.x + ", " + point.y + ", " + point.z);
			
			double dist1;
			
			if (axis == 'z') dist1 = Math.sqrt(Math.pow(point.getX(), 2) + Math.pow(point.getY(), 2));
			else if (axis == 'x') dist1 = Math.sqrt(Math.pow(point.getZ(), 2) + Math.pow(point.getY(), 2));
			else dist1 = Math.sqrt(Math.pow(point.getX(), 2) + Math.pow(point.getZ(), 2));
			
			if (Math.abs(dist1) < 0.01)
				continue;
			
			
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
			
			//System.out.println("SHEWP: " + point.x + ", " + point.y + ", " + point.z);
			
			
			if (axis != 'x')
				point.setX(point.getX() + center.x);
			if (axis != 'y')
				point.setY(point.getY() + center.y);
			if (axis != 'z')
				point.setZ(point.getZ() + center.z);

			//System.out.println("POINTERE: " + point.x + ", " + point.y + ", " + point.z);
		}
		//System.out.println("C3: " + center);
		refreshPolygons();
		//System.out.println("C4: " + center);
	}
	
	//Moves the shape by dx, dy, and dz.
	public final void moveBy(double dx, double dy, double dz) {
		Matrix transMatrix = Point3D.translationMatrix(dx, dy, dz);
		for (Point3D[] facePoints : points) {
			for (Point3D p : facePoints) {
				p.setX(p.x+dx);
				p.setY(p.y+dy);
				p.setZ(p.z+dz);
				
			}
		}
		
//		System.out.println("CENTERCAHGNE");
//		System.out.println(center);
		center.setX(center.x+dx);
		center.setY(center.y+dy);
		center.setZ(center.z+dz);
//		System.out.println(center);
		x+= dx;
		y+= dy;
		z+= dz;
		refreshPolygons();
	}
	
	public final void scale(double x, double y, double z) {
		if (center == null) setCenter();
		System.out.println("Scaled");
		for (Point3D p : allPoints) {
			p.scale(x, y, z);
		}
		refreshPolygons();
		System.out.println("MECENTER: " + center);
	}
	
	public final int countVisible() {
		int count = 0;
		for (Polygon3D p : polygons) {
			if (p.checkNormal() && Math.abs(p.getAvgX()) < GraphicDriver.screenWidth/2 && Math.abs(p.getAvgY()) < GraphicDriver.screenHeight/2)
				count++;
		}
		return count;
	}
	
	//Adds to the existing list of Polygon3Ds
	public final int addTo(Polygon3D[] list, int index) {
		
		if (polygons == null) makePolygons();
		for (Polygon3D p : polygons) {
			//If in bounds and facing the viewer, draw it
			if (p.checkNormal() && Math.abs(p.getAvgX()) < GraphicDriver.screenWidth/2 && Math.abs(p.getAvgY()) < GraphicDriver.screenHeight/2)
				list[index++] = p;
		}
		return index;
	}
	
	//returns the 0th point in the shape.
	public Point3D getP0() {
		return allPoints[0];
	}
	
	
	
}
