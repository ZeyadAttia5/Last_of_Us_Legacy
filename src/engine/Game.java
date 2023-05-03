package engine;

import model.collectibles.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

public class Game {

	public static Cell[][] map;
	public static ArrayList<Hero> availableHeroes = new ArrayList<Hero>();
	public static ArrayList<Hero> heroes = new ArrayList<Hero>();
	public static ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	public static int vaccinesUsed;

	public static void loadHeroes(String filePath) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Hero hero = null;
			switch (content[1]) {
			case "FIGH":
				hero = new Fighter(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]));
				break;
			case "MED":
				hero = new Medic(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]));
				break;
			case "EXP":
				hero = new Explorer(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]));
				break;
			}
			availableHeroes.add(hero);
			line = br.readLine();

		}
		br.close();

	}

	public static void createMap() {
		for (int x = 0; x < 16; x++) {

			for (int y = 0; y < 16; y++) {
				map[x][y] = new CharacterCell(null);
			}

		}
	}

	public static void startGame(Hero h) {
		availableHeroes.remove(h);
		heroes.add(h);
		map[0][0] = new CharacterCell(h, true);

		for (int i = 0; i < 5; i++) { // Add Randomized Vax
			Vaccine v = new Vaccine();
			Random rand = new Random();
			int x = rand.nextInt(14) + 1;
			int y = rand.nextInt(14) + 1;
			if(map[x][y] instanceof CharacterCell) {
				if (((CharacterCell) map[x][y]).getCharacter() == null)
					map[x][y] = new CollectibleCell(v);
				else
					i--;
			}
			else
				i--;
		}
		for (int i = 0; i < 5; i++) { // Add Randomized Supply
			Supply s = new Supply();
			Random rand = new Random();
			int x = rand.nextInt(14) + 1;
			int y = rand.nextInt(14) + 1;
			if(map[x][y] instanceof CharacterCell) {
				if (((CharacterCell) map[x][y]).getCharacter() == null)
					map[x][y] = new CollectibleCell(s);
				else
					i--;
			}
			else
				i--;
		}

		for (int i = 0; i < 10; i++) { // Add Randomized Zombie
			Zombie z = new Zombie();
			Random rand = new Random();
			int x = rand.nextInt(14) + 1;
			int y = rand.nextInt(14) + 1;
			if(map[x][y] instanceof CharacterCell) {
				if (((CharacterCell) map[x][y]).getCharacter() == null)
					map[x][y] = new CharacterCell(z);
				else
					i--;
			}
			else
				i--;
		}

		for (int i = 0; i < 5; i++) { // Add Randomized Trap
			Random rand = new Random();
			int x = rand.nextInt(14) + 1;
			int y = rand.nextInt(14) + 1;
			if(map[x][y] instanceof CharacterCell) {
				if (((CharacterCell) map[x][y]).getCharacter() == null)
					map[x][y] = new TrapCell();
				else
					i--;
			}
			else
				i--;
		}

	}

	public static boolean checkGameOver() {
		return false;
	}

	public static boolean checkGameOver2() {
		return false;
	}
}
