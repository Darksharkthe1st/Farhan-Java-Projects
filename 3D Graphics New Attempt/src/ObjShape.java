import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ObjShape extends Shape3D {
	String filepath;
	ArrayList<String> myFaces = new ArrayList<String>();

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
		moveBy(0,0,140);
	}

	@Override
	protected void setCenter() {
		// TODO Auto-generated method stub
		cx = x; cy = y; cz = z-800;
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

	// Sets the names of the polygons for testing
	protected void setPolygonNames() {
		for (int i = 0; i < faces; i++) {
			polygons[i].setName(name + (i + 1));
		}

	}


}
