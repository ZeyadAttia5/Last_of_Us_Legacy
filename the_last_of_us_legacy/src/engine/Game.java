package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.io.*;

import model.characters.*;
import model.world.Cell;

public class Game {
	public static ArrayList<Hero> availableHeroes;
	public static ArrayList<Hero> heroes;
	public static ArrayList<Zombie> zombies;
	public static Cell [][] map;


	
	public static void loadHeros(String filePath) throws Exception {
		FileReader csvFile = new FileReader(filePath);
		BufferedReader br = new BufferedReader((csvFile));
		String line = br.readLine();
		while(line != null) {
			String [] Content = line.split(",");

			if (Content[1].equals("FIGH")) {
				Fighter newFighter = new Fighter(Content[0], Integer.parseInt(Content[2]), Integer.parseInt(Content[4]), Integer.parseInt(Content[3]));
				availableHeroes.add(newFighter);
			}
			if (Content[1].equals("MED")) {
				Medic newMed = new Medic(Content[0], Integer.parseInt(Content[2]), Integer.parseInt(Content[4]), Integer.parseInt(Content[3]));
				availableHeroes.add(newMed);
			}
			if (Content[1].equals("EXP")) {
				Explorer newExp = new Explorer(Content[0], Integer.parseInt(Content[2]), Integer.parseInt(Content[4]), Integer.parseInt(Content[3]));
				availableHeroes.add(newExp);
			}
			line = br.readLine();
		}
		br.close();
		csvFile.close();
		
	}

}
