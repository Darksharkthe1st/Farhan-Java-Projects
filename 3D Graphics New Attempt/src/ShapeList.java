// Self made Graphics loading list so layering can happen :)

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;

//This class takes care of reducing buffering, ordering shapes, and drawing them
class ShapeList3D extends JPanel {
	ArrayList<Shape3D> shapeList = new ArrayList<Shape3D>(); // List of all the Shape3Ds
	int shapeCount = 0;
	int width, height; // Screen width and height
	Color bgColor = new Color(255, 255, 255); // White.

	// Goal: when transformations are made, updateOnChanges becomes true,
	// All points get multiplied by the Matrix "transformation", updateOnChanges
	// becomes false.
	// This way, we don't execute pointless transformations when changes aren't
	// made.
	boolean updateOnChanges = true; // Planned for future.
	Matrix transformation;

	ShapeList3D(int w, int h) {
		width = w;
		height = h;
		transformation = new Matrix(new double[4][4]);
	}

	int add(Shape3D s) {
		if (s == null) {
			return -1;
		}
		shapeList.add(s);
		shapeCount++;
		return (shapeCount - 1);
	}

	// True for fill, false for draw
	void replace(int index, Shape3D s, boolean isFilled) {
		shapeList.set(index, s);
	}

	void setBG(Color c) {
		bgColor = c;
	}

	Shape3D getShape3DByID(int ID) {
		return shapeList.get(ID);
	}

	public void addTransformation(Matrix m) {
		transformation.multiply(m);
	}

	void renderShape3Ds(Graphics g) {
		// We draw to a BufferedImage before pasting on screen so
		// the screen doesn't flash white every time we begin redrawing
		BufferedImage screen = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		// We get the Graphics object of the image so we can draw to it
		Graphics screenGraphics = screen.getGraphics();

		// Polygon border thickness
		((Graphics2D) screenGraphics).setStroke(new BasicStroke(2));

		// We draw to the image
		screenGraphics.setColor(bgColor);
		Rectangle2D bg = new Rectangle2D.Double(0, 0, width, height);
		((Graphics2D) screenGraphics).fill(bg);

		// Loop through and count the faces to paint
		int totalFaces = 0;
		for (Shape3D s : shapeList) {
			totalFaces += s.countVisible();
		}

		// Make our array
		Polygon3D[] polygons = new Polygon3D[totalFaces];

		// Loop through and get all the polygons we need to draw
		int index = 0;
		for (Shape3D s : shapeList) {
			index = s.addTo(polygons, index);
		}

		// Sort by viewing order
		Arrays.sort(polygons);

		// Draw them all
		for (int i = 0; i < index; i++) {
			Polygon3D poly = polygons[i];
			if (poly.getAvgZ() > 0) {
				screenGraphics.setColor(poly.getShadedFill());
				screenGraphics.fillPolygon(poly.getPolygon());

				// If it has an outline, draw it
				if (poly.getOutlineColor() != null) {
					screenGraphics.setColor(poly.getOutlineColor());
					screenGraphics.drawPolygon(poly.getPolygon());
				}
			}

		}
		// We paste the image onto our Graphics window
		g.drawImage(screen, 0, 0, width, height, bgColor, null);

	}

	public void paintComponent(Graphics g) {
		renderShape3Ds(g);
	}
}
