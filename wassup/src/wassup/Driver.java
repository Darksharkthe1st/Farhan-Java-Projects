package wassup;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//creating JFrame
		JFrame frame = new JFrame();
		
		//determining color of fractal
		boolean color = Boolean.parseBoolean(JOptionPane.showInputDialog("Would you like a multicolored fractal? (true for yes, false for no)"));
		frame.setSize(1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//creating JSlider for the depth of the fractal
		JSlider depth = new JSlider(JSlider.VERTICAL, 0, 10, 1);
		depth.setMinorTickSpacing(1);
		depth.setMajorTickSpacing(5);
		depth.setPaintTicks(true);
		depth.setPaintLabels(true);
		depth.setInverted(true);
		frame.add(depth, BorderLayout.EAST);
		
		//setting up Fractal
		frame.setTitle("Ooooo");
		frame.add(new Fractal(75, depth.getValue(), color));
		frame.setVisible(true);
		
		//repaint loop
		while(true) {
			Thread.sleep(2);
			frame.repaint();
		}
	}

}