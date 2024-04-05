import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Thread;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.*;
public class Trying_Out_Graphics_In_Java {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		

	        JFrame f = new JFrame();
	        
			
			int maxSize = 1000;
			//We make an instance of our class
			
			f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
			System.out.print("HI");
			f.setSize(600, 300);
			f.setTitle("DRAWING IN JAVA");
			f.setVisible(true);
			Graphics g = f.getGraphics();
			int i = maxSize;
			int gain = -1;
			Graphics2D g2d = (Graphics2D)g;
			while (true)
			for (int le = 1; le <= 50; le++) {
				String s = String.valueOf(le);
				if (s.length() == 1) s = "0" + s;
				if (s.length() == 2) s = "0" + s;
				Path p = Paths.get("C:\\Users\\farha\\Downloads\\ezgif-2-4031bd32c7-gif-jpg\\frame_" + s + "_delay-0.2s.jpg");
		        BufferedImage newBi = ImageIO.read(new ByteArrayInputStream(Files.readAllBytes(p)));
		        g2d.drawImage(newBi, null, 0, 0);
		        f.setVisible(true);
		        Thread.sleep(250);
			}
			
			//Rectangle2D r;
			//FULL RED -> FULL RED FULL GREEN
			//FULL RED FULL GREEN -> FULL GREEN
			//FULL GREEN -> FULL GREEN FULL BLUE
			//FULL GREEN FULL BLUE -> FULL BLUE
			//FULL BLUE -> FULL BLUE FULL RED
			//FULL BLUE FULL RED -> FULL RED
			
			//100 -> 110 -> 010 -> 011 -> 001 -> 101 -> 100
			//
			/*
			g2d.setColor(new Color(255, 0, 0));
			r = new Rectangle2D.Double(0,0,600,600);
			g2d.fill(r);
			
			int[] growth = {0, 1, 0}; int[] rgb = {255, 0, 0}; 
			
			
			*/
			/*
			 * g2d.setColor(new Color(rgb[0], rgb[1], rgb[2])); System.out.println(rgb[0] +
			 * ", " + rgb[1] + ", " + rgb[2]); r = new Rectangle2D.Double(0,0,i,i);
			 * g2d.fill(r);
			 */
			
			//System.out.println("NOPE!");
			/*
			
			while (true) {
				//System.out.println(i);
				i = i + gain;
				f.setSize(i, i);
				if (i < 10) {
					gain = 1;
				} else if (i > maxSize) {
					gain = -1;
				}
				for (int a = 0; a < 3; a++) {
					rgb[a] += growth[a];
					if (rgb[a] == 255 && growth[a] == 1) {
						growth[a] = 0;
						if (a == 0) {
							growth[2] = -1;
						} else {
							growth[a - 1] = -1;
						}
						break;
					} else if (rgb[a] == 0 && growth[a] == -1) {
						growth[a] = 0;
						growth[(a + 2) % 3] = 1;
					}
				}
				g2d.setColor(new Color(rgb[0], rgb[1], rgb[2]));
				System.out.println(rgb[0] + ", " + rgb[1] + ", " + rgb[2]);
				r = new Rectangle2D.Double(i/4,i/4,i/2,i/2);
				g2d.fill(r);
				
				/*
				 * try { Thread.sleep(1); } catch (InterruptedException e) { // TODO
				 * Auto-generated catch block System.out.print("FAILED :("); }
				 */
			
}
}