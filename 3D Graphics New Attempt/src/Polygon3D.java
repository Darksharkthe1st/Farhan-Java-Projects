import java.awt.Color;
import java.awt.Polygon;

//Class to represent polygons in 3d
//Basically same as polygon but supports color and
//also saves z position for ordering
//Not the actual 3D polygon, just the normalized polygon https://en.wikipedia.org/wiki/Normalization_(image_processing) 
public class Polygon3D implements Comparable {
	private Color fillColor;
	private Color outlineColor;
	private Point3D[] actualPoints;
	private int[] xpoints;
	private int[] ypoints;
	private double[] zpoints;
	private double avgZ;
	private double avgX;
	private double avgY;
	private double avgDist;
	private Polygon myself;
	public String name; //Name variable just to keep track of which is which when debugging
	private boolean visibility = false; //Keeps track of how visible the polygon is
	private boolean rotated = true;
	
	//Keeps track if any changes were made to the internal variables
	//If changes were made, it remakes the polygon when asked to return it
	private boolean changesMade;
	
	//Just another constructor with projection built in. The boolean has no use - it's just for overloading
	public Polygon3D(Color c, Point3D[] points) {
		this.fillColor = c;
		this.name = "Polygon";
		this.actualPoints = points;
		projectToPolygon(points);
		changesMade = true;
	}
	
	public void projectToPolygon(Point3D[] points) {
		Point3D[] result = new Point3D[points.length];
		for (int i = 0; i < result.length; i++) 
			result[i] = Point3D.project(points[i]);
		setPoints(result);
		changesMade = true;
	}
	
	public void refresh() {
		Point3D[] result = new Point3D[actualPoints.length];
		for (int i = 0; i < result.length; i++) 
			result[i] = Point3D.project(actualPoints[i]);
		setPoints(result);
		changesMade = true;
	}
	
	//Just for construction, can be used if needed to change the polygon
	public void setPoints(Point3D[] points) {
		xpoints = new int[points.length];
		ypoints = new int[points.length];
		zpoints = new double[points.length];
		for (int i = 0; i < points.length; i++) {
			xpoints[i] = (int)points[i].x;
			ypoints[i] = (int)points[i].y;
			zpoints[i] = points[i].z;
		}
		makePolygon();
		makeAvgs();
	}

	//The closest z position
	//Used for ordering when rendering
	private void makeAvgs() {
		double screenHalfWidth = GraphicDriver.screenWidth / 2;
		double screenHalfHeight = GraphicDriver.screenHeight / 2;
		avgZ = 0;
		for (double z : zpoints) {
			avgZ += z;
		}
		avgZ /= zpoints.length;
		
		avgY = 0;
		
		//We subtract screenWidth/2 and screenHeight/2 to move the origin to the center
		for (double y : ypoints) {
			avgY += y - screenHalfHeight;
		}
		avgY /= ypoints.length;
		
		avgX = 0;
		for (double x : xpoints) {
			avgX += x - screenHalfWidth;
		}
		avgX /= xpoints.length;
		
		avgDist = Math.sqrt(Math.pow(avgX, 2) + Math.pow(avgY, 2));
	}
	
	private void makePolygon() {
		myself = new Polygon(xpoints,ypoints,xpoints.length);
	}
	
	public Point3D[] getActualPoints() {
		return actualPoints;
	}

	public void setActualPoints(Point3D[] actualPoints) {
		this.actualPoints = actualPoints;
		changesMade = true;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color color) {
		this.fillColor = color;
	}
	
	public Color getOutlineColor() {
		return outlineColor;
	}

	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}

	public int[] getXpoints() {
		return xpoints;
	}

	public int[] getYpoints() {
		return ypoints;
	}

	public double[] getZpoints() {
		return zpoints;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//Used for sorting in order to visually layer polygons
	@Override
	public int compareTo(Object poly) {
		makeChanges();
		if (poly instanceof Polygon3D) {
			if ((int)((getAvgZ() - ((Polygon3D)poly).getAvgZ()) * 100) != 0) 
				return -(int)((   getAvgZ() - ((Polygon3D)poly).getAvgZ()   )  * 100);
			else 
				return -(int)((getAvgDist() - ((Polygon3D)poly).getAvgDist()) * 100);
		}
		return -1;
	}
	
	
	//Checks if we're seeing the front side of the polygon or the back side. Returns true for the front side
	public boolean checkNormal() {
		
		//Calculates the normal by
		//Taking the cross product of the vectors made by two edges at a time
		//Then, takes the dot product of the normal and the vector to the camera
		//This dot product tells us how much the normal is facing the camera
		//Does this for two edges a time for every two edges, takes the averages,
		//And returns if it's facing the camera on average
		
		double totalNormalZ = 0;
		if (actualPoints.length < 3) return true;
		for (int i = 0; i < actualPoints.length - 2; i++) {
			totalNormalZ += Point3D.getNormalToCamera(actualPoints[i], actualPoints[i+1],actualPoints[i+2]);
		}
		totalNormalZ += Point3D.getNormalToCamera(actualPoints[actualPoints.length - 2], actualPoints[actualPoints.length - 1],actualPoints[0]);
		totalNormalZ += Point3D.getNormalToCamera(actualPoints[actualPoints.length - 1], actualPoints[0],actualPoints[1]);
		
		return (totalNormalZ / (xpoints.length)) > -0.1;
	}
	
	public Color getShadedFill() {
		double totalShade = 0;
		for (int i = 0; i < actualPoints.length - 2; i++) {
			totalShade += Point3D.getNormalToLight(actualPoints[i], actualPoints[i+1],actualPoints[i+2]);
		}
		totalShade += Point3D.getNormalToLight(actualPoints[actualPoints.length - 2], actualPoints[actualPoints.length - 1],actualPoints[0]);
		totalShade += Point3D.getNormalToLight(actualPoints[actualPoints.length - 1], actualPoints[0],actualPoints[1]);
		totalShade /= xpoints.length;
		totalShade = Math.abs(totalShade);
		//System.out.println(totalShade);
		
		return new Color((int)(this.fillColor.getRed() * totalShade), (int)(this.fillColor.getGreen() * totalShade), (int)(this.fillColor.getBlue() * totalShade));
	}
	
	public double getAvgZ() {
		return avgZ;
	}
	
	public double getAvgX() {
		return avgX;
	}

	public double getAvgY() {
		return avgY;
	}
	
	public double getAvgDist() {
		return avgDist;
	}
	
	public Polygon getPolygon() {
		makeChanges();
		return myself;
	}
	
	private void makeChanges() {
		if (changesMade) {
			makeAvgs();
			makePolygon();
		}
	}
	
	public String toString() {
		String output = "{" + name;
		for (int i = 0; i < xpoints.length - 1; i++) {
			output += "{P" + (i+1) + ": x:" + xpoints[i];
			output += "; y:" + ypoints[i];
			output += "; z:" + zpoints[i] + "}, ";
		}
		output += "{P" + (xpoints.length) + ": x:" + xpoints[xpoints.length - 1];
		output += "; y:" + ypoints[xpoints.length - 1];
		output += "; z:" + zpoints[xpoints.length - 1] + "}}";
		return output;
	}
}
