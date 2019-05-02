package com.deficts.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Prueba extends JPanel implements MouseMotionListener{
	
	HashMap<Rectangle, String> mapa;
	
	public void llenar() {
		mapa.put(new Rectangle(10, 10, 40, 40), "A");
		mapa.put(new Rectangle(60, 10, 40, 40), "B");
		mapa.put(new Rectangle(10, 60, 40, 40), "C");
		mapa.put(new Rectangle(60, 60, 40, 40), "D");
	}
	
	public Prueba() {
		super();
		setPreferredSize(new Dimension(500,500));
	}
	
	public void paintComponent(Graphics g) {
		g.drawRect(10, 10, 40, 40);
		g.drawRect(60, 10, 40, 40);
		g.drawRect(10, 60, 40, 40);
		g.drawRect(60, 60, 40, 40);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Hola");
		frame.add(new Prueba());
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
