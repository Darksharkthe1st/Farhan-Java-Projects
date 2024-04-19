import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ObjShape extends Shape3D {
	String objFilename;
	String filedir;
	String mtlFilename;
	ArrayList<String> myFaces = new ArrayList<String>();
	ArrayList<String> materials = new ArrayList<String>();
	double xOffset, yOffset, zOffset;
	public ObjShape(double x, double y, double z, String filepath) throws FileNotFoundException {
		super(x, y, z);
		
		this.objFilename = filepath.substring(filepath.lastIndexOf('\\')+1);
		this.filedir = filepath.substring(0,filepath.lastIndexOf('\\') + 1);
		setAllPoints();
		setPoints();
		setColors();
		makePolygons();
		setCenter();
		xOffset = 4.5; yOffset = 0; zOffset = 10;
		double tempX = x;
		double tempY = y;
		double tempZ = z;
		x=0;
		y=0;
		z=0;
		moveBy(tempX,tempY,tempZ);
		setCenter();
	}

	@Override
	protected void setCenter() {
		// TODO Auto-generated method stub
		cx = x+xOffset; cy = y+yOffset; cz = z+zOffset;
	}
	
	@Override
	protected void setColors() {
		// TODO Auto-generated method stub
		this.colors = new Color[points.length];
		for (int i = 0; i < colors.length; i++) {
			colors[i] = Color.white;
		}
	}

	@Override
	protected void setPoints() {
		Scanner lineScanner;
		this.points = new Point3D[myFaces.size()][];
		for (int i = 0; i < points.length; i++) {
			myFaces.set(i, myFaces.get(i).substring(myFaces.get(i).indexOf(' ') + 1));
			String[] pts = myFaces.get(i).split(" ");
			for (int j = 0; j < pts.length; j++) {
				
				if (pts[j].indexOf('/') > 0) {
					pts[j] = pts[j].substring(0,pts[j].indexOf('/'));
				}
			}
			points[i] = new Point3D[pts.length];
			for (int j = 0; j < points[i].length; j++) {
				points[i][j] = allPoints[Integer.valueOf(pts[j]) - 1];
			}
		}
		faces = points.length;
		myFaces = null;
		
		for (String line : materials) {
			
		}
	}
	
	private Color getMtl(String name) {
		
	}

	protected void setAllPoints() {
		ArrayList<Point3D> myPoints = new ArrayList<Point3D>();
		ArrayList<String> vertices = new ArrayList<String>();
		myFaces = new ArrayList<String>();
		materials = new ArrayList<String>();
		Scanner input = null;
		try {
			input = new Scanner(new File(filedir + objFilename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int currentFace = 0;
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
				if (materials.size() > 0)
					materials.set(materials.size() - 1, materials.get(materials.size() - 1) + " " + currentFace);
				materials.add(line + " " + currentFace);
			} else if (identifier.equals("mtllib")) {
				mtlFilename = line.substring(line.indexOf(' ') + 1);
			}
			
		}
		if (materials.size() > 0)
			materials.set(materials.size() - 1, materials.get(materials.size() - 1) + " " + currentFace);
		System.out.println(materials);
		this.allPoints = new Point3D[vertices.size()];
		Scanner lineScanner;
		for (int i = 0; i < allPoints.length; i++) {
			lineScanner = new Scanner(vertices.get(i).substring(2));
			allPoints[i] = new Point3D(-lineScanner.nextDouble(), -lineScanner.nextDouble(), -lineScanner.nextDouble());
		}
	}

	// Sets the names of the polygons for testing
	protected void setPolygonNames() {
		for (int i = 0; i < faces; i++) {
			polygons[i].setName(name + (i + 1));
		}

	}

	public double getxOffset() {
		return xOffset;
	}

	public void setxOffset(double xOffset) {
		this.xOffset = xOffset;
	}

	public double getyOffset() {
		return yOffset;
	}

	public void setyOffset(double yOffset) {
		this.yOffset = yOffset;
	}

	public double getzOffset() {
		return zOffset;
	}

	public void setzOffset(double zOffset) {
		this.zOffset = zOffset;
	}
}
