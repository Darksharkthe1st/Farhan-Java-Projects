// Self made Graphics loading list so layering can happen :)

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

class ShapeList {
		public class GraphicObject {
			public Shape shape;
			public Color color;
			public boolean fillOrDraw;
			public int renderOrder;

			public GraphicObject() {
			}

			public GraphicObject(Shape shape, Color color, boolean fillOrDraw, int renderOrder) {
				this.shape = shape;
				this.color = color;
				this.fillOrDraw = fillOrDraw;
				this.renderOrder = renderOrder;
			}
		}

		Graphics g;

		ArrayList<GraphicObject> objList = new ArrayList<GraphicObject>();
		int shapeCount = 0;
		int width, height;
		Color bgColor = new Color(255, 255, 255);
		boolean updateOnChanges = true;

		ShapeList(int w, int h) {
			if (w < 0 || h < 0) {
				return;
			}
			width = w;
			height = h;
			// A dummy shape to start off the program so the arrays aren't all null
			objList.add(new GraphicObject(new Rectangle2D.Double(0, 0, 1, 1), new Color(255, 255, 255), true, 0));
			shapeCount++;
		}

		int draw(Shape s, Color c) {
			if (s == null || c == null) {
				return -1;
			}
			objList.add(new GraphicObject(s, c, false, shapeCount++));
			return (shapeCount - 1);
		}

		int fill(Shape s, Color c) {
			if (s == null || c == null) {
				return -1;
			}
			objList.add(new GraphicObject(s, c, true, shapeCount++));
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
