package com.deficts.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Test {
	public static HashMap<Integer, String> diccionario = new HashMap<>();
	
	public static void leer() throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader("engmix.txt"));
			String line = br.readLine();
			diccionario.put(line.hashCode(), line);
			while(line != null) {
				System.out.println(line);
				diccionario.put(line.hashCode(), line);
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Diccionario no encontrado");
		}
	}
	
	public static void main(String[] args) {
		System.out.println(new File(".").getAbsoluteFile());
		try {
			leer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(diccionario.containsKey("achilles".hashCode()));
	}
}
