import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.Arrays;

//Represents a point in 3D space that can be manipulated by my Matrix class
public class Point3D{
	double x, y, z, w;
	
	public static Matrix projector;
	public static Point3D lightSource = new Point3D(0,0.7,0.5); //Vector representing direction of light
	//The camera is where we imagine our imaginary viewer being placed
	public static Point3D camera = new Point3D(0,0,0);
	
	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = 1;
	}
	
	//Constructor only used in static methods, w is used for translation and projection (non-homogenous coordinates)
	private Point3D(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double d) {
		this.x = d;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	
	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}
	
	//Just matrix multiply, but done in a non-mutating way
	public static Point3D matrixTransform(Point3D point, Matrix other) {
		double x = point.x * other.arraytrix[0][0] + point.y * other.arraytrix[0][1] + point.z * other.arraytrix[0][2] + point.w * other.arraytrix[0][3];
		double y = point.x * other.arraytrix[1][0] + point.y * other.arraytrix[1][1] + point.z * other.arraytrix[1][2] + point.w * other.arraytrix[1][3];
		double z = point.x * other.arraytrix[2][0] + point.y * other.arraytrix[2][1] + point.z * other.arraytrix[2][2] + point.w * other.arraytrix[2][3];
		double w = point.x * other.arraytrix[3][0] + point.y * other.arraytrix[3][1] + point.z * other.arraytrix[3][2] + point.w * other.arraytrix[3][3];
	
		return new Point3D(x,y,z,w);
	}

	//Transforms a point by a matrix through mulitplication
	public void matrixTransform(Matrix transformer) {
		x = x * transformer.arraytrix[0][0] + y * transformer.arraytrix[0][1] + z * transformer.arraytrix[0][2] + w * transformer.arraytrix[0][3];
		y = x * transformer.arraytrix[1][0] + y * transformer.arraytrix[1][1] + z * transformer.arraytrix[1][2] + w * transformer.arraytrix[1][3];
		z = x * transformer.arraytrix[2][0] + y * transformer.arraytrix[2][1] + z * transformer.arraytrix[2][2] + w * transformer.arraytrix[2][3];
		w = x * transformer.arraytrix[3][0] + y * transformer.arraytrix[3][1] + z * transformer.arraytrix[3][2] + w * transformer.arraytrix[3][3];
	}
	
	//Generates the projection matrix using the Driver's set values
	public static Matrix projectionMatrix() {
		//No need to make the projection matrix if it already exists
		if (projector == null) { 
			double width = GraphicDriver.screenWidth;
			double height = GraphicDriver.screenHeight;
			double fov = GraphicDriver.fov;
			double zFar = GraphicDriver.zFar;
			double zNear = GraphicDriver.zNear;
			double[][] arrayx = new double[4][4];
			double aspectRatio = height / width;
			double fovRad = 1 / Math.tan(fov * 0.5 * Math.PI / 180);
			arrayx[0][0] = aspectRatio * fovRad;
			arrayx[1][1] = fovRad;
			arrayx[2][2] = zFar / (zFar - zNear);
			arrayx[2][3] = -(zFar * zNear) / (zFar - zNear);
			arrayx[3][2] = 1;
			projector = new Matrix(arrayx);
		}
		return projector;
	}
	
	//Matrix to rotate about dimension axis by theta degrees, where dimension is x, y, or z. Returns null if dimension not x, y, or z.
	public static Matrix rotationMatrix(double theta, char dimension) {
		dimension = Character.toLowerCase(dimension); //Lowercase in case they accidentally capitalize
		Matrix result = Matrix.identity(4);
		double[][] arrayx = result.arraytrix;
		double cosTheta = (Math.cos(theta * Math.PI / 180));
		double sinTheta = (Math.sin(theta * Math.PI / 180));
		if (dimension == 'x') { //Rot about x axis
			arrayx[1][1] = cosTheta;
			arrayx[1][2] = -sinTheta;
			arrayx[2][1] = sinTheta;
			arrayx[2][2] = cosTheta;
		} else if (dimension == 'y') { //Rot about y axis
			arrayx[0][0] = cosTheta;
			arrayx[0][2] = sinTheta;
			arrayx[2][0] = -sinTheta;
			arrayx[2][2] = cosTheta;
		} else if (dimension == 'z') { //Rot about z axis
			arrayx[0][0] = cosTheta;
			arrayx[0][1] = -sinTheta;
			arrayx[1][0] = sinTheta;
			arrayx[1][1] = cosTheta;
		} else {
			return null;
		}
		return result;
	}
	
	//Matrix to translate by distances x, y, and z
	public static Matrix translationMatrix(double x, double y, double z) {
		Matrix result = Matrix.identity(4);
		result.arraytrix[0][3] = x;
		result.arraytrix[1][3] = y;
		result.arraytrix[2][3] = z;	
		return result;
	}
	
	//Projects a point using the projection matrix
	public static Point3D project(Matrix projMatrix, Point3D point) {
		point = matrixTransform(point, projMatrix);
		
		if (Math.abs(point.w) > 0.01) {
			point.setX(point.x/point.w);
			point.setY(point.y/point.w);
		}
		
		point.setX(point.x + 0.5);
		point.setY(point.y + 0.5);
		
		point.setX(point.x * GraphicDriver.screenWidth);
		point.setY(point.y * GraphicDriver.screenHeight);
		return point;
	}

	//Transforms all the points by the parameter matrix
	public static void transformPoints(Matrix transformation, Point3D[] points) {
		for (Point3D p : points) {
			p.matrixTransform(transformation);
		}
	}
	
	//Takes three points, 
	//Finds the normal vector of edges AB and BC through a cross product
	//Returns the dot product of the normal and the vector of Camera to B
	public static double getNormalToCamera(Point3D a, Point3D b, Point3D c) {
		
		double ax = a.x - b.x;
		double ay = a.y - b.y;
		double az = a.z - b.z;
		
		double cx = c.x - b.x;
		double cy = c.y - b.y;
		double cz = c.z - b.z;
		
		
		Point3D normal = new Point3D(
				-((ay * cz) - (az * cy)),
				-((az * cx) - (ax * cz)),
				-((ax) * (cy) - (ay) * (cx)));
		return dotProduct(normal.normalize(), new Point3D(b.x - camera.x, b.y - camera.y, b.z - camera.z).normalize());
	}
	
	//Takes three points, 
		//Finds the normal vector of edges AB and BC through a cross product
		//Returns the dot product of the normal and the vector of Light to B
		public static double getNormalToLight(Point3D a, Point3D b, Point3D c) {
			
			double ax = a.x - b.x;
			double ay = a.y - b.y;
			double az = a.z - b.z;
			
			double cx = c.x - b.x;
			double cy = c.y - b.y;
			double cz = c.z - b.z;
			
			
			Point3D normal = new Point3D(
					(ay * cz) - (az * cy),
					(az * cx) - (ax * cz),
					(ax) * (cy) - (ay) * (cx));
			return Math.max(dotProduct(normal.normalize(), lightSource.normalize()),0.5);
		}
	
	public Point3D normalize() {
		double length = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
		x /= length;
		y /= length;
		z /= length;
		return this;
	}
	
	public void move(double dx, double dy, double dz) {
		x += dx;
		y += dy;
		z += dz;
	}
	
	public static Point3D getCamera() {
		return camera;
	}

	public static void setCamera(Point3D camera) {
		Point3D.camera = camera;
	}

	//Dot product of two points
	public static double dotProduct(Point3D a, Point3D b) {
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}
	
	public String toString() {
		String output = "";
		output += "{ x: " + x + ", y: " + y + ", z: " + z + ", w: " + w + "}\n";
		//output += Arrays.deepToString(arraytrix) + "}\n";
		return output;
	}
	
}
