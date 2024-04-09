import java.awt.Color;

import javax.swing.JFrame;

public class GraphicDriver {
	static int screenWidth = 1000;
	static int screenHeight = 800;
	static double fov = 90;
	static double vFar = 100;
	static double vNear = 0.1;
	public static void main(String[] args) throws InterruptedException {
		JFrame mainframe = new JFrame();
		mainframe.setSize(1000,800);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Cube c = new Cube(6,5,10,1,1,1);
		mainframe.add(c);
		mainframe.setVisible(true);
		System.out.println(Point3d.projectionMatrix(1000.0,800.0,90,1000,0.1));
		boolean fovUp = true;
				while (true) {
			mainframe.repaint();
			Thread.sleep(10);
			if (fovUp) 
				fov+= 0.1;
			else
				fov -= 0.1;
			if (fov > 120 || fov < 30)
				fovUp = !fovUp;
				
			
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
		return (new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255), 100));
	}
}
