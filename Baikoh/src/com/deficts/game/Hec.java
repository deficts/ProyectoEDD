package com.deficts.game;

import java.awt.Graphics;
import java.util.Random;

public class Hec{
	private final String [] consonantes = {"B","C","D","F","G","H","J","K","L","M","N","P","Q","R","S","T","V","X","Y","Z"};
	private final String [] vocales = {"A","E","I","O","U"};
	public Node <Letra>[][] tablero;
	public Node<Letra> esquina;
	Random ran=new Random();
	
	@SuppressWarnings("unchecked")
	public Hec() {
		this.tablero= new Node[8][8];
		llenado();
	}
	
	
	private void mapeo() {
//		int iteracion=0;
		this.esquina=this.tablero[7][7];
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if(i>0 && i<7 && j>0 && j<7) {
					this.tablero[i][j].up=this.tablero[i][j-1];
					this.tablero[i][j].down=this.tablero[i][j+1];
					this.tablero[i][j].left=this.tablero[i-1][j];
					this.tablero[i][j].right=this.tablero[i+1][j];
					this.tablero[i][j].upleft=this.tablero[i-1][j-1];
					this.tablero[i][j].upright=this.tablero[i+1][j-1];
					this.tablero[i][j].downleft=this.tablero[i-1][j+1];
					this.tablero[i][j].downright=this.tablero[i+1][j+1];
//					System.out.println("check enmedio");
				}
				else {
					if(i==0) {
						this.tablero[i][j].upleft=null;
						this.tablero[i][j].left=null;
						this.tablero[i][j].downleft=null;
						this.tablero[i][j].right=this.tablero[i+1][j];
						if(j==7) {
							this.tablero[i][j].down=null;
							this.tablero[i][j].downright=null;
						}
						else {
							this.tablero[i][j].down=this.tablero[i+1][j];
							this.tablero[i][j].downright=this.tablero[i+1][j+1];
						}
						if(j==0) {
							this.tablero[i][j].up=null;
							this.tablero[i][j].upright=null;
						}
						else {
							this.tablero[i][j].up=this.tablero[i][j-1];
							this.tablero[i][j].upright=this.tablero[i+1][j-1];
						}
//						System.out.println("check izquierda");
						
					}
					else if(i==7) {
						this.tablero[i][j].right=null;
						this.tablero[i][j].upright=null;
						this.tablero[i][j].downright=null;
						this.tablero[i][j].left=this.tablero[i-1][j];
						if(j==0) {
							this.tablero[i][j].up=null;
							this.tablero[i][j].upright=null;
						}
						else {
							this.tablero[i][j].up=this.tablero[i][j-1];
							this.tablero[i][j].upleft=this.tablero[i-1][j-1];
						}
						if(j==7) {
							this.tablero[i][j].down=null;
							this.tablero[i][j].downright=null;
						}
						else {
							this.tablero[i][j].down=this.tablero[i][j+1];
							this.tablero[i][j].downleft=this.tablero[i-1][j+1];
						}
//						System.out.println("check derecha");
					}
					else if(j==0 && i>0 && i<7) {
						this.tablero[i][j].up=null;
						this.tablero[i][j].upright=null;
						this.tablero[i][j].upleft=null;
						this.tablero[i][j].left=this.tablero[i-1][j];
						this.tablero[i][j].right=this.tablero[i+1][j];
						this.tablero[i][j].down=this.tablero[i][j+1];
						this.tablero[i][j].downright=this.tablero[i+1][j+1];
						this.tablero[i][j].downleft=this.tablero[i-1][j+1];
//						System.out.println("check arriba");
					}
					else if(j==7 && i>0 && i<7) {
						this.tablero[i][j].down=null;
						this.tablero[i][j].downleft=null;
						this.tablero[i][j].downright=null;
						this.tablero[i][j].up=this.tablero[i][j-1];
						this.tablero[i][j].upright=this.tablero[i+1][j-1];
						this.tablero[i][j].upleft=this.tablero[i-1][j-1];
						this.tablero[i][j].left=this.tablero[i-1][j];
						this.tablero[i][j].right=this.tablero[i+1][j];
//						System.out.println("check abajo");
					}
				}
//				System.out.println("iteracion: "+iteracion++);
			}
		}
		
	}
	
	public void llenado(){
		int x=130;
		int y=200;
		int contador=0;
		for (int i = 0; i < this.tablero.length; i++) {
			for (int j = 0; j < this.tablero[i].length; j++) {
				if(contador==3) {
					this.tablero[i][j]=new Node <Letra> (new Letra(getVocal(), x,y));					
					contador=0;
				}
				else {
					this.tablero[i][j]=new Node <Letra> (new Letra(getConsonante(), x,y));
					contador++;
				}
				x+=42;
			}
			y+=42;
			x=130;
		}
		
		mapeo();
	}
	
	private String getVocal() {
		return vocales[ran.nextInt(5)];
	}
	
	private String getConsonante() {
		return consonantes[ran.nextInt(20)];
	}
	
	public void pinta(int i, int j, Graphics g) {
		this.tablero[i][j].getData().draw(g);
	}
	
}

class Node<E>{
	public Node<E> right,left,up,down,upleft,upright,downleft,downright;
	private E dato;
	
	public Node(Node<E> right, Node<E> left, Node<E> up, Node<E> down, Node<E> upleft, Node<E> upright, Node<E> downleft, Node<E> downright,
			E dato) {
		super();
		this.right = right;
		this.left = left;
		this.up = up;
		this.down = down;
		this.upleft = upleft;
		this.upright = upright;
		this.downleft = downleft;
		this.downright = downright;
		this.dato = dato;
	}

	public Node(E dato) {
		this(null,null,null,null,null,null,null,null,dato);
	}
	
	public String toString(){
		return this.dato.toString();
		
	}
	
	public E getData() {
		return this.dato;
	}
	
}