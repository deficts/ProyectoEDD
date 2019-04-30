package com.deficts.game;

import javax.swing.JFrame;

public class Frame extends JFrame{
	private Panel panel;
	
	public Frame() {
		super("Baikoh!");
		this.panel=new Panel();
		this.add(panel);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new Frame();
		
	}
}
