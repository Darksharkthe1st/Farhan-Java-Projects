import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JPanel;

//First 3d shape! Made without Shape3d class, solely for testing
public class Cube extends JPanel {
	 double x,  y,  z,  dx,  dy, dz;
	 Point3D[][] points;
	 Color[] colors;
	 Polygon3D[] faces;

	public Cube(double x, double y, double z, double l, double w, double h) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.dx = l/2;
		this.dz = w/2;
		this.dy = h/2;
		
		colors = new Color[6];
		int opacity = 100;
		colors[0] = new Color(0,0,0,opacity);
		colors[1] = new Color(255,0,0,opacity);
		colors[2] = new Color(0,255,0,opacity);
		colors[3] = new Color(0,0,255,opacity);
		colors[4] = new Color(0,255,255,opacity);
		colors[5] = new Color(255,0,255,opacity);
		
		points = new Point3D[6][5];
		faces = new Polygon3D[6];
		
		setPoints();
	}

	public double getXPos() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
		setPoints();
	}

	public double getYPos() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
		setPoints();
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
		setPoints();
	}

	public double getL() {
		return dx * 2;
	}

	public void setL(double l) {
		this.dx = l / 2;
		setPoints();
	}

	public double getW() {
		return dz * 2;
	}

	public void setW(double w) {
		this.dz = w / 2;
		setPoints();
	}

	public double getH() {
		return dy * 2;
	}

	public void setH(double h) {
		this.dy = h / 2;
		setPoints();
	}
	
	public void setPoints() {
		//Back face
		points[0] = new Point3D[] {
				new Point3D( x - dx, y - dy, z - dz ), 
				new Point3D( x + dx, y - dy, z - dz ), 
				new Point3D( x + dx, y + dy, z - dz ), 
				new Point3D( x - dx, y + dy, z - dz ), 
				new Point3D( x - dx, y - dy, z - dz )
		};
		
		//Right Face
		
		points[1] = new Point3D[] {
				new Point3D( x + dx, y - dy, z - dz ), 
				new Point3D( x + dx, y - dy, z + dz ), 
				new Point3D( x + dx, y + dy, z + dz ), 
				new Point3D( x + dx, y + dy, z - dz ), 
				new Point3D( x + dx, y - dy, z - dz )
		};

		
		//Front Face
		points[2] = new Point3D[] {
				new Point3D( x - dy, y - dy, z  +  dz ), 
				new Point3D( x + dx, y - dy, z  +  dz ), 
				new Point3D( x + dx, y + dy, z  +  dz ), 
				new Point3D( x - dx, y + dy, z  +  dz ), 
				new Point3D( x - dx, y - dy, z  +  dz )
		};
		
		//Left Face
		points[3] = new Point3D[] {
				new Point3D( x - dx, y - dy, z - dz ), 
				new Point3D( x - dx, y - dy, z + dz ), 
				new Point3D( x - dx, y + dy, z + dz ), 
				new Point3D( x - dx, y + dy, z - dz ), 
				new Point3D( x - dx, y - dy, z - dz )
		};
		
		//Bottom Face
		
		points[4] = new Point3D[] {
				new Point3D( x - dx, y + dy, z - dz ), 
				new Point3D( x + dx, y + dy, z - dz ), 
				new Point3D( x + dx, y + dy, z + dz ), 
				new Point3D( x - dx, y + dy, z + dz ), 
				new Point3D( x - dx, y + dy, z - dz )
		};
		
		//Top Face
		points[5] = new Point3D[] {
				new Point3D( x - dx, y - dy, z - dz ), 
				new Point3D( x + dx, y - dy, z - dz ), 
				new Point3D( x + dx, y - dy, z + dz ), 
				new Point3D( x - dx, y - dy, z + dz ), 
				new Point3D( x - dx, y - dy, z - dz )
		};
		
		faces = new Polygon3D[points.length];
		for (int i = 0; i < points.length; i++) {
			faces[i] = new Polygon3D(colors[i], points[i]);
		}
				
				
	}
	
	public void paintComponent(Graphics g) {		
		for (int face = 0; face < points.length; face++) {
			if (faces[face].getAvgZ() > 0) {
				g.setColor(colors[face]);
				g.fillPolygon(faces[face].getPolygon());
			}
		}
	}
	
	 
}
