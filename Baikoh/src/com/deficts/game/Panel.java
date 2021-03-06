package com.deficts.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.Timer;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Panel extends JPanel implements KeyListener,MouseListener, MouseMotionListener, ActionListener{
	
	private String[] tablero= new String[12];
	private int state;
	
	private Diccionario d = new Diccionario();
	
	private JButton btnStart,
					btnOptions,
					btnExit,
					btnBack,
					btnSubmit,
					btnLeaderboard;
	
	private long startTime,
				 elapsedTime;
	
	private Hec hec;
	
	private boolean isBuilding;
	
	private Node<Letra> pointer;
	
	private String s="";
	
	private TextField tfNombre;
	
	FileWriter leaderboardFile;
	
	private int countdown,
				direction,
				puntaje,
				contadorTablero;
	
	private Point cover;
	
	private Timer timer;
	
	public Panel(){
		super();
		this.setPreferredSize(new Dimension(600,810));
		this.setBackground(Color.darkGray.darker());
		this.setLayout(null);
		
		this.state=0;
		this.countdown=120000;
		this.direction=this.puntaje=this.contadorTablero=0;
		this.timer=new Timer(1000, this);
		
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		crearBotones();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(this.state==0) {
			this.btnStart.setVisible(true);
			this.btnOptions.setVisible(true);
			this.btnExit.setVisible(true);
			this.btnBack.setVisible(false);
			this.btnSubmit.setVisible(false);
			this.tfNombre.setVisible(false);
			this.btnLeaderboard.setVisible(true);
			g.setFont(new Font("Helvetica", Font.BOLD, 150));
			g.setColor(Color.WHITE);
			g.drawString("Baikoh!", 20, 170);
			
		}
		else if(this.state==1) {
			this.btnStart.setVisible(false);
			this.btnOptions.setVisible(false);
			this.btnExit.setVisible(false);
			this.btnBack.setVisible(true);
			this.btnSubmit.setVisible(false);
			this.tfNombre.setVisible(false);
			this.btnLeaderboard.setVisible(false);
			
			
			pintaCuadricula(g);
			llenadoInicial(g);
			pintaTablero(g);
			
			this.requestFocusInWindow();
			g.setColor(Color.red);
			pintaFlecha(g, new Point(cover.x, cover.y));
			g.setColor(Color.GREEN);
			g.drawString("Score: "+puntaje, 400, 70);
			int time=(this.countdown-(int)this.elapsedTime+13)/1000;
			if(time>80){
				g.setColor(Color.cyan);
			}
			if(time<81 && time>30) {
				g.setColor(Color.yellow);
			}
			if(time<31) {
				g.setColor(Color.red);
			}
			g.drawString("Time left: "+time, 170, 70);
			this.repaint();
			
		}
		else if(this.state==2) {
			this.btnStart.setVisible(false);
			this.btnOptions.setVisible(false);
			this.btnExit.setVisible(false);
			this.btnBack.setVisible(true);
			this.btnSubmit.setVisible(false);
			this.tfNombre.setVisible(false);
			this.btnLeaderboard.setVisible(false);
			
			pintaReglas(g);
			
		}
		else if(this.state==3) {
			this.btnStart.setVisible(false);
			this.btnOptions.setVisible(false);
			this.btnExit.setVisible(false);
			this.btnBack.setVisible(true);
			this.btnSubmit.setVisible(true);
			this.tfNombre.setVisible(true);
			this.btnLeaderboard.setVisible(false);
			
			g.setFont(new Font("Helvetica", Font.BOLD, 70));
			g.setColor(Color.white);
			g.drawString("End of", 180, 200); 
			g.drawString("the game!!", 120, 270);
			g.setFont(new Font("Helvetica", Font.BOLD, 30));
			g.drawString("Enter your name:", 10, 500);
			g.setFont(new Font("Helvetica", Font.BOLD, 60));
			g.setColor(Color.GREEN);
			g.drawString("Final Score: "+this.puntaje, 100, 390);
			
		}
		else if(this.state==4) {
			this.btnStart.setVisible(false);
			this.btnOptions.setVisible(false);
			this.btnExit.setVisible(false);
			this.btnBack.setVisible(true);
			this.btnSubmit.setVisible(false);
			this.tfNombre.setVisible(false);
			this.btnLeaderboard.setVisible(false);
			
			pintaLeaderboard(g);
	
			g.setFont(new Font("Helvetica", Font.BOLD, 60));
			g.setColor(Color.orange);
			g.drawString("Top 10", 220, 90);
			
		}
		
	}
	
	private void pintaLeaderboard(Graphics g) {
		int y=220;
		String linea;
		g.setFont(new Font("Helvetica", Font.BOLD, 40));
		g.setColor(Color.white);
		try {
			BufferedReader br=new BufferedReader(new FileReader("Leaderboard.txt"));
			
			for (int i = 0; i < 10; i++) {
				if((linea=br.readLine())!=null) {
					g.drawString(linea, 90, y);
					linea=br.readLine();
					g.drawString(linea, 400, y);
				}
				g.drawString("#"+(i+1), 5, y);
				y+=60;
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
		
		g.drawString("Name", 120, 150);
		g.drawString("Score", 400, 150);
		
	}
	
	private void ordenaLeaderBoard() throws IOException{
		int contador=0;
		int[] puntuaciones;
		String[] nombres;
		try {
			BufferedReader br=new BufferedReader(new FileReader("Leaderboard.txt"));
			while(br.readLine()!=null) {
				contador++;
			}
			System.out.println(contador);
			puntuaciones=new int[contador/2];
			nombres=new String[contador/2];
			
			
			BufferedReader br2=new BufferedReader(new FileReader("Leaderboard.txt"));
			
			for (int i = 0; i < contador/2; i++) {
				nombres[i]=br2.readLine();
				puntuaciones[i]=Integer.parseInt(br2.readLine());
			}
			
			for (int i = 0; i < puntuaciones.length-1; i++) {
				for (int j = 0; j < puntuaciones.length-1-i; j++) {
					if(puntuaciones[j]<puntuaciones[j+1]) {
						int tmp=puntuaciones[j];
						puntuaciones[j]=puntuaciones[j+1];
						puntuaciones[j+1]=tmp;
						
						String tmp2=nombres[j];
						nombres[j]=nombres[j+1];
						nombres[j+1]=tmp2;
					}
				}
			}
			
			PrintWriter pw= new PrintWriter(new FileWriter("Leaderboard.txt"));
			for (int i = 0; i < nombres.length; i++) {
				pw.println(nombres[i]);
				pw.println(puntuaciones[i]);
			}
			
			pw.close();
			br.close();
			br2.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void crearBotones() {
		this.btnStart = new JButton("START");
		this.btnStart.setFont(new Font("Arial",Font.PLAIN,55));
		this.btnStart.setForeground(Color.white);
		this.btnStart.setBounds(100, 240 , 400, 80);
		this.btnStart.setBackground(null);
		this.btnStart.setBorderPainted(false);
		this.add(this.btnStart);
		this.btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state=1;
				hec=new Hec();
				pointer=hec.getEsquina();
				cover=new Point(pointer.getDato().getX(), pointer.getDato().getY());
				timer.start();
				startTime = System.currentTimeMillis();
				repaint();
			}
		});
		
		this.btnOptions= new JButton("OPTIONS");
		this.btnOptions.setFont(new Font("Arial",Font.PLAIN,55));
		this.btnOptions.setForeground(Color.white);
		this.btnOptions.setBounds(100, 320 , 400, 80);
		this.btnOptions.setBackground(null);
		this.btnOptions.setBorderPainted(false);
		this.add(btnOptions);
		this.btnOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					state=2;
					repaint();
			}
		});
		
		this.btnLeaderboard= new JButton("LEADERBOARD");
		this.btnLeaderboard.setFont(new Font("Arial",Font.PLAIN,50));
		this.btnLeaderboard.setForeground(Color.white);
		this.btnLeaderboard.setBounds(60,400 , 490, 80);
		this.btnLeaderboard.setBackground(null);
		this.btnLeaderboard.setBorderPainted(false);
		this.btnLeaderboard.setOpaque(false);
		this.add(this.btnLeaderboard);
		this.btnLeaderboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					state=4;
					repaint();
			}
		});
		
		this.btnExit= new JButton("EXIT");
		this.btnExit.setFont(new Font("Arial",Font.PLAIN,55));
		this.btnExit.setForeground(Color.white);
		this.btnExit.setBounds(100, 480 , 400, 80);
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
					puntaje=0;
					timer.restart();
					timer.stop();
					elapsedTime=0;
					repaint();
			}
		});
		
		this.btnSubmit = new JButton("SUBMIT");
		this.btnSubmit.setFont(new Font("Arial",Font.PLAIN,35));
		this.btnSubmit.setForeground(Color.white);
		this.btnSubmit.setBounds(200, 650 , 190, 100);
		this.btnSubmit.setBackground(null);
		this.btnSubmit.setBorderPainted(false);
		this.add(this.btnSubmit);
		this.btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String input=tfNombre.getText()+"\n"+puntaje+"\n";
					tfNombre.setText(tfNombre.getText());
				    Files.write(Paths.get("Leaderboard.txt"), input.getBytes(), StandardOpenOption.APPEND);
				    
				    tfNombre.setText("");
				    ordenaLeaderBoard();
				}catch (IOException e2) {
				    System.out.println("Error: "+e2);
				}
				
				state=0;
				puntaje=0;
				repaint();
			}
		});
		
		this.tfNombre=new TextField();
		this.tfNombre.setBounds(300, 480, 180, 25);
		this.add(this.tfNombre);
	}
	
	private void pintaReglas(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Helvetica", Font.BOLD, 30));
		g.drawString("Press the letters in order", 50, 120);
		g.drawString("to form words", 50, 160);
		
		g.drawString("Click on the screen", 50, 300);
		g.drawString("to shuffle", 50, 340);
		
		g.drawString("Move with the left and right arrow", 50, 420);
		g.drawString("Move forward with up arrow", 50, 460);
		
		g.drawString("Start building the word by", 50, 520);
		g.drawString("pressing space once", 50, 560);
		g.drawString("Stop building the word by", 50, 620);
		g.drawString("pressing space once", 50, 660);
		
		

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
	
	private void pintaCuadricula(Graphics g) {
		int x=90;
		int y=150;
		g.setColor(Color.white);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				g.drawRect(x, y, 40, 40);
				x+=55;
			}
			y+=55;
			x=90;
		}	
		x=46;
		y=662;
		for (int i = 0; i < 12; i++) {
			g.drawRect(x, y, 40, 40);
			x+=42;
		}
		
	}
	
	private void llenadoInicial(Graphics g) {
		for (int i = 0; i < this.hec.tablero.length; i++) {
			for (int j = 0; j < this.hec.tablero[i].length; j++) {
				this.hec.tablero[i][j].getDato().draw(g);
			}
		}
	}
	
	private void pintaTablero(Graphics g) {
		int x=55;
		int y=695;
		g.setColor(Color.white);
		for (int i = 0; i < 12; i++) {
			if(this.tablero[i]!=null) {
				g.drawString(this.tablero[i], x, y);
			}
			
			x+=42;
		}
	}
	
	private void pintaFlecha(Graphics g, Point p) {
		int[] xpoints=new int[3];
	    int[] ypoints=new int[3];
	    int npoints=3;
		switch(direction) {
			case 0:
				xpoints[0] = p.x+10;
				xpoints[1] = p.x+20;
				xpoints[2] = p.x+30;
			    ypoints[0] = p.y;
			    ypoints[1] = p.y-10;
			    ypoints[2] = p.y;

			    g.fillPolygon(xpoints, ypoints, npoints);
				g.drawLine(p.x+10, p.y, p.x+20, p.y-10); //izquierda
				g.drawLine(p.x+10, p.y, p.x+30, p.y); //abajo
				g.drawLine(p.x+30, p.y, p.x+20, p.y-10); //derecha
				break;
			case 1:
				xpoints[0] = p.x+40;
				xpoints[1] = p.x+40;
				xpoints[2] = p.x+30;
			    ypoints[0] = p.y;
			    ypoints[1] = p.y+10;
			    ypoints[2] = p.y;

			    g.fillPolygon(xpoints, ypoints, npoints);
				g.drawLine(p.x+40, p.y, p.x+40, p.y+10); //derecha
				g.drawLine(p.x+30, p.y, p.x+40, p.y); //arriba
				g.drawLine(p.x+30, p.y, p.x+40, p.y+10); //abajo
				break;
			case 2:
				xpoints[0] = p.x+40;
				xpoints[1] = p.x+40;
				xpoints[2] = p.x+50;
			    ypoints[0] = p.y+10;
			    ypoints[1] = p.y+30;
			    ypoints[2] = p.y+20;

			    g.fillPolygon(xpoints, ypoints, npoints);
				g.drawLine(p.x+40, p.y+10, p.x+40, p.y+30); //izquierda
				g.drawLine(p.x+40, p.y+10, p.x+50, p.y+20); //arriba
				g.drawLine(p.x+50, p.y+20, p.x+40, p.y+30); //abajo
				break;
			case 3:
				xpoints[0] = p.x+40;
				xpoints[1] = p.x+40;
				xpoints[2] = p.x+30;
			    ypoints[0] = p.y+30;
			    ypoints[1] = p.y+40;
			    ypoints[2] = p.y+40;

			    g.fillPolygon(xpoints, ypoints, npoints);
				g.drawLine(p.x+40, p.y+30, p.x+40, p.y+40); //derecha
				g.drawLine(p.x+40, p.y+30, p.x+30, p.y+40); //izquierda
				g.drawLine(p.x+40, p.y+40, p.x+30, p.y+40); //abajo
				break;
			case 4:
				xpoints[0] = p.x+10;
				xpoints[1] = p.x+20;
				xpoints[2] = p.x+30;
			    ypoints[0] = p.y+40;
			    ypoints[1] = p.y+50;
			    ypoints[2] = p.y+40;

			    g.fillPolygon(xpoints, ypoints, npoints);
				g.drawLine(p.x+10, p.y+40, p.x+20, p.y+50); //derecha
				g.drawLine(p.x+10, p.y+40, p.x+30, p.y+40); //arriba
				g.drawLine(p.x+30, p.y+40, p.x+20, p.y+50); //izquierda
				break;
			case 5:
				xpoints[0] = p.x+10;
				xpoints[1] = p.x;
				xpoints[2] = p.x;
			    ypoints[0] = p.y+40;
			    ypoints[1] = p.y+40;
			    ypoints[2] = p.y+30;

			    g.fillPolygon(xpoints, ypoints, npoints);
				g.drawLine(p.x+10, p.y+40, p.x, p.y+40); //abajo
				g.drawLine(p.x, p.y+40, p.x, p.y+30); //izquierda
				g.drawLine(p.x, p.y+30, p.x+10, p.y+40); //arriba
				break;
			case 6:
				xpoints[0] = p.x;
				xpoints[1] = p.x;
				xpoints[2] = p.x-10;
			    ypoints[0] = p.y+10;
			    ypoints[1] = p.y+30;
			    ypoints[2] = p.y+20;

			    g.fillPolygon(xpoints, ypoints, npoints);
				g.drawLine(p.x, p.y+10, p.x, p.y+30); //derecha
				g.drawLine(p.x, p.y+30, p.x-10, p.y+20); //abajo
				g.drawLine(p.x, p.y+10, p.x-10, p.y+20); //arriba
				break;
			case 7:
				xpoints[0] = p.x;
				xpoints[1] = p.x+10;
				xpoints[2] = p.x;
			    ypoints[0] = p.y;
			    ypoints[1] = p.y;
			    ypoints[2] = p.y+10;

			    g.fillPolygon(xpoints, ypoints, npoints);
				g.drawLine(p.x, p.y, p.x+10, p.y); //arriba
				g.drawLine(p.x, p.y, p.x, p.y+10); //izquierda
				g.drawLine(p.x, p.y+10, p.x+10, p.y); //arriba
				break;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		try{
			hec.shuffle();
		}catch(NullPointerException ex) {
			
		}
		
		this.repaint();
	}
	
	public boolean mouseBounds(int mx, int my) {
		if(mx>=90 && mx<=530 && my>=150 && my<=590) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent k) {
		if(this.state == 1) {
			if(k.getKeyCode() == KeyEvent.VK_H) {
				hint(pointer.toString());
			}
			if(k.getKeyCode() == KeyEvent.VK_SPACE) {
				if(isBuilding) {
					this.isBuilding=false;
					if(d.diccionario.containsKey(s.toLowerCase().hashCode())) {
						puntaje+=s.length()*10;
						System.out.println(puntaje);
						System.out.println(true);
					}else {
						System.out.println(false);
					}
					s="";
					this.contadorTablero=0;
					this.tablero=new String[12];
					
				}else {
					this.isBuilding = true;
					s+=pointer.toString();
					this.tablero[contadorTablero++]=pointer.toString();
				}	
			}
			if(k.getKeyCode() == KeyEvent.VK_LEFT) {
				if(direction>0) {
					direction--;
				}else {
					direction=7;
				}
			}
			if(k.getKeyCode() == KeyEvent.VK_RIGHT) {
				
				if(direction<7) {
					direction++;
				}else {
					direction=0;
				}
			}
			if(this.contadorTablero<this.tablero.length) {
				if(k.getKeyCode() == KeyEvent.VK_UP) {
					switch(direction) {
						case 0:
							if(pointer.up!=null) {
								if(isBuilding) {
									s+=pointer.up.toString();
									this.tablero[contadorTablero++]=pointer.up.toString();
									
								}
								pointer=pointer.up;
							}
							break;
						case 1:
							if(pointer.upright!=null) {
								if(isBuilding) {
									s+=pointer.upright.toString();
									this.tablero[contadorTablero++]=pointer.upright.toString();
								}
								pointer=pointer.upright;
							}
							break;
						case 2:
							if(pointer.right!=null) {
								if(isBuilding) {
									s+=pointer.right.toString();
									this.tablero[contadorTablero++]=pointer.right.toString();
								}
								pointer=pointer.right;
							}
							break;
						case 3:
							if(pointer.downright!=null) {
								if(isBuilding) {
									s+=pointer.downright.toString();
									this.tablero[contadorTablero++]=pointer.downright.toString();
								}
								pointer=pointer.downright;
							}
							
							break;
						case 4:
							if(pointer.down!=null) {
								if(isBuilding) {
									s+=pointer.down.toString();
									this.tablero[contadorTablero++]=pointer.down.toString();
								}
								pointer=pointer.down;
							}
							break;
						case 5:
							if(pointer.downleft!=null) {
								if(isBuilding) {
									s+=pointer.downleft.toString();
									this.tablero[contadorTablero++]=pointer.downleft.toString();
								}
								pointer=pointer.downleft;
							}
							break;
						case 6:
							if(pointer.left!=null) {
								if(isBuilding) {
									s+=pointer.left.toString();
									this.tablero[contadorTablero++]=pointer.left.toString();
								}
								pointer=pointer.left;
							}
							break;
						case 7:
							if(pointer.upleft!=null) {
								if(isBuilding) {
									s+=pointer.upleft.toString();
									this.tablero[contadorTablero++]=pointer.upleft.toString();
								}
								pointer=pointer.upleft;
							}
							break;
					}
					cover = new Point(pointer.getDato().getX(), pointer.getDato().getY());
				}
			
			} 
			
			this.repaint();
		}
	}
	
	private void hint(String s) {
		System.out.println(s);
		if(s.length()>6) {
			return;
		}
		
		if(pointer.down!=null) {
			if(d.diccionario.containsKey((s+pointer.down.toString()).hashCode())) {
				pointer = pointer.down;
				cover = new Point(pointer.getDato().getX(), pointer.getDato().getY());
				this.repaint();
				System.out.println(s+pointer.toString());
				hint(s+pointer.toString());
			}
		}
		
		else if(pointer.downleft!=null) {
			if(d.diccionario.containsKey((s+pointer.downleft.toString()).hashCode())) {
				pointer = pointer.downleft;
				cover = new Point(pointer.getDato().getX(), pointer.getDato().getY());
				this.repaint();
				System.out.println(s+pointer.toString());
				hint(s+pointer.toString());
			}
		}
		
		else if(pointer.downright!=null) {
			if(d.diccionario.containsKey((s+pointer.downright.toString()).hashCode())) {
				pointer = pointer.downright;
				cover = new Point(pointer.getDato().getX(), pointer.getDato().getY());
				this.repaint();
				System.out.println(s+pointer.toString());
				hint(s+pointer.toString());
			}
		}
		
		else if(pointer.right!=null) {
			if(d.diccionario.containsKey((s+pointer.right.toString()).hashCode())) {
				pointer = pointer.right;
				cover = new Point(pointer.getDato().getX(), pointer.getDato().getY());
				this.repaint();
				System.out.println(s+pointer.toString());
				hint(s+pointer.toString());
			}
		}
		
		else if(pointer.left!=null) {
			if(d.diccionario.containsKey((s+pointer.left.toString()).hashCode())) {
				pointer = pointer.left;
				cover = new Point(pointer.getDato().getX(), pointer.getDato().getY());
				this.repaint();
				System.out.println(s+pointer.toString());
				hint(s+pointer.toString());
			}
		}
		
		else if(pointer.up!=null) {
			if(d.diccionario.containsKey((s+pointer.up.toString()).hashCode())) {
				pointer = pointer.up;
				cover = new Point(pointer.getDato().getX(), pointer.getDato().getY());
				this.repaint();
				System.out.println(s+pointer.toString());
				hint(s+pointer.toString());
			}
		}
		
		else if(pointer.upright!=null) {
			if(d.diccionario.containsKey((s+pointer.upright.toString()).hashCode())) {
				pointer = pointer.upright;
				cover = new Point(pointer.getDato().getX(), pointer.getDato().getY());
				this.repaint();
				System.out.println(s+pointer.toString());
				hint(s+pointer.toString());
			}
		}
		
		else if(pointer.upleft!=null) {
			if(d.diccionario.containsKey((s+pointer.upleft.toString()).hashCode())) {
				pointer = pointer.upleft;
				cover = new Point(pointer.getDato().getX(), pointer.getDato().getY());
				this.repaint();
				System.out.println(s+pointer.toString());
				hint(s+pointer.toString());
			}
		}
		else {
			
		}
		System.out.println(s+pointer.right.toString());
		pointer = pointer.right;
		hint(s+pointer.toString());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.timer.isRunning()) {
			this.elapsedTime = System.currentTimeMillis()-startTime;
			if(this.elapsedTime>=this.countdown) {
				this.state=3;
				this.repaint();
				this.timer.restart();
				this.timer.stop();
				this.elapsedTime=0;
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}
	
	@Override
	public void keyTyped(KeyEvent k) {
	}
	
}

