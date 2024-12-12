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
			//obj = new ObjShape(0,0,4,"Cube.obj");
			obj = new ObjShape(2,2,4,"Cube.obj");
			//obj = new ObjShape(2,-2,5,"C:\\Users\\farha\\Downloads\\Cube.obj");
			//obj = new ObjShape(2,-2,5,"C:\\Users\\farha\\Downloads\\bigSphere.obj");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		obj.rotateAbout('x', 45);
//		obj.rotateAbout('y', 45);
//		
		
		ShapeList3D listy = new ShapeList3D(screenWidth, screenHeight);
		betterCube b = new betterCube(1.616, 1.616, 5, 1, 1, 1);
		b.setName("Mouse Cube");
		b.rotateAbout('x', 45);
		betterCube b2 = new betterCube(0, 0, 6, 1, 1, 1);
		b2.setName("Screen Cube");
		mainframe.add(listy);
		listy.add(obj);
		listy.setBackground(Color.red);
		b.setOutlineColor(Color.black);
		b2.setOutlineColor(Color.darkGray);
		mainframe.setVisible(true);
		boolean increase = true;
		
		listy.add(b);
		listy.add(b2);
		
		
		double x = 0;
		PointerInfo a = MouseInfo.getPointerInfo();
		b2.rotateAbout('z', 45);
		int count = 0;
		
		while (true) {
			Thread.sleep(100);
			mainframe.repaint();
			if (increase)
				x+= 0.1;
			else
				x-= 0.1;
			if (Math.abs(x) > 3)
				increase = !increase;
			obj.rotateAbout('z', 10);
			b.rotateAbout('x', 3);
			b.setX(x);
			b2.setY(-x);
			mainframe.repaint();
			
		}
	}

	public static Color randColor() {
		return (new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), 100));
	}
}
