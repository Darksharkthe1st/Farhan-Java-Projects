import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ObjShape extends Shape3D {
	Point3D[] allPoints;
	Color[] colors;
	Point3D[][] points;
	Polygon3D[] polygons;
	String filepath;
	ArrayList<String> myFaces;

	public ObjShape(double x, double y, double z, String filepath) throws FileNotFoundException {
		super(x, y, z);
		x = 0;
		y = 0;
		z = 0;
		
		this.filepath = filepath;
		setAllPoints();
		setPoints();
		setColors();
		makePolygons();
		setCenter();
		rotateAbout('x',180);
		moveBy(0,0,10);
	}

	@Override
	protected void setCenter() {
		// TODO Auto-generated method stub
		cx = x; cy = y; cz = z + 3;
	}

	@Override
	protected void setColors() {
		// TODO Auto-generated method stub
		this.colors = new Color[points.length];
		for (int i = 0; i < colors.length; i++) {
			colors[i] = GraphicDriver.randColor();
		}
	}

	@Override
	protected void setPoints() {
		Scanner lineScanner;
		this.points = new Point3D[myFaces.size()][];
		ArrayList<Integer> myFace;
		for (int i = 0; i < points.length; i++) {
			myFace = new ArrayList<Integer>();
			lineScanner = new Scanner(myFaces.get(i).substring(2));
			while (lineScanner.hasNextInt())
				myFace.add(lineScanner.nextInt());
			points[i] = new Point3D[myFace.size()];
			for (int j = 0; j < points[i].length; j++) {
				points[i][j] = allPoints[myFace.get(j) - 1];
			}
		}
		faces = points.length;
	}

	protected void setAllPoints() {
		ArrayList<Point3D> myPoints = new ArrayList<Point3D>();
		ArrayList<String> vertices = new ArrayList<String>();
		myFaces = new ArrayList<String>();
		ArrayList<String> materials = new ArrayList<String>();
		Scanner input = null;
		try {
			input = new Scanner(new File(filepath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int prevFace = 1;
		int currentFace = 1;
		while (input.hasNextLine()) {
			String line = input.nextLine();
			String identifier = line.substring(0, line.indexOf(' '));
			if (identifier.charAt(0) == '#') {
				continue;
			} else if (identifier.equals("v")) {
				vertices.add(line);
			} else if (identifier.equals("f")) {
				myFaces.add(line);
				currentFace++;
			} else if (identifier.equals("usemtl")) {
				materials.add(line + " " + prevFace + " " + currentFace);

				prevFace = currentFace + 1;
			}
		}

		this.allPoints = new Point3D[vertices.size()];
		Scanner lineScanner;
		for (int i = 0; i < allPoints.length; i++) {
			lineScanner = new Scanner(vertices.get(i).substring(2));
			allPoints[i] = new Point3D(lineScanner.nextDouble(), lineScanner.nextDouble(), lineScanner.nextDouble());
		}
	}

	// Makes the polygon from the points. Final so it cannot be changed.
	protected void makePolygons() {
		this.polygons = new Polygon3D[faces];
		for (int i = 0; i < faces; i++) {
			polygons[i] = new Polygon3D(colors[i], points[i]);
			polygons[i].setOutlineColor(outlineColor);
		}
		setPolygonNames();
	}

	// Sets the names of the polygons for testing
	protected void setPolygonNames() {
		for (int i = 0; i < faces; i++) {
			polygons[i].setName(name + (i + 1));
		}

	}

	// Paints directly on the graphics window for quick testing
	public void paintMe(Graphics g) {
		for (int face = 0; face < points.length; face++) {
			if (polygons[face] != null && polygons[face].getAvgZ() > 0) {
				g.setColor(colors[face]);
				g.fillPolygon(polygons[face].getPolygon());
			}
		}
	}

	// Rotates the shape about the specified axis by theta degrees, where axis is x,
	// y, or z.
	public void rotateAbout(char axis, double theta) {
		Matrix rotation = Point3D.rotationMatrix(theta, axis);

		for (Point3D point : allPoints) {
			point.setX(point.getX() - cx);
			point.setY(point.getY() - cy);
			point.setZ(point.getZ() - cz);

			double dist1;

			if (axis == 'z')
				dist1 = Math.sqrt(Math.pow(point.getX(), 2) + Math.pow(point.getY(), 2));
			else if (axis == 'x')
				dist1 = Math.sqrt(Math.pow(point.getZ(), 2) + Math.pow(point.getY(), 2));
			else
				dist1 = Math.sqrt(Math.pow(point.getX(), 2) + Math.pow(point.getZ(), 2));
			point.matrixTransform(rotation);
			double dist2;

			if (axis == 'z')
				dist2 = Math.sqrt(Math.pow(point.getX(), 2) + Math.pow(point.getY(), 2));
			else if (axis == 'x')
				dist2 = Math.sqrt(Math.pow(point.getZ(), 2) + Math.pow(point.getY(), 2));
			else
				dist2 = Math.sqrt(Math.pow(point.getX(), 2) + Math.pow(point.getZ(), 2));
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
		x += dx;
		y += dy;
		z += dz;
		for (Point3D[] facePoints : points) {
			for (Point3D p : facePoints) {
				p.move(dx, dy, dz);
			}
		}
		makePolygons();
		setCenter();
	}

	public int countVisible() {
		int count = 0;
		for (Polygon3D p : polygons) {
			if (p.checkNormal() && Math.abs(p.getAvgX()) < GraphicDriver.screenWidth / 2
					&& Math.abs(p.getAvgY()) < GraphicDriver.screenHeight / 2)
				count++;
		}
		return count;
	}

	// Adds to the existing list of Polygon3Ds
	public int addTo(Polygon3D[] list, int index) {
		if (polygons == null)
			makePolygons();
		for (Polygon3D p : polygons) {
			// If in bounds and facing the viewer, draw it
			if (p.checkNormal() && Math.abs(p.getAvgX()) < GraphicDriver.screenWidth / 2
					&& Math.abs(p.getAvgY()) < GraphicDriver.screenHeight / 2) {
				list[index++] = p;
			}
		}
		return index;
	}

}
