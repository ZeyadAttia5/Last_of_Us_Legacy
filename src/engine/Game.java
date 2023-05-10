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
				if (x == 0 && y == 0)
					map[x][y].setVisible(true);
			}
		}

		heroes.add(h);
		h.setLocation(new Point(0, 0));
		((CharacterCell) map[0][0]).setCharacter(h);
		availableHeroes.remove(h);

		Random rand = new Random();
		for (int i = 0; i < 5; i++) { // Add Randomized Vax
			Vaccine v = new Vaccine();
			int x = rand.nextInt(0, 15);
			int y = rand.nextInt(0, 15);
			if (map[x][y] instanceof CharacterCell) {
				if (((CharacterCell) map[x][y]).getCharacter() == null) {
					map[x][y] = new CollectibleCell(v);
				} else
					i--;
			} else
				i--;
		}
		for (int i = 0; i < 5; i++) { // Add Randomized Supply
			Supply s = new Supply();
			int x = rand.nextInt(0, 15);
			int y = rand.nextInt(0, 15);
			if (map[x][y] instanceof CharacterCell) {
				if (((CharacterCell) map[x][y]).getCharacter() == null) {
					map[x][y] = new CollectibleCell(s);
				} else
					i--;
			} else
				i--;
		}

		int zombieCount = 0;
		do { // Add Randomized Zombie
			Zombie z = new Zombie();
			int x = rand.nextInt(15);
			int y = rand.nextInt(15);
			if (map[x][y] instanceof CharacterCell) {
				if (((CharacterCell) map[x][y]).getCharacter() == null) {
					((CharacterCell) map[x][y]).setCharacter(z);
					z.setLocation(new Point(x, y));
					zombies.add(z);
					zombieCount++;
				}
			}
		} while (zombieCount < 10);

		for (int i = 0; i < 5; i++) { // Add Randomized Trap
			int x = rand.nextInt(15);
			int y = rand.nextInt(15);
			if (map[x][y] instanceof CharacterCell) {
				if (((CharacterCell) map[x][y]).getCharacter() == null) {
					map[x][y] = new TrapCell();
				} else
					i--;
			} else
				i--;
		}
		//set adjacent cells visibility to visible, here for an edge case
	//	h.getAdjacentIndices().forEach((point) -> map[point.x][point.y].setVisible(true));
	}

	public static boolean checkWin() {
		boolean result = false;
		for (Hero hero : heroes) {
			if (hero.getVaccineInventory().size() != 0)
				return false;
		}

		for (int x = 0; x < 15; x++) {
			for (int y = 0; y < 15; y++) {
				if (map[x][y] instanceof CollectibleCell
						&& ((CollectibleCell) map[x][y]).getCollectible() instanceof Vaccine) {
					return false;
				}
			}
		}

		if (heroes.size() >= 5) {
			return true;
		}
		return result;

	}

	public static void endTurn() {
		// The method should allow all zombies to attempt to attack an adjacent hero (if
		// exists)

//		for(Zombie zombie : Game.zombies) {
//			if(zombie.getCurrentHp() <= 0) {
//				zombie.onCharacterDeath();
//			}
//		}

		for (Zombie zombie : zombies) {
			try {
				zombie.attack();
				zombie.setTarget(null);
			} catch (InvalidTargetException | NotEnoughActionsException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
		}

		// turnOff visibility for the whole map
		// reset each hero’s actions, target, and special, update the map visibility
		// in the game such that only
		// cells adjacent to heroes are visible

		for (int x = 0; x < 15; x++) {
			for (int y = 0; y < 15; y++) {
				map[x][y].setVisible(false);
			}
		}

		heroes.forEach((hero) -> {
			hero.setActionsAvailable(hero.getMaxActions());
			hero.setTarget(null);
			hero.setSpecialAction(false);
			// set visibility for adjacent maps only
			hero.getAdjacentCells().forEach((cell) -> cell.setVisible(true));
			Point heroLocation = hero.getLocation();
			map[heroLocation.x][heroLocation.y].setVisible(true);
		});

		// if (zombies.size() < 10) {
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
		// }
	}

	public static boolean checkGameOver() {

		if (availableHeroes.isEmpty())
			return true;
		if (heroes.isEmpty())
			return true;
		boolean thereAreStillVaccines = false;
		for (Hero hero : heroes) {
			if (hero.getVaccineInventory().size() != 0)
				return false;
			thereAreStillVaccines = true;
		}

		for (int x = 0; x < 15; x++) {
			for (int y = 0; y < 15; y++) {
				if (map[x][y] instanceof CollectibleCell
						&& ((CollectibleCell) map[x][y]).getCollectible() instanceof Vaccine) {
					return false;
				}
			}
		}

		if (!(availableHeroes.isEmpty()) && thereAreStillVaccines)
			return true;

		return false;
	}

}
