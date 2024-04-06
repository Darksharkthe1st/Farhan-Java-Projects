import javax.swing.JFrame;

public class graphicDriver {
	public static void main(String[] args) throws InterruptedException {
		JFrame mainframe = new JFrame();
		System.out.println("HI");
		mainframe.setSize(1000,800);
		mainframe.setVisible(true);
		mainframe.setTitle("Farhan Kittur - CSA Final Project");
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Cube cubey = new Cube(100,0,0,4);
		rectangularPrism rectangley = new rectangularPrism(300,300,150,150,200,50,50);
		mainframe.add(rectangley);
		//mainframe.add(cubey);
		double theta = 1;
		while (true) {
			mainframe.repaint();

			rectangley.setAx((int)(Math.sin(theta) * 100));
			rectangley.setAy((int)(Math.cos(theta) * 200));
			theta = theta + 0.1;
			Thread.sleep(100);
		}
	}
	/*
	int counter = 0;
	
	int ax, ay;
	ax = (int) (width * Math.sin(theta));
	ay = (int) (width * Math.cos(theta));
	//System.out.println(ax + ", " + ay);
	{ //Top Face

		g.setColor(Color.black);
		int[] xpoints = { x, x + length, x + length + ax, x + ax,  x }; //x + length + ax, x + ax,
		int[] ypoints = { y, y, y + ay, y + ay, y };//y + ay, y + ay
		g.setColor(Color.red);
		g.drawLine(x, y, x + length, y);
		g.setColor(Color.green);
		System.out.println((x + length + ax) + ", " + (y + ay));
		g.drawLine(x + length, y, x + length + ax, y + ay);
		int npoints = xpoints.length;
		Polygon p = new Polygon(xpoints, ypoints, npoints);
		//g.drawPolygon(p);
	}
	{ //Bottom Face

		g.setColor(colors[counter++]);
		int[] xpoints = { x, x + length, x + length + ax, x + ax, x };
		int[] ypoints = { y + height, y + height, y + height + ay, y + height + ay, y + height };
		int npoints = xpoints.length;
		Polygon p = new Polygon(xpoints, ypoints, npoints);
		//g.fillPolygon(p);
	}
	{ //Left Face

		g.setColor(colors[counter++]);
		int[] xpoints = { x, x + ax, x + ax, x, x };
		int[] ypoints = {y, y + ay, y + height + ay, y + height, y};
		int npoints = xpoints.length;
		Polygon p = new Polygon(xpoints, ypoints, npoints);
		//g.fillPolygon(p);
	}
	{ //Right Face

		g.setColor(colors[counter++]);
		int[] xpoints = { x + length, x + length + ax, x + length + ax, x + length, x + length };
		int[] ypoints = { y, y + ay, y + height + ay, y + height, y };
		int npoints = xpoints.length;
		Polygon p = new Polygon(xpoints, ypoints, npoints);
		//g.fillPolygon(p);
	}
	*/
}
