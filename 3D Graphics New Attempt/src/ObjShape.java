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
	ArrayList<Integer> mtlOrder = new ArrayList<Integer>();
	ArrayList<Color> mtlColors = new ArrayList<Color>();
	double xOffset, yOffset, zOffset;
	public ObjShape(double x, double y, double z, String filepath) throws FileNotFoundException {
		super(x, y, z);
		
		this.objFilename = filepath.substring(filepath.lastIndexOf('\\')+1);
		this.filedir = filepath.substring(0,filepath.lastIndexOf('\\') + 1);
		setAllPoints();
		setPoints();
		setColors();
		setCenter();
		makePolygons();
		xOffset = 4.5; yOffset = 0; zOffset = 10;
		double tempX = x;
		double tempY = y;
		double tempZ = z;
		x=0;
		y=0;
		z=0;
		moveBy(tempX,tempY,tempZ);
		setCenter();
		if (!allPoints[allPoints.length - 1].equals(center)) {
			Point3D[] newAllPoints = new Point3D[allPoints.length + 1];
			for (int i = 0; i < allPoints.length; i++) {
				newAllPoints[i] = allPoints[i];
			}
			newAllPoints[newAllPoints.length - 1] = center;
			allPoints = newAllPoints;
			System.out.println("NEW CENTER, " + objFilename);
		}
	}

	@Override
	protected void setCenter() {
		centerInAllPoints();
		if (center==null) {center = new Point3D(x+xOffset, y+yOffset,z+zOffset);
		System.out.println("NULL CENTER");}
		else center.setX(x+xOffset); center.setY(y+yOffset); center.setZ(z+zOffset);
	}
	
	@Override
	protected void setColors() {
		// TODO Auto-generated method stub
		this.colors = new Color[points.length];
		if (materials.size() > 0) {
			int index = 0;
			int currentMtl = 0;
			while (index < colors.length) {
				if (index >= mtlOrder.get(currentMtl)) {
					currentMtl++;
				}
				colors[index++] = getMtl(materials.get(currentMtl-1));
			}
		} else {
			for (int i = 0; i < colors.length; i++) {
				colors[i] = GraphicDriver.randColor();
			}
		}
		
		System.out.println(mtlOrder);
	}

	@Override
	protected void setPoints() {
		Scanner lineScanner;
		this.points = new Point3D[myFaces.size()][];
		
		for (int i = 0; i < allPoints.length; i++) {
			System.out.println(i + ": " + allPoints[i] + ", " + allPoints[i].distTo(null));
			allPoints[i].setZ(allPoints[i].z + 5);
			System.out.println(Point3D.project(allPoints[i]) + "\n");
			allPoints[i].setZ(allPoints[i].z - 5);
		}
		
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
				System.out.println(i + ", " + j + ": " + allPoints[Integer.valueOf(pts[j]) - 1] + ", " + allPoints[Integer.valueOf(pts[j]) - 1].distTo(null) + "\n");
			}
		}
		faces = points.length;
		myFaces = null;
		
	}
	
	private Color getMtl(String name) {
		System.out.println(name);
		if (name.equals("m_Body"))
			return Color.red;
		else if (name.equals("Material.001"))
			return Color.pink;
		else if (name.equals("Material.002"))
			return Color.pink;
		else if (name.equals("Material.003"))
			return Color.green;
		return Color.gray;
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
				materials.add(line.substring(line.indexOf(" ") + 1));
				mtlOrder.add(currentFace);
			} else if (identifier.equals("mtllib")) {
				mtlFilename = line.substring(line.indexOf(' ') + 1);
			}	
		}
		mtlOrder.add(currentFace);
		System.out.println(materials);
		this.allPoints = new Point3D[vertices.size()];
		Scanner lineScanner;
		for (int i = 0; i < allPoints.length; i++) {
			lineScanner = new Scanner(vertices.get(i).substring(2));
			allPoints[i] = new Point3D(lineScanner.nextDouble(), lineScanner.nextDouble(), -lineScanner.nextDouble());
			double dist = Math.sqrt(Math.pow(allPoints[i].x,2) + Math.pow(allPoints[i].y,2) + Math.pow(allPoints[i].z,2));
			if (dist > 2.9)
				System.out.println("E " + dist + ", " + allPoints[i].z + ", " + vertices.get(i));
			System.out.println(i + ", " + allPoints[i]);
		}
		
		mtlColors = new ArrayList<Color>();
		input = null;
		try {
			input = new Scanner(new File(filedir + mtlFilename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
