package com.deficts.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Diccionario {
	public HashMap<Integer, String> diccionario = new HashMap<>();
	
	public Diccionario(){
		try {
			leer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void leer() throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader("engmix.txt"));
			String line = br.readLine();
			diccionario.put(line.hashCode(), line);
			while(line != null) {
				diccionario.put(line.hashCode(), line);
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Diccionario no encontrado");
		}
	}
}
