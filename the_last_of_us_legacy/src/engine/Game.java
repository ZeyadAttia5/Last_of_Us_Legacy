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
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\omar\\OneDrive\\Documents\\GitHub\\Last_of_Us_Legacy\\info\\Heros_csv"));
		String line = br.readLine();
		int i = 0;
		while(line != null) {
			String [] Content = line.split(",");
			Integer.parseInt(Content[i++]);
			if (Content[i].equals("FIGH")) {
				Fighter newFighter = new Fighter();
				Heroes.add(newFighter);
			}
			if (Content[i].equals("MED")) {
				Medic newMed = new Medic();
				Heroes.add(newMed);
			}
			if (Content[i].equals("EXP")) {
				Explorer newExp = new Explorer();
				Heroes.add(newExp);
			}
			line = br.readLine();
		}
		br.close();
		
	}

}
