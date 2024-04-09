import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.Arrays;

public class Point3d extends Matrix {
	double x, y, z;
	public Point3d(double x, double y, double z) {
		super(4,1);
		this.x = x;
		this.y = y;
		this.z = z;
		arraytrix = new double[4][1];
		arraytrix[0][0] = x;
		arraytrix[1][0] = y;
		arraytrix[2][0] = z;
		arraytrix[3][0] = 1;
	}
	
	private Point3d(double x, double y, double z, double w) {
		super(4,1);
		this.x = x;
		this.y = y;
		this.z = z;
		arraytrix = new double[4][1];
		arraytrix[0][0] = x;
		arraytrix[1][0] = y;
		arraytrix[2][0] = z;
		arraytrix[3][0] = w;
	}
	
	private Point3d(Matrix m) {
		super(m.arraytrix);
		x = arraytrix[0][0];
		y = arraytrix[1][0];
		z = arraytrix[2][0];
	}
	
	public double getX() {
		return x;
	}
	public void setX(double d) {
		this.x = d;
		arraytrix[0][0] = d;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
		arraytrix[1][0] = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
		arraytrix[2][0] = z;
	}
	
	//Just matrix multiply, but the class variables of the Point3d change too.
	public void matrixTransform(Matrix other) {
		this.multiply(other);
		x = arraytrix[0][0];
		y = arraytrix[1][0];
		z = arraytrix[2][0];
	}
	
	//Just matrix multiply, but the class variables of the Point3d change too.
	public static Point3d matrixTransform(Point3d point, Matrix other) {
		double x = point.x * other.arraytrix[0][0] + point.y * other.arraytrix[0][1] + point.z * other.arraytrix[0][2] + point.arraytrix[3][0] * other.arraytrix[0][3];
		double y = point.x * other.arraytrix[1][0] + point.y * other.arraytrix[1][1] + point.z * other.arraytrix[1][2] + point.arraytrix[3][0] * other.arraytrix[1][3];
		double z = point.x * other.arraytrix[2][0] + point.y * other.arraytrix[2][1] + point.z * other.arraytrix[2][2] + point.arraytrix[3][0] * other.arraytrix[2][3];
		double w = point.x * other.arraytrix[3][0] + point.y * other.arraytrix[3][1] + point.z * other.arraytrix[3][2] + point.arraytrix[3][0] * other.arraytrix[3][3];
		
		
	
		return new Point3d(x,y,z,w);
	}
	
	public static Matrix projectionMatrix(double width, double height, double fov, double zFar, double zNear) {
		double[][] arrayx = new double[4][4];
		double aspectRatio = height / width;
		double fovRad = 1 / Math.tan(fov * 0.5 * Math.PI / 180);
		arrayx[0][0] = aspectRatio * fovRad;
		arrayx[1][1] =  fovRad;
		arrayx[2][2] = zFar / (zFar - zNear);
		arrayx[2][3] = (-zFar * zNear) / (zFar - zNear);
		arrayx[3][2] = 1;
		
		return new Matrix(arrayx);
	}
	
	public static Point3d project(Matrix projMatrix, Point3d point) {
		double oldZ = point.z;
		testingOutput("P1: " + point);
		point = matrixTransform(point, projMatrix);
		double w = point.arraytrix[3][0];
		

		testingOutput(projMatrix);
		
		testingOutput("P2: " + point);
		
		if (Math.abs(w) > 0.01) {
			point.setX(point.x/w);
			point.setY(point.y/w);
			point.setZ(point.z/w);
		}
		
		testingOutput("P3: " + point);
		
		
		
		point.setX(point.x * GraphicDriver.screenWidth);
		point.setY(point.y * GraphicDriver.screenHeight);
		testingOutput("P5: " + point);
		testingOutput("--------------------------------------------------");
		return point;
	}
	
	public static Polygon PointsToPolygon(Point3d[] points) {
		int[] xpoints = new int[points.length];
		int[] ypoints = new int[points.length];
		for (int i = 0; i < points.length; i++) {
			xpoints[i] = (int)points[i].getX();
			ypoints[i] = (int)points[i].getY();
		}
		return new Polygon(xpoints,ypoints, points.length);
		
	}
	
	public String toString() {
		String output = "";
		output += "{m: " + m + ", n: " + n + "} { x: " + x + ", y: " + y + ", z: " + z + ", w: " + arraytrix[3][0] + "}\n";
		//output += Arrays.deepToString(arraytrix) + "}\n";
		return output;
	}
	
	private static void testingOutput(Object s) {
		if (true) 
			System.out.println(s);
	}
	
}
