import java.awt.Color;


//Replacement for class cube to extend Shape3D
public class betterCube extends Shape3D {
	double dx, dy, dz;
	
	//l is x length, h is y length, w is z length
	public betterCube(double x, double y, double z, double l, double w, double h) {
		super(x, y, z, 6);
		this.dx = l/2;
		this.dy = h/2;
		this.dz = w/2;
		this.name = "betterCube";
		setColors();
		setAllPoints();
		setPoints();
		makePolygons();
		setCenter();
	}
	
	public void setL(double l) {
		this.dx = l / 2;
		makePolygons();
	}

	public double getW() {
		return dz * 2;
	}

	public void setW(double w) {
		this.dz = w / 2;
		makePolygons();
	}

	public double getH() {
		return dy * 2;
	}

	public void setH(double h) {
		this.dy = h / 2;
		makePolygons();
	}
	
	@Override
	protected void setPoints() {
		points = new Point3D[6][];
		// TODO Auto-generated method stub
		// Front face

		Point3D a = allPoints[0];
		Point3D b = allPoints[1];
		Point3D c = allPoints[2];
		Point3D d = allPoints[3];

		Point3D e = allPoints[4];
		Point3D f = allPoints[5];
		Point3D g = allPoints[6];
		Point3D h = allPoints[7];

		// Front Face
		points[0] = new Point3D[] { d, c, b, a };

		// Right Face
		points[1] = new Point3D[] { g, d, a, f };

		// Back Face
		points[2] = new Point3D[] { h, g, f, e };

		// Left Face
		points[3] = new Point3D[] { c, h, e, b };

		// Bottom Face
		points[4] = new Point3D[] { g, h, c, d };

		// Top Face
		points[5] = new Point3D[] { e, f, a, b };
	}
	
	protected void setAllPoints() {
		allPoints = new Point3D[8];
		allPoints[0] = new Point3D(x + dx, y - dy, z - dz);
		allPoints[1] = new Point3D(x - dx, y - dy, z - dz);
		allPoints[2] = new Point3D(x - dx, y + dy, z - dz);
		allPoints[3] = new Point3D(x + dx, y + dy, z - dz);
		allPoints[4] = new Point3D(x - dx, y - dy, z + dz);
		allPoints[5] = new Point3D(x + dx, y - dy, z + dz);
		allPoints[6] = new Point3D(x + dx, y + dy, z + dz);
		allPoints[7] = new Point3D(x - dx, y + dy, z + dz);
	}

	@Override
	protected void setCenter() {
		// TODO Auto-generated method stub
		this.center.x = x;
		this.center.y = y;
		this.center.z = z;
	}
	
	//Setting polygon names for debugging
	protected void setPolygonNames() {
		polygons[0].setName(name + " Front G");
		polygons[1].setName(name + " Right R");
		polygons[2].setName(name + " Back Bla");
		polygons[3].setName(name + " Left Blu");
		polygons[4].setName(name + " Bottom Pin");
		polygons[5].setName(name + " Top Yel");
	}

	@Override
	protected void setColors() {
		int opacity = 255;
		// TODO Auto-generated method stub
		colors[0] = new Color(0,255,0,opacity); //Front, Green
		colors[1] = new Color(255,0,0,opacity); //Right, Red
		colors[2] = new Color(0,0,0,opacity); //Back, Black
		colors[3] = new Color(0,0,255,opacity); //Left, Blue
		colors[4] = new Color(255,0,255,opacity); //Bottom, Pink
		colors[5] = new Color(255,255,0,opacity); //Top, Yellow
	}
}
