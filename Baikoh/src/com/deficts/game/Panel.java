package com.deficts.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.ShapeGraphicAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.io.IOException;
import java.rmi.dgc.DGC;
import java.security.Key;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Panel extends JPanel implements KeyListener,Runnable, MouseListener, MouseMotionListener{
	
	private String[] tablero= new String[12]; // Letra[fila][columna]
	private Letra[] tablero2=new Letra[12];
	private int state;
	
	private Diccionario d = new Diccionario();
	
	private JButton btnStart,
					btnOptions,
					btnExit,
					btnBack;
	
	private Hec hec;
	
	private boolean start, isBuilding, bandera=true;
	
	private Random ran=new Random();
	
	private int c0,c1,c2,c3,c4,c5,c6,c7;
	
	private Thread hilo;
	
	private Node<Letra> pointer;
	
	private String s="";
	
	private Point cover;
	
	private int direction= 0;
	
	private int puntaje=0;
	
	private int contadorTablero=0;
	
	public Panel() {
		super();
		this.setPreferredSize(new Dimension(600,810));
		this.setBackground(Color.darkGray.darker());
		this.setLayout(null);
		this.state=2;
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.start=false;
		this.hec=new Hec();
		this.hilo=new Thread(this);
		this.c0=this.c1=this.c2=this.c3=this.c4=this.c5=this.c6=this.c7=4;
		crearBotones();
		pointer=hec.getEsquina();
		cover = new Point(pointer.getDato().getX(), pointer.getDato().getY());
	}
	
	public void run() {
		try {
			if(this.start) {
				while(true) {
					int num=ran.nextInt(8);
					System.out.println(num);
					this.repaint();
						
					if(num==0) {			
						if(this.c0>=0) {
							this.hec.tablero[c0--][0].getDato().draw(this.getGraphics());
						}
						else {
							this.state=3;
							break;
						}
					}
					else if(num==1) {
						if(this.c1>=0) {
							this.hec.tablero[c1--][1].getDato().draw(this.getGraphics());
						}
						else {
							this.state=3;
							break;
						}
					}
					else if(num==2) {
						if(this.c2>=0) {
							this.hec.tablero[c2--][2].getDato().draw(this.getGraphics());
						}
						else {
							this.state=3;
							break;
						}
					}
					else if(num==3) {
						if(this.c3>=0) {
							this.hec.tablero[c3--][3].getDato().draw(this.getGraphics());
						}
						else {
							this.state=3;
							break;
						}
					}
					else if(num==4) {
						if(this.c4>=0) {
							this.hec.tablero[c4--][4].getDato().draw(this.getGraphics());
						}
						else {
							this.state=3;
							break;
						}
					}
					else if(num==5) {
						if(c5>=0) {
							this.hec.tablero[c5--][5].getDato().draw(this.getGraphics());
						}
						else {
							this.state=3;
							break;
						}
					}
					else if(num==6) {
						if(this.c6>=0) {
							this.hec.tablero[c6--][6].getDato().draw(this.getGraphics());
						}
						else {
							this.state=3;
							break;
						}
					}
					else if(num==7) {
						if(this.c7>=0) {
							this.hec.tablero[c7--][7].getDato().draw(this.getGraphics());
						}
						else {
							this.state=3;
							break;
						}
					}
					Thread.sleep(3000);
				}
			}
			
			
		} catch (InterruptedException e) {
			System.out.println("Error en sleep "+e);
		}
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
			this.requestFocusInWindow();
			g.setColor(Color.red);
			pintaFlecha(g, new Point(cover.x, cover.y));
			g.drawString("Score: "+puntaje, 400, 70);
			this.pintaTablero(g);
		}
		else if(this.state==2) {
			this.btnStart.setVisible(false);
			this.btnOptions.setVisible(false);
			this.btnExit.setVisible(false);
			this.btnBack.setVisible(true);
			pintaReglas(g);
			
		}
		else if(this.state==3) {
			this.btnStart.setVisible(false);
			this.btnOptions.setVisible(false);
			this.btnExit.setVisible(false);
			g.setFont(new Font("Helvetica", Font.BOLD, 70));
			g.setColor(Color.white);
			g.drawString("End of", 180, 350); 
			g.drawString("the game!!", 120, 450);
			this.btnBack.setVisible(true);
			this.c0=this.c1=this.c2=this.c3=this.c4=this.c5=this.c6=this.c7=4;
			this.hec=new Hec();
		}
	}
	private void crearBotones() {
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
//		int x=46;
//		int y=662;
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
		switch(direction) {
			case 0:
				g.drawLine(p.x+10, p.y, p.x+20, p.y-10); //izquierda
				g.drawLine(p.x+10, p.y, p.x+30, p.y); //abajo
				g.drawLine(p.x+30, p.y, p.x+20, p.y-10); //derecha
				break;
			case 1:
				g.drawLine(p.x+40, p.y, p.x+40, p.y+10); //derecha
				g.drawLine(p.x+30, p.y, p.x+40, p.y); //arriba
				g.drawLine(p.x+30, p.y, p.x+40, p.y+10); //abajo
				break;
			case 2:
				g.drawLine(p.x+40, p.y+10, p.x+40, p.y+30); //izquierda
				g.drawLine(p.x+40, p.y+10, p.x+50, p.y+20); //arriba
				g.drawLine(p.x+50, p.y+20, p.x+40, p.y+30); //abajo
				break;
			case 3:
				g.drawLine(p.x+40, p.y+30, p.x+40, p.y+40); //derecha
				g.drawLine(p.x+40, p.y+30, p.x+30, p.y+40); //izquierda
				g.drawLine(p.x+40, p.y+40, p.x+30, p.y+40); //abajo
				break;
			case 4:
				g.drawLine(p.x+10, p.y+40, p.x+20, p.y+50); //derecha
				g.drawLine(p.x+10, p.y+40, p.x+30, p.y+40); //arriba
				g.drawLine(p.x+30, p.y+40, p.x+20, p.y+50); //izquierda
				break;
			case 5:
				g.drawLine(p.x+10, p.y+40, p.x, p.y+40); //abajo
				g.drawLine(p.x, p.y+40, p.x, p.y+30); //izquierda
				g.drawLine(p.x, p.y+30, p.x+10, p.y+40); //arriba
				break;
			case 6:
				g.drawLine(p.x, p.y+10, p.x, p.y+30); //derecha
				g.drawLine(p.x, p.y+30, p.x-10, p.y+20); //abajo
				g.drawLine(p.x, p.y+10, p.x-10, p.y+20); //arriba
				break;
			case 7:
				g.drawLine(p.x, p.y, p.x+10, p.y); //arriba
				g.drawLine(p.x, p.y, p.x, p.y+10); //izquierda
				g.drawLine(p.x, p.y+10, p.x+10, p.y); //arriba
				break;
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		//this.start=true;
		//run();
		//System.out.println(d.diccionario.containsKey("achilles".hashCode()));
		hec.shuffle();
		this.repaint();
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
		
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {

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
		// TODO Auto-generated method stub
		if(this.state == 1) {
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
					this.bandera=true;
					
				}else {
					this.isBuilding = true;
					s+=pointer.toString();
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
			if(k.getKeyCode() == KeyEvent.VK_UP) {
				
				if(this.bandera) {
					this.tablero[contadorTablero++]=s;
					this.bandera=false;
				}
				
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
			
			this.repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent k) {
		// TODO Auto-generated method stub
	}
}

