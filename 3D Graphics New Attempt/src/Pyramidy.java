import java.awt.Color;
import java.util.Set;


//Replacement for class cube to extend Shape3D
public class Pyramidy extends Shape3D {
	double dx, dy, dz;
	
	//l is x length, h is y length, w is z length
	public Pyramidy(double x, double y, double z, double l, double w, double h) {
		super(x, y, z, 5);
		this.dx = l/2;
		this.dy = h/2;
		this.dz = w/2;
		this.name = "betterCube";
		setup();
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
		points = new Point3D[faces][];
		// TODO Auto-generated method stub
		// Front face

		Point3D a = allPoints[0]; // front right
		Point3D b = allPoints[1]; // front left
		Point3D c = allPoints[2]; // back left
		Point3D d = allPoints[3]; // back right

		Point3D e = allPoints[4]; //tip
		// Bottom Face
		points[0] = new Point3D[] { a, b, c, d };

		// Front Face
		points[1] = new Point3D[] { a, b, e };

		//Left face
		points[2] = new Point3D[] { b,c,e };

		points[3] = new Point3D[] {c,d,e};

		points[4] = new Point3D[] {d,a,e};

	}
	
	protected void setAllPoints() {
		allPoints = new Point3D[faces];
		// //Front Down Edge
		// allPoints[0] = new Point3D(x + dx, y - dy, z - dz);
		// allPoints[1] = new Point3D(x - dx, y - dy, z - dz);
		// //Front Up Edge
		// allPoints[2] = new Point3D(x - dx, y + dy, z - dz);
		// allPoints[3] = new Point3D(x + dx, y + dy, z - dz);
		// //Back Down Edge
		// allPoints[4] = new Point3D(x - dx, y - dy, z + dz);
		// allPoints[5] = new Point3D(x + dx, y - dy, z + dz);
		// //Back Up Edge
		// allPoints[6] = new Point3D(x + dx, y + dy, z + dz);
		// allPoints[7] = new Point3D(x - dx, y + dy, z + dz);

		//Pyramid Time:

		//Front Down
		allPoints[0] = new Point3D(x + dx, y - dy, z - dz);
		allPoints[1] = new Point3D(x - dx, y - dy, z - dz);

		//Back Down
		allPoints[2] = new Point3D(x - dx, y - dy, z + dz);
		allPoints[3] = new Point3D(x + dx, y - dy, z + dz);

		//Tip
		allPoints[4] = new Point3D(x, y + dy, z);

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
		//polygons[5].setName(name + " Top Yel");
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
		//colors[5] = new Color(255,255,0,opacity); //Top, Yellow
	}
}
