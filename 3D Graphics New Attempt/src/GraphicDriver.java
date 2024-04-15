import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.io.FileNotFoundException;

import javax.swing.JFrame;

//Runs the whole project
public class GraphicDriver {
	static int screenWidth = 1000;
	static int screenHeight = 800;
	static double fov = 90;
	static double zFar = 100;
	static double zNear = 4;

	public static void main(String[] args) throws InterruptedException {
		JFrame mainframe = new JFrame();
		mainframe.setSize(screenWidth, screenHeight);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		Cube c = new Cube(0, 0, 3, 1, 1, 1);
//		mainframe.add(c);
		ObjShape obj = null;
		try {
			obj = new ObjShape(0,0,10,"C:\\Users\\farha\\Downloads\\videoShip.obj");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ShapeList3D listy = new ShapeList3D(screenWidth, screenHeight);
		betterCube b = new betterCube(0, 5, 20, 1, 1, 1);
		b.setName("Mouse Cube");
		b.rotateAbout('x', 45);
		betterCube b2 = new betterCube(0, 5, 14, 1, 1, 1);
		b2.setName("Screen Cube");
		mainframe.add(listy);
		//mainframe.add(b);
		listy.add(b);
		listy.add(obj);
		listy.setBackground(Color.red);
		b.setOutlineColor(Color.black);
		b2.setOutlineColor(Color.darkGray);
		listy.add(b2);
		mainframe.setVisible(true);
		//System.out.println(Point3D.projectionMatrix());
		boolean increase = true;
		double x = 0;
		
		PointerInfo a = MouseInfo.getPointerInfo();
		b2.rotateAbout('z', 45);
		int count = 0;
		while (true) {
			Thread.sleep(10);
			mainframe.repaint();
			if (increase)
				x+= 0.1;
			else
				x-= 0.1;
			if (Math.abs(x) > 10)
				increase = !increase;
			
			double newX = (MouseInfo.getPointerInfo().getLocation().x - mainframe.getLocation().x - mainframe.getWidth() /2);
			double newY = (MouseInfo.getPointerInfo().getLocation().y - mainframe.getLocation().y - mainframe.getHeight() /2);

			b.setX(newX);
			b.setY(newY);
			System.out.println(b.getXPos() + ", " + b.getYPos());

			//b.rotateAbout('x', 5);
			//b2.setX(x);
			b2.rotateAbout('x', 2);
			System.out.println(obj.getXPos() + ", " + obj.getYPos() + ", " + obj.getZ());
			//obj.rotateAbout('x', 4);
			obj.setX(x/2);
			obj.setY(x/2);
		}
//		
//		Matrix m;
//		double[][] arry = new double[4][4];
//		arry[0] = new double[] {2, 5, 4, 0};
//		arry[1] = new double[] {1, 3, 2, 0};
//		arry[2] = new double[] {7, 4, 6, 0};
//		arry[3] = new double[] {0, 0, 0, 1};
//		
//		m = new Matrix(arry);
//		
//		double[][] awry = new double[3][1];
//		awry[0] = new double[] {4};
//		awry[1] = new double[] {3};
//		awry[2] = new double[] {5};
//		
//		Point3d n = new Point3d(4, 3, 5);
//		System.out.println(m);
//		System.out.println(n);
//		System.out.println(Matrix.multiply(m,n));
	}

	public static Color randColor() {
		return (new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), 100));
	}
}
