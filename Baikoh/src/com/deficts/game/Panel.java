package com.deficts.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Panel extends JPanel{
	
	private Letra[][] tablero= new Letra[8][8]; // Letra[fila][columna]
	private int state;
	
	private JButton btnStart,
					btnOptions,
					btnExit,
					btnBack;
	
	private Hec hec;
	
	public Panel() {
		super();
		this.setPreferredSize(new Dimension(600,810));
		this.setBackground(Color.darkGray.darker());
		this.setLayout(null);
		this.state=1;
		this.hec=new Hec();
		
		this.btnStart = new JButton("START");
		this.btnStart.setFont(new Font("Arial",Font.PLAIN,55));
		this.btnStart.setForeground(Color.white);
		this.btnStart.setBounds(100, 300 , 400, 100);
		this.btnStart.setBackground(null);
		this.btnStart.setBorderPainted(false);
		this.add(this.btnStart);
		this.btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state=1;
				repaint();
			}
		});
		
		this.btnOptions= new JButton("OPTIONS");
		this.btnOptions.setFont(new Font("Arial",Font.PLAIN,55));
		this.btnOptions.setForeground(Color.white);
		this.btnOptions.setBounds(100, 400 , 400, 100);
		this.btnOptions.setBackground(null);
		this.btnOptions.setBorderPainted(false);
		this.add(btnOptions);
		this.btnOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					state=2;
					repaint();
			}
		});
		
		this.btnExit= new JButton("EXIT");
		this.btnExit.setFont(new Font("Arial",Font.PLAIN,55));
		this.btnExit.setForeground(Color.white);
		this.btnExit.setBounds(100, 500 , 400, 100);
		this.btnExit.setBackground(null);
		this.btnExit.setBorderPainted(false);
		this.btnExit.setOpaque(false);
		this.add(this.btnExit);
		this.btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					System.exit(0);
			}
		});
		
		this.btnBack = new JButton("BACK");
		this.btnBack.setFont(new Font("Arial",Font.PLAIN,35));
		this.btnBack.setForeground(Color.white);
		this.btnBack.setBounds(10, 10 , 150, 100);
		this.btnBack.setBackground(null);
		this.btnBack.setBorderPainted(false);
		this.add(this.btnBack);
		this.btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					state=0;
					repaint();
			}
		});
		
	}
	

	
	public void Start() {
		
	}
	
	public void Update() {
		
	}
	
	private void llenadoInicial(Graphics g) {
		for (int i = 5; i < this.hec.tablero.length; i++) {
			for (int j = 0; j < this.hec.tablero[i].length; j++) {
				this.hec.pinta(i,j,g);
			}
		}
	}
	
	private void pintaCuadricula(Graphics g) {
		int x=130;
		int y=200;
		g.setColor(Color.white);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				g.drawRect(x, y, 40, 40);
				x+=42;
			}
			y+=42;
			x=130;
		}
	}
	
	private void pintaReglas(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Helvetica", Font.BOLD, 30));
		g.drawString("Press the letters in order", 50, 120);
		g.drawString("to form words", 50, 160);
		

		g.drawString("-->",95, 227);
		g.drawString("-->",179, 227);
		g.drawString("-->",263, 227);
		g.drawString("=",347, 227);
		g.drawString("BEAR",365, 230);
		
		Letra b=new Letra("B", 50, 200, Color.blue);
		Letra e=new Letra("E", 135, 200, Color.yellow);
		Letra a=new Letra("A", 220, 200, Color.magenta);
		Letra r=new Letra("R", 305, 200, Color.orange);
		b.draw(g);
		e.draw(g);
		a.draw(g);
		r.draw(g);
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(this.state==0) {
			this.btnStart.setVisible(true);
			this.btnOptions.setVisible(true);
			this.btnExit.setVisible(true);
			this.btnBack.setVisible(false);
			
		}
		else if(this.state==1) {
			this.btnStart.setVisible(false);
			this.btnOptions.setVisible(false);
			this.btnExit.setVisible(false);
			this.btnBack.setVisible(true);
			
			pintaCuadricula(g);
			llenadoInicial(g);
			
			
		}
		else if(this.state==2) {
			this.btnStart.setVisible(false);
			this.btnOptions.setVisible(false);
			this.btnExit.setVisible(false);
			this.btnBack.setVisible(true);
			pintaReglas(g);
			
		}
	}
	
}

