package model.characters;

import java.awt.Point;
import java.util.Random;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.world.CharacterCell;

public class Zombie extends Character {
	static int ZOMBIES_COUNT = 1;

	public Zombie() {
		super("Zombie " + ZOMBIES_COUNT, 40, 10);
		ZOMBIES_COUNT++;
	}

	public void onCharacterDeath() {
		if (this.getCurrentHp() <= 0) {

			((CharacterCell) Game.map[this.getLocation().x][this.getLocation().y]).setCharacter(null);
			Game.zombies.remove(this);
			boolean isZombieAdded = false;
			do {
				Zombie z = new Zombie();
				Random rand = new Random();
				int x = rand.nextInt(14) + 1;
				int y = rand.nextInt(14) + 1;
				if (Game.map[x][y] instanceof CharacterCell) {
					if (((CharacterCell) Game.map[x][y]).getCharacter() == null) {
						((CharacterCell) Game.map[x][y]).setCharacter(z);
						z.setLocation(new Point(x, y));
						Game.zombies.add(z);
						isZombieAdded = true;
					}
				}
			} while (!isZombieAdded);
			Game.zombiesRemoved.add(new Zombie());
		}
	}

	public void attack() throws InvalidTargetException, NotEnoughActionsException {
		super.attack();
		if (this.isTargetAdjacent()) {
			if (getTarget() instanceof Hero) {
				getTarget().getAttackers().add(this);
				getTarget().setCurrentHp(this.getTarget().getCurrentHp() - getAttackDmg());
				//this.getTarget().onCharacterDeath();
			} else
				throw new exceptions.InvalidTargetException("Invalid Target, You Cannot Attack Other Zombies");
		} else
			throw new exceptions.InvalidTargetException("Target is not adjacent.");
	}

	public void defend(Character c) throws exceptions.InvalidTargetException {
		if (!getAttackers().isEmpty()) {

			if (getAttackers().contains(c)) {
				c.setCurrentHp(c.getCurrentHp() - (getAttackDmg() / 2));

				getAttackers().clear();
			} else
				throw new InvalidTargetException("This target did not attack you.");
		}

		else
			throw new InvalidTargetException("You have not been attacked.");
	}
}
