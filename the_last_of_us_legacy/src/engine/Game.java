package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.io.*;

import model.characters.*;
import model.world.Cell;

public class Game {
	static ArrayList<Hero> availableHeroes;
	static ArrayList<Hero> Heroes;
	static ArrayList<Zombie> Zombies;
	static Cell [][] map;

	public Game() {}
	
	public static void loadHeros(String filePath) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("Heros.csv"));
		String line = br.readLine();
		while(line != null) {
			String [] Content = line.split(",");

			if (Content[1].equals("FIGH")) {
				Fighter newFighter = new Fighter(Content[0], Integer.parseInt(Content[2]), Integer.parseInt(Content[3]), Integer.parseInt(Content[4]));
				availableHeroes.add(newFighter);
			}
			if (Content[1].equals("MED")) {
				Medic newMed = new Medic(Content[0], Integer.parseInt(Content[2]), Integer.parseInt(Content[3]), Integer.parseInt(Content[4]));
				availableHeroes.add(newMed);
			}
			if (Content[1].equals("EXP")) {
				Explorer newExp = new Explorer(Content[0], Integer.parseInt(Content[2]), Integer.parseInt(Content[3]), Integer.parseInt(Content[4]));
				availableHeroes.add(newExp);
			}
			line = br.readLine();
		}
		br.close();
		
	}

}
