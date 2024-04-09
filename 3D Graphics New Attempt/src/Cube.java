import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JPanel;

public class Cube extends JPanel {
	 int x,  y,  z,  dx,  dy, dz;
	 Point3d[][] points;
	 Color[] colors;

	public Cube(int x, int y, int z, int l, int w, int h) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.dx = l;
		this.dz = w;
		this.dy = h;
		
		colors = new Color[6];
		int opacity = 100;
		colors[0] = new Color(0,0,0,opacity);
		colors[1] = new Color(255,0,0,opacity);
		colors[2] = new Color(0,255,0,opacity);
		colors[3] = new Color(0,0,255,opacity);
		colors[4] = new Color(0,255,255,opacity);
		colors[5] = new Color(255,0,255,opacity);
		
		points = new Point3d[6][5];
		//Back face
		points[0] = new Point3d[] {
				new Point3d( x, y, z ), 
				new Point3d( x+dx, y, z ), 
				new Point3d( x+dx, y+dy, z ), 
				new Point3d( x, y+dy, z ), 
				new Point3d( x, y, z )
		};
		
		//Right Face
		
		points[1] = new Point3d[] {
				new Point3d( x+dx, y, z ), 
				new Point3d( x+dx, y, z+dz ), 
				new Point3d( x+dx, y+dy, z+dz ), 
				new Point3d( x+dx, y+dy, z ), 
				new Point3d( x+dx, y, z )
		};

		
		
		//Front Face
		points[2] = new Point3d[] {
				new Point3d( x, y, z + dz ), 
				new Point3d( x+dx, y, z + dz ), 
				new Point3d( x+dx, y+dy, z + dz ), 
				new Point3d( x, y+dy, z + dz ), 
				new Point3d( x, y, z + dz )
		};
		
		//Left Face
		points[3] = new Point3d[] {
				new Point3d( x, y, z ), 
				new Point3d( x, y, z+dz ), 
				new Point3d( x, y+dy, z+dz ), 
				new Point3d( x, y+dy, z ), 
				new Point3d( x, y, z )
		};
		
		//Bottom Face
		
		points[4] = new Point3d[] {
				new Point3d( x, y+dy, z ), 
				new Point3d( x+dx, y+dy, z ), 
				new Point3d( x+dx, y+dy, z+dz ), 
				new Point3d( x, y+dy, z+dz ), 
				new Point3d( x, y+dy, z )
		};
		
		//Top Face
		points[5] = new Point3d[] {
				new Point3d( x, y, z ), 
				new Point3d( x+dx, y, z ), 
				new Point3d( x+dx, y, z+dz ), 
				new Point3d( x, y, z+dz ), 
				new Point3d( x, y, z )
		};
				
		
		
	}

	public int getXPos() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getYPos() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int getL() {
		return dx;
	}

	public void setL(int l) {
		this.dx = l;
	}

	public int getW() {
		return dz;
	}

	public void setW(int w) {
		this.dz = w;
	}

	public int getH() {
		return dy;
	}

	public void setH(int h) {
		this.dy = h;
	}
	
	public void paintComponent(Graphics g) {
		System.out.println(Arrays.deepToString(points));
		
		Matrix projector = Point3d.projectionMatrix(GraphicDriver.screenWidth, 
				GraphicDriver.screenHeight,
				GraphicDriver.fov,
				GraphicDriver.vFar,
				GraphicDriver.vNear);
		
		
		Point3d[][] ps2 = new Point3d[points.length][points[0].length];
		for (int i = 0; i < points.length; i++) {
			ps2[i] = new Point3d[points[i].length];
			for (int j = 0; j < points[i].length; j++) {
				ps2[i][j] = Point3d.project(projector, points[i][j]);
			}
		}

		g.setColor(GraphicDriver.randColor());
		for (int face = 0; face < points.length; face++) {
			g.setColor(colors[face]);
			g.fillPolygon(Point3d.PointsToPolygon(ps2[face]));
		}
	}
	
	 
}
