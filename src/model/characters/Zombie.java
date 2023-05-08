package model.characters;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;

public class Zombie extends Character {
	static int ZOMBIES_COUNT = 1;

	public Zombie() {
		super("Zombie " + ZOMBIES_COUNT, 40, 10);
		ZOMBIES_COUNT++;
	}

	public void onCharacterDeath() {
		Game.zombies.remove(this);
		Game.zombiesRemoved.add(new Zombie());
	}

	public void attack() throws InvalidTargetException, NotEnoughActionsException {
		super.attack();
		if (this.getTarget() == null) {
			throw new InvalidTargetException("No target is selected");
		}
		if (this.isTargetAdjacent()) {
			if (getTarget() instanceof Hero) {
				getTarget().setCurrentHp(this.getTarget().getCurrentHp() - getAttackDmg());
				getTarget().getAttackers().add(this);
			} else
				throw new exceptions.InvalidTargetException("Invalid Target, You Cannot Attack Other Heros");
		} else
			throw new exceptions.InvalidTargetException("Target is not adjacent.");
	}

	public void defend(Character c) throws exceptions.InvalidTargetException {
		if (!getAttackers().isEmpty()) {
			if (getAttackers().contains(c)) {
				c.setCurrentHp(c.getCurrentHp() - (c.getAttackDmg() / 2));
				getAttackers().clear();
			} else
				throw new InvalidTargetException("This target did not attack you.");
		}

		else
			throw new InvalidTargetException("You have not been attacked.");
	}
}
