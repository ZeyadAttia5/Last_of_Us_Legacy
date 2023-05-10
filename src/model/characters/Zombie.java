package model.characters;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.world.Cell;
import model.world.CharacterCell;

public class Zombie extends Character {
	static int ZOMBIES_COUNT = 1;

	public Zombie() {
		super("Zombie " + ZOMBIES_COUNT, 40, 10);
		ZOMBIES_COUNT++;
	}

	public void onCharacterDeath() {
		if (this.getCurrentHp() <= 0) {

			boolean isZombieAdded = false;

			do {
				Zombie z = new Zombie();
				int x = Game.rand.nextInt(14) + 1;
				int y = Game.rand.nextInt(14) + 1;
				if (Game.map[x][y] instanceof CharacterCell) {
					if (((CharacterCell) Game.map[x][y]).getCharacter() == null) {
						((CharacterCell) Game.map[x][y]).setCharacter(z);
						z.setLocation(new Point(x, y));
						Game.zombies.add(z);
						isZombieAdded = true;
					}
				}
			} while (!isZombieAdded);
			((CharacterCell) Game.map[this.getLocation().x][this.getLocation().y]).setCharacter(null);
			Game.zombies.remove(this);
		}
	}

	public void attack() throws InvalidTargetException, NotEnoughActionsException {

		ArrayList<Cell> adjCells = this.getAdjacentCells();
		for (int j = 0; j < adjCells.size(); j++) {
			Cell adjCell = (Cell) adjCells.get(j);
			if (adjCell instanceof CharacterCell) {
				if (((CharacterCell) adjCell).getCharacter() != null) {
					if (((CharacterCell) adjCell).getCharacter() instanceof Hero) {
						this.setTarget(((CharacterCell) adjCell).getCharacter());
						try {
							super.attack();
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

	public void defend(Character c) throws exceptions.InvalidTargetException {
		if (!getAttackers().contains(c)) {
			getAttackers().add(c);
			c.setCurrentHp(c.getCurrentHp() - (getAttackDmg() / 2));
		}
	}
}
