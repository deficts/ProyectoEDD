package com.deficts.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.HashMap;
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
	
	public Node<Letra> getEsquina() {
		return this.esquina;
	}
	
	private void mapeo() {
		this.esquina=this.tablero[7][0];
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if(i==0 && j==0) {
					tablero[i][j].down=tablero[i+1][j];
					tablero[i][j].right=tablero[i][j+1];
					tablero[i][j].downright=tablero[i+1][j+1];
				}else if(i==0 && j==7 ) {
					tablero[i][j].down=tablero[i+1][j];
					tablero[i][j].left=tablero[i][j-1];
					tablero[i][j].downleft=tablero[i+1][j-1];
				}else if(i==7 && j==0 ) {
					tablero[i][j].up=tablero[i-1][j];
					tablero[i][j].right=tablero[i][j+1];
					tablero[i][j].upright=tablero[i-1][j+1];
				}else if(i==7 && j==7 ) {
					tablero[i][j].up=tablero[i-1][j];
					tablero[i][j].left=tablero[i][j-1];
					tablero[i][j].upright=tablero[i-1][j-1];
				}else if (i==0 && j<7 && j>0) {
					tablero[i][j].down=tablero[i+1][j];
					tablero[i][j].right=tablero[i][j+1];
					tablero[i][j].downright=tablero[i+1][j+1];
					tablero[i][j].left=tablero[i][j-1];
					tablero[i][j].downleft=tablero[i+1][j-1];
				}
				else if (i==7 && j<7 && j>0) {
					tablero[i][j].up=tablero[i-1][j];
					tablero[i][j].right=tablero[i][j+1];
					tablero[i][j].upright=tablero[i-1][j+1];
					tablero[i][j].left=tablero[i][j-1];
					tablero[i][j].upleft=tablero[i-1][j-1];
				}
				else if(j==0 && i<7 && i>0) {
					tablero[i][j].down=tablero[i+1][j];
					tablero[i][j].up=tablero[i-1][j];
					tablero[i][j].downright=tablero[i+1][j+1];
					tablero[i][j].right=tablero[i][j+1];
					tablero[i][j].upright=tablero[i-1][j+1];
				}
				else if(j==7 && i<7 && i>0) {
					tablero[i][j].up=tablero[i-1][j];
					tablero[i][j].down=tablero[i+1][j];
					tablero[i][j].left=tablero[i][j-1];
					tablero[i][j].downleft=tablero[i+1][j-1];
					tablero[i][j].upleft=tablero[i-1][j-1];
				}else {
					tablero[i][j].down=tablero[i+1][j];
					tablero[i][j].right=tablero[i][j+1];
					tablero[i][j].downright=tablero[i+1][j+1];
					tablero[i][j].left=tablero[i][j-1];
					tablero[i][j].downleft=tablero[i+1][j-1];
					tablero[i][j].up=tablero[i-1][j];
					tablero[i][j].upright=tablero[i-1][j+1];
					tablero[i][j].upleft=tablero[i-1][j-1];
				}
				
			}
		}
		
	}
	
	public void llenado(){
		int x=90;
		int y=150;
		int contador=0;
		for (int i = 0; i < this.tablero.length; i++) {
			for (int j = 0; j < this.tablero[i].length; j++) {
				if(contador==2) {
					this.tablero[i][j]=new Node <Letra> (new Letra(getVocal(), x,y, Color.white));
					contador=0;
				}
				else {
					this.tablero[i][j]=new Node <Letra> (new Letra(getConsonante(), x,y));
					contador++;
				}
				x+=55;
			}
			y+=55;
			x=90;
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
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < tablero.length; i++) {
			s+="[";
			for (int j = 0; j < tablero[i].length; j++) {
				s+=tablero[i][j];
			}
			s+="] \n";
		}
		return s;
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