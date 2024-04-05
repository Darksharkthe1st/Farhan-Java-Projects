import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.awt.geom.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.*;

public class angryBirds {

	
	/* TO DO:
	 * Reduce Buffering/Flashing by finding a way to paste all shapes at once
	 * 
	 * 
	 * 
	 * */
	
	
	
	public static void main(String[] args) {
		
		//We make an instance of our class
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		balloonPunching(f);
		testingBufferedImages(f);
		graphicsTesting(f);
		playPong(f);
	}
	
	public static void balloonPunching(JFrame f) {
		int width = 1000; int height = 800;
		f.setSize(width, height);
		f.setTitle("PUNCHING BLOONS!");
		f.setVisible(true);
		shapeList renderQueue = new shapeList(width, height);
		//Our balloon is in place.
		renderQueue.fill(new Ellipse2D.Double(100,100,100,200), new Color(255,0,0));
		renderQueue.renderShapes(f.getGraphics());
		renderQueue.renderShapes(f.getGraphics());
//		MouseListener m = new MouseListener();
//		MouseEvent e;
//		m.mouseClicked(null);
		
	}
	
	
	public static void testingBufferedImages(JFrame f) {
		int maxSize = 900;
		f.setSize(maxSize, maxSize);
		f.setTitle("DRAWING IN JAVA");
		f.setVisible(true);
		
		
		BufferedImage b = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
		Graphics g = b.getGraphics();
		Graphics2D g2d = (Graphics2D)g;
		final String imagePath = "\\C:\\Users\\farha\\Downloads\\Super Mario Sprite.png\\";
		URL imgURL = angryBirds.class.getResource(imagePath);
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Graphics realG = f.getGraphics();
		Graphics2D realG2D = (Graphics2D)realG;
		//realG.drawLine(0, 0, 100, 100);
		realG2D.drawImage(img, null, 0, 0);
		for (int i = 0; i < 100; i++) {
			realG2D.drawImage(img, null, i, i);
		}
	}
	
	//Simple program that shrinks and grows a square. Used for testing graphics features.
	public static void graphicsTesting(JFrame f) {
		int maxSize = 900;
		
		System.out.print("HI");
		f.setSize(maxSize, maxSize);
		f.setTitle("DRAWING IN JAVA");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		Graphics g = f.getGraphics();
		
		//playAngryBirds(g, maxSize, maxSize);
		
		
		
		Graphics2D g2d = (Graphics2D)g; shapeList renderQueue = new shapeList(maxSize, maxSize);
		 
		
		Rectangle2D r = new Rectangle2D.Double(maxSize/4, maxSize/4, maxSize/2, maxSize/2);
		int myRect = renderQueue.fill(r, new Color(0,0,0));
		int s = maxSize/2;
		boolean upOrDown = false;
		for (int q  = 0; q < 1000; q++) {
			renderQueue.replace(myRect, new Rectangle2D.Double(maxSize/2 - s/2,maxSize/2 - s/2, s,s), true);
			if (upOrDown) {
				s++;
			}
			else {
				s--;
			}
			if (s > maxSize/2) {
				upOrDown = false;
			}
			if (s < maxSize/16) {
				upOrDown = true;
			}
			
			renderQueue.renderShapes(g);
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void playPong(JFrame f) {
		int width = 1000, height = 800;
		f.setSize(width, height);
		f.setVisible(true);
		Graphics g = f.getGraphics();
		shapeList renderQueue = new shapeList(width, height);
		renderQueue.setBG(new Color(200,200,255));
		Random randy = new Random();
		int pongDX = randy.nextInt(31)-15, pongDY = randy.nextInt(21)-10;
		while (Math.abs(pongDX) < 3) {
			pongDX = randy.nextInt(31)-15;
		}
		int pongX = width/2-25, pongY = height/2-25;
		Ellipse2D myEllipse = new Ellipse2D.Double(width/2-25, height/2-25, 50, 50);
		int pongBall = renderQueue.fill(myEllipse, new Color(0,255,0));
		
		int playerY = height/2, compY = height/2;
		
		renderQueue.fill(new Rectangle2D.Double(0,playerY-25,20,50), new Color(255,255,255));
		int playerWins = 0, compWins = 0;
		while (true) {
			pongX += pongDX; pongY += pongDY;
			if (pongY < 25 || pongY > height - 25) {
				pongDY = (pongDY * -1);
			}
			if (pongX < 0) {
				playerWins++;
				pongDX = randy.nextInt(31)-15; pongDY = randy.nextInt(21)-10;
				pongX = width/2-25; pongY = height/2-25;
			} else if (pongX > width - 50) {
				compWins++;
				pongDX = randy.nextInt(31)-15; pongDY = randy.nextInt(21)-10;
				pongX = width/2-25; pongY = height/2-25;
			}
			
			
			renderQueue.replace(pongBall, new Ellipse2D.Double(pongX, pongY, 50, 50), true);
			renderQueue.renderShapes(g);
		}
		/*
		 * try { Image red = ImageIO.read(new
		 * File("C:\\Users\\farha\\OneDrive\\Documents\\Red.png")); } catch (IOException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
		
		
		
	}
	
	// Self made Graphics loading list so layering can happen :)
		static class shapeList {
			public class GraphicObject {
				public Shape shape;
				public Color color;
				public boolean fillOrDraw;
				public int renderOrder;
				public GraphicObject() {}
				public GraphicObject(Shape shape, Color color, boolean fillOrDraw, int renderOrder) {
					this.shape = shape; this.color = color;
					this.fillOrDraw = fillOrDraw; this.renderOrder = renderOrder;
				}
			}
			
			Graphics g;
			
			ArrayList<GraphicObject> objList = new ArrayList<GraphicObject>();
			int shapeCount = 0;
			int width, height;
			Color bgColor = new Color(255, 255, 255);
			boolean updateOnChanges = true;
			
			shapeList(int w, int h) {
				if (w < 0 || h < 0) {
					return;
				}
				width = w;
				height = h;
				// A dummy shape to start off the program so the arrays aren't all null
				objList.add(new GraphicObject(
						new Rectangle2D.Double(0, 0, 1, 1),
						new Color(255, 255, 255),
						true,
						0
						));
				shapeCount++;
			}
			
			int draw(Shape s, Color c) {
				if (s == null || c == null) {
					return -1;
				}
				objList.add(new GraphicObject(
						s,
						c,
						false,
						shapeCount++));
				return (shapeCount - 1);
			}

			int fill(Shape s, Color c) {
				if (s == null || c == null) {
					return -1;
				}
				objList.add(new GraphicObject(
						s,
						c,
						true,
						shapeCount++));
				return (shapeCount - 1);
			}

			// True for fill, false for draw
			void replace(int index, Shape s, boolean isFilled) {
				
				objList.get(index).shape = s;
				objList.get(index).fillOrDraw = isFilled;
			}

			void setBG(Color c) {
				bgColor = c;
			}

			Shape getShapeByID(int ID) {
				return objList.get(ID).shape;
			}

			boolean getFillOrDrawByID(int ID) {
				return objList.get(ID).fillOrDraw;
			}

			void renderShapes(Graphics g) {
				// The screen business is to avoid buffering.
				// (The name bufferedImage doesn't have anything to do with buffering, it's just
				// an image for our use cases)
				BufferedImage screen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				// We get the Graphics object of the image so we can draw to it
				Graphics2D tg2d = (Graphics2D) screen.getGraphics();

				// We draw to the image
				tg2d.setColor(bgColor);
				Rectangle2D bg = new Rectangle2D.Double(0, 0, width, height);
				tg2d.fill(bg);
				for (int i = 0; i < shapeCount; i++) {
					GraphicObject graphObj = objList.get(i);
					tg2d.setColor(graphObj.color);
					if (graphObj.fillOrDraw) {
						tg2d.fill(graphObj.shape);
					} else {
						tg2d.draw(graphObj.shape);
					}
				}

				// We paste the image onto our Graphics window
				g.drawImage(screen, 0, 0, width, height, bgColor, null);

			}

			void replaceShape(int id, int toIndex) {
				GraphicObject focusObject = objList.get(id);
				int prevOrder = focusObject.renderOrder;
				if (toIndex < prevOrder) {
					for (int i = 0; i < shapeCount; i++) {
						GraphicObject graphObj = objList.get(i);
						if (graphObj.renderOrder >= toIndex && graphObj.renderOrder <= prevOrder) {
							graphObj.renderOrder++;
						}
					}
				} else if (prevOrder < toIndex) {
					for (int i = 0; i < shapeCount; i++) {
						GraphicObject graphObj = objList.get(i);
						if (graphObj.renderOrder <= toIndex && graphObj.renderOrder >= prevOrder) {
							graphObj.renderOrder--;
						}
					}
				}
				focusObject.renderOrder = toIndex;
			}

		}
	
	
	//Self made Graphics loading list so layering can happen :)
	/*static class shapeList {
		Graphics g;
		Graphics2D g2d;
		Shape[] shapeList;
		Color[] colorList;
		boolean[] fillOrDraw;
		int shapeCount = 0;
		int[] renderOrder;
		int width, height;
		Color bgColor = new Color(255,255,255);
		boolean updateOnChanges = true;
		
		
		
		shapeList(Graphics gWindow, int w, int h) {
			if (gWindow == null) {
				return;
			}
			if (w < 0 || h < 0) {
				return;
			}
			width = w;
			height = h;
			g = gWindow;
			g2d = (Graphics2D)g;
			//A dummy shape to start off the program so the arrays aren't all null
			shapeList = append(shapeList, new Rectangle2D.Double(0,0,1,1));
			colorList = append(colorList, new Color(255,255,255));
			fillOrDraw = append(fillOrDraw, true);
			renderOrder = append(renderOrder, 0);
			shapeCount++;
		}
		int draw(Shape s, Color c) {
			if (s == null || c == null) {
				return -1;
			}
			shapeList = append(shapeList, s);
			colorList = append(colorList, c);
			fillOrDraw = append(fillOrDraw, false);
			renderOrder = append(renderOrder, shapeCount);
			shapeCount++;
			return (shapeCount - 1);
		}
		int fill(Shape s, Color c) {
			if (s == null || c == null) {
				return -1;
			}
			shapeList = append(shapeList, s);
			colorList = append(colorList, c);
			fillOrDraw = append(fillOrDraw, true);
			renderOrder = append(renderOrder, shapeCount);
			shapeCount++;
			return (shapeCount - 1);
		}
		//True for fill, false for draw
		void replace(int index, Shape s, boolean isFilled) {
			
			
			shapeList[index] = s;
			fillOrDraw[index] = isFilled;
		}
		
		void setBG(Color c) {
			bgColor = c;
		}
		
		Shape getShapeByID(int ID) {
			return shapeList[ID];
		}
		boolean getFillOrDrawByID(int ID) {
			return fillOrDraw[ID];
		}
		void renderShapes() {
			//The screen business is to avoid buffering.
			//(The name bufferedImage doesn't have anything to do with buffering, it's just an image for our use cases)
			BufferedImage screen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			//We get the Graphics object of the image so we can draw to it
			Graphics2D tg2d = (Graphics2D)screen.getGraphics();
			
			//We draw to the image
			tg2d.setColor(bgColor);
			Rectangle2D bg = new Rectangle2D.Double(0,0,width,height);
			tg2d.fill(bg);
			for (int i = 0; i < shapeCount; i++) {
				tg2d.setColor(colorList[renderOrder[i]]);
				if (fillOrDraw[i]) {
					tg2d.fill(shapeList[renderOrder[i]]);
				} else {
					tg2d.draw(shapeList[renderOrder[i]]);
				}
			}
			
			
			//We paste the image onto our Graphics window
			g.drawImage(screen, 0, 0, width, height, bgColor, null);
			
		}
		Shape getShape(int id) {
			return shapeList[id];
		}
		void replaceShape(int id, int toIndex) {
			int prevOrder = renderOrder[id];
			if (toIndex < prevOrder) {
				for (int i = 0; i < renderOrder.length; i++) {
					if (renderOrder[i] >= toIndex && renderOrder[i] <= prevOrder) {
						renderOrder[i]++;
					}
				}
			Shape myShape; //Type = 1
		} else if (prevOrder < toIndex) {
				for (int i = 0; i < renderOrder.length; i++) {
					if (renderOrder[i] <= toIndex && renderOrder[i] >= prevOrder) {
						renderOrder[i]--;
					}
				}
			}
			renderOrder[id] = toIndex;
		}
		
	} */
	
	//A class containing any type of graphical object, whether an image or a shape.
	/*static class GraphicObject {
		enum objType {
			SHAPEObj,
			IMAGEObj
		}
		objType thisType;
		
		Image thisImage;
		Shape thisShape;
		
		
		GraphicObject(objType myType) {
			thisType = myType;
		}
		
		void setType(objType myType) {thisType = myType;}
		void setImage(Image img) { thisImage = img;}
		void setShape(Shape shape) {thisShape = shape;}
		
		
		Image myImage; //Type = 2
		void fill(int type) {
			if (thisType == objType.SHAPEObj) {
				
			}
		}
	}
	
	static Shape[] append(Shape[] arr, Shape value) {
		if (arr == null) {
			arr = new Shape[1];
			arr[0] = value;
			return arr;
		}
		
		Shape[] arry = arr;
		arr = new Shape[arr.length + 1];
		for (int i = 0; i < arry.length; i++) {
			arr[i] = arry[i];
		}
		arr[arry.length] = value; 
		return arr;
	}
	static int[] append(int[] arr, int value) {
		if (arr == null) {
			arr = new int[1];
			arr[0] = value;
			return arr;
		}
		
		int[] arry = arr;
		arr = new int[arr.length + 1];
		for (int i = 0; i < arry.length; i++) {
			arr[i] = arry[i];
		}
		arr[arry.length] = value; 
		return arr;
	}
	
	static boolean[] append(boolean[] arr, boolean value) {
		if (arr == null) {
			arr = new boolean[1];
			arr[0] = value;
			return arr;
		}
		
		boolean[] arry = arr;
		arr = new boolean[arr.length + 1];
		for (int i = 0; i < arry.length; i++) {
			arr[i] = arry[i];
		}
		arr[arry.length] = value; 
		return arr;
	}
	
	static Color[] append(Color[] arr, Color value) {
		if (arr == null) {
			arr = new Color[1];
			arr[0] = value;
			return arr;
		}
		
		Color[] arry = arr;
		arr = new Color[arr.length + 1];
		for (int i = 0; i < arry.length; i++) {
			arr[i] = arry[i];
		}
		arr[arry.length] = value; 
		return arr;
	}*/
}
