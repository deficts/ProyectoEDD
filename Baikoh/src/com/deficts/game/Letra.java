package com.deficts.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

public class Letra {
	private String letra;
	private Color color;
	private int x,y;
	Random ran=new Random();
	
	public Letra(String letra, int x, int y) {
		this.letra=letra;
		this.color=selectColor();
		this.x=x;
		this.y=y;
	}
	
	public Letra(String letra, int x, int y, Color color) {
		this.letra=letra;
		this.color=color;
		this.x=x;
		this.y=y;
	}
	
	private Color selectColor() {
		int col=ran.nextInt(9);
		
		switch (col) {
		case 0: return Color.red; 
		case 1: return Color.yellow;
		case 2: return Color.blue;
		case 3: return Color.cyan; 
		case 4: return Color.GREEN;
		case 5: return Color.magenta; 
		case 6: return Color.orange;
		case 7: return Color.PINK;
		case 8: return Color.WHITE;
		
		default:
			return Color.BLACK;
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(this.color.darker().darker());
		g.fillRect(this.x, this.y, 40, 40);
		
		g.setColor(this.color);
		g.drawRect(this.x,this.y, 40, 40);
		
		g.setColor(this.color.brighter());
		g.setFont(new Font("Helvetica", Font.BOLD, 30));
		g.drawString(this.letra, this.x+10, this.y+30);
	}
	
	public String toString() {
		return this.letra;
	}
	
}
