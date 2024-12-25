package wassup;
import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FractalDriver {
	static int level = 0;
	static int side = 0;
	static boolean rotate = true;
	static int radius = 200;
	
	
	public static void main(String[] args) throws InterruptedException {
		
		//Setting up the mainframe
		JFrame mainframe = new JFrame();
		mainframe.setTitle("Farhizzy's Fantastic Fractals");
		mainframe.setSize(1000,800);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.setLocationRelativeTo(null);
		
		//Slider stuff
		JSlider levelSlider = new JSlider(SwingConstants.HORIZONTAL,0,15,7);
		
		levelSlider.setMajorTickSpacing(5);
		levelSlider.setMinorTickSpacing(1);
		//mainframe.add(levelSlider,BorderLayout.SOUTH);
		levelSlider.setPaintTicks(true);
		levelSlider.setPaintLabels(true);
		//Our class
		Fractal f = new Fractal(levelSlider.getValue(), 3, mainframe.getWidth() /2 ,mainframe.getHeight() /2 , 300, 0.9, 0.4);
		mainframe.add(f);
		mainframe.setVisible(true);

		mainframe.add(levelSlider, BorderLayout.SOUTH);	
		String userInput;
		int output = -1;
		while (output < 0)
			output = JOptionPane.showOptionDialog(mainframe, "Would you like to use the demo?", "Option Pane", JOptionPane.YES_NO_OPTION, JOptionPane.DEFAULT_OPTION, null, new String[] {"Yes", "No"}, "Yes");
		System.out.println(output);
		
		boolean useDemo = (output == 0);
		
		
		//addChangeListener runs stateChanged(ChangeEvent e) whenever changes are made to the slider.
		levelSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				if(levelSlider.getValue() != level) {
					level = levelSlider.getValue();
					f.setLevel(level);
				}
				mainframe.repaint();
			}
			
		});

		
		//Move the fractal when the window is resized
		mainframe.addComponentListener(new ComponentListener() {

			public void componentHidden(ComponentEvent arg0) {}
			public void componentMoved(ComponentEvent arg0) {}
			public void componentShown(ComponentEvent arg0) {}

			@Override
			public void componentResized(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				f.setX(mainframe.getWidth()/2);
				//Subtract 30 to account for slider height
				f.setY((mainframe.getHeight()-30)/2);
			}
		});
		
		double theta = 0;
		int count = 0;
		if (useDemo) {
			f.setWidthRatio(0.9);
		}

		int x = 0;

		//Demo made to show off the coolest parts of the program
		while (useDemo) {
			x++;
			f.setSides((int)Math.abs(1000 * Math.sin(0.001 * x)) + 1);
			f.setHeightRatio(Math.sin(x * 0.01));
			f.setWidthRatio(Math.sin(x* 0.01));
			if (f.getSides() % 100 == 0)
				System.out.println(f.getSides());
			Thread.sleep(50);
			theta = (theta + 0.1) % (Math.PI * 2);
			f.setAngleOffset(theta);
			//System.out.println(theta);
			mainframe.repaint();
		}
		
		
		JFrame optionFrame = new JFrame();
		optionFrame.setTitle("Control Options");
		optionFrame.setSize(500,500);
		optionFrame.setLocationRelativeTo(null);
		
		JLabel sideLabel = new JLabel("Drag to left to reduce sides, right to increase sides:");
		
		JSlider sideSlider = new JSlider(SwingConstants.HORIZONTAL, -1, 1, 0);
		
		JLabel hRatioLabel = new JLabel("Increase or decrease triangle tip distance to center:");
		
		JSlider hRatioSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 50, 30);
		sideSlider.setMajorTickSpacing(10);
		sideSlider.setMinorTickSpacing(15);
		//mainframe.add(sideSlider,BorderLayout.SOUTH);
		sideSlider.setPaintTicks(true);
		sideSlider.setPaintLabels(true);
		
		JLabel wRatioLabel = new JLabel("Increase or decrease triangle base width:");
		
		JSlider wRatioSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 50, 30);
		sideSlider.setMajorTickSpacing(10);
		sideSlider.setMinorTickSpacing(5);
		//mainframe.add(sideSlider,BorderLayout.SOUTH);
		sideSlider.setPaintTicks(true);
		sideSlider.setPaintLabels(true);
		
		JLabel radiusLabel = new JLabel("Radius (100-400):");
		
		JSlider radiusSlider = new JSlider(SwingConstants.HORIZONTAL, 100, 400, 200);
		radiusSlider.setMajorTickSpacing(50);
		radiusSlider.setMinorTickSpacing(25);
		//mainframe.add(radiusSlider,BorderLayout.SOUTH);
		radiusSlider.setPaintTicks(true);
		radiusSlider.setPaintLabels(true);
		
		JButton rotateButton = new JButton();
		rotateButton.setText("Stop Rotating");
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		
		int compIndex = 0;
		
		p.add(sideLabel, compIndex++);
		p.add(sideSlider, compIndex++);
		p.add(hRatioLabel, compIndex++);
		p.add(hRatioSlider, compIndex++);
		p.add(wRatioLabel, compIndex++);
		p.add(wRatioSlider, compIndex++);
		p.add(radiusLabel, compIndex++);
		p.add(radiusSlider,compIndex++);
		p.add(rotateButton, compIndex++);
		
		
		side = 7;
		
		sideSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				side += sideSlider.getValue();
				side = Math.max(2, side);
				sideSlider.setValue(0);
				f.setSides(side);
				mainframe.repaint();
			}
		});
		
		hRatioSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				f.setHeightRatio(hRatioSlider.getValue() / 10.0);
				System.out.println(hRatioSlider.getValue()); // 20
				mainframe.repaint();
			}
		});
		
		wRatioSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				f.setWidthRatio(wRatioSlider.getValue() / 10.0);
				System.out.println(wRatioSlider.getValue());
				mainframe.repaint();
			}
		});
		
		radiusSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				f.setRadius(radiusSlider.getValue());
				mainframe.repaint();
			}
		});
		
		rotateButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (rotateButton.getText().equals("Rotate")) {
					rotateButton.setText("Stop Rotating");
					rotate = !rotate;
				} else if (rotateButton.getText().equals("Stop Rotating")) {
					rotateButton.setText("Rotate");
					rotate = !rotate;
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		radius = radiusSlider.getValue();
		side = sideSlider.getValue();
		
		optionFrame.add(p);
		optionFrame.setVisible(true);
		
		while (true) {
			mainframe.repaint();
			optionFrame.repaint();
			
			if (rotate) {
				Thread.sleep(50);
				theta = (theta + 0.1) % (Math.PI * 2);
				f.setAngleOffset(theta);
			}
			
		}
	}
}