package model.characters;

import engine.Game;
import exceptions.*;

public class Medic extends Hero {
	// Heal amount attribute - quiz idea

	public Medic(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
	}

	@Override
	public void useSpecial() throws NoAvailableResourcesException, InvalidTargetException {

		if (this.getTarget() == null)
			throw new InvalidTargetException("No Target is selected");
		if ((this.getTarget() instanceof Zombie)) {
			throw new InvalidTargetException("Cannot heal a Zombie");
		}
		if (!(Game.heroes.contains(this.getTarget())))
			throw new InvalidTargetException("Invalid Target");
		if (!this.isTargetAdjacentCheckIndex()) {
			throw new InvalidTargetException("The target is not adjacent");
		} else {
			// Whenever a supply is used, allows Medic to other heros or themselves
			if (this.getSupplyInventory().size() > 0) {
				Hero target = (Hero) this.getTarget();
				this.getSupplyInventory().get(0).use(this);
				target.setCurrentHp(target.getMaxHp());
			} else {
				throw new exceptions.NoAvailableResourcesException("Insufficient Supplies");
			}
		}
	}

}
