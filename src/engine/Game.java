package engine;

import model.collectibles.*;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
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
	public static ArrayList<Zombie> zombiesRemoved;

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

	public static void startGame(Hero h) {
		// create map
		for (int x = 0; x < 15; x++) {
			for (int y = 0; y < 15; y++) {
				map[x][y] = new CharacterCell(null);
			}
		}

		availableHeroes.remove(h);
		heroes.add(h);
		h.setLocation(new Point(0, 0));
		((CharacterCell) map[0][0]).setCharacter(h);
		h.getAdjacentCells().forEach((cell) -> cell.setVisible(true));
		map[0][0].setVisible(true);

		for (int i = 0; i < 5; i++) { // Add Randomized Vax
			Vaccine v = new Vaccine();
			Random rand = new Random();
			int x = rand.nextInt(14) + 1;
			int y = rand.nextInt(14) + 1;
			if (map[x][y] instanceof CharacterCell) {
				if (((CharacterCell) map[x][y]).getCharacter() == null)
					map[x][y] = new CollectibleCell(v);
				else
					i--;
			} else
				i--;
		}
		for (int i = 0; i < 5; i++) { // Add Randomized Supply
			Supply s = new Supply();
			Random rand = new Random();
			int x = rand.nextInt(14) + 1;
			int y = rand.nextInt(14) + 1;
			if (map[x][y] instanceof CharacterCell) {
				if (((CharacterCell) map[x][y]).getCharacter() == null)
					map[x][y] = new CollectibleCell(s);
				else
					i--;
			} else
				i--;
		}

		int zombieCount = 0;
		do { // Add Randomized Zombie
			Zombie z = new Zombie();
			Random rand = new Random();
			int x = rand.nextInt(14) + 1;
			int y = rand.nextInt(14) + 1;
			if (map[x][y] instanceof CharacterCell) {
				if (((CharacterCell) map[x][y]).getCharacter() == null) {
					((CharacterCell) map[x][y]).setCharacter(z);
					z.setLocation(new Point(x, y));
					zombies.add(z);
					zombieCount++;
				}
			}
		} while (zombieCount == 10);
		for (int i = 0; i < 5; i++) { // Add Randomized Trap
			Random rand = new Random();
			int x = rand.nextInt(14) + 1;
			int y = rand.nextInt(14) + 1;
			if (map[x][y] instanceof CharacterCell) {
				if (((CharacterCell) map[x][y]).getCharacter() == null)
					map[x][y] = new TrapCell();
				else
					i--;
			} else
				i--;
		}

	}

	public static boolean checkWin() {
		boolean result = false;
		if (vaccinesUsed == 5) {
			if (heroes.size() >= 5)
				result = true;
		}
		return result;
	}

	public static void endTurn() {
		// The method should allow all zombies to attempt to attack an adjacent hero (if
		// exists)
		for (int i = 0; i < zombies.size(); i++) {
			Zombie zombie = zombies.get(i);
			ArrayList<Cell> adjCells = zombie.getAdjacentCells();
			for (int j = 0; j < adjCells.size(); j++) {
				Cell adjCell = (Cell) adjCells.get(i);
				if (adjCell instanceof CharacterCell) {
					if (((CharacterCell) adjCell).getCharacter() != null) {
						if (((CharacterCell) adjCell).getCharacter() instanceof Hero) {
							zombie.setTarget(((CharacterCell) adjCell).getCharacter());
							try {
								zombie.attack();
								break;
							} catch (InvalidTargetException e) {
								// TODO Auto-generated catch block
								e.getMessage();
							} catch (NotEnoughActionsException e) {
								// TODO Auto-generated catch block
								e.getMessage();
							}
						}
					}
				}
			}

		}
		// turnOff visibility for the whole map
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				map[row][col].setVisible(false);
			}
		}

		// reset each hero’s actions, target, and special, update the map visibility
		// in the game such that only
		// cells adjacent to heroes are visible
		heroes.forEach((hero) -> {
			hero.setActionsAvailable(hero.getMaxActions());
			hero.setTarget(null);
			hero.setSpecialAction(false);
			// set visibility for adjacent maps only
			hero.getAdjacentCells().forEach((cell) -> cell.setVisible(true));
			Point heroLocation = hero.getLocation();
			map[heroLocation.x][heroLocation.y].setVisible(true);
		});

		if (zombies.size() < 10) {
			// Add a Randomized Zombie
			boolean isZombieAdded = false;
			do {
				Zombie z = new Zombie();
				Random rand = new Random();
				int x = rand.nextInt(14) + 1;
				int y = rand.nextInt(14) + 1;
				if (map[x][y] instanceof CharacterCell) {
					if (((CharacterCell) map[x][y]).getCharacter() == null) {
						((CharacterCell) map[x][y]).setCharacter(z);
						z.setLocation(new Point(x, y));
						zombies.add(z);
						isZombieAdded = true;
					}
				}
			} while (!isZombieAdded);
		}
	}

	public static boolean checkGameOver() {
		boolean result = false;

		if (availableHeroes.isEmpty())
			result = true;
		if (heroes.isEmpty())
			result = true;

		return result;
	}

}
