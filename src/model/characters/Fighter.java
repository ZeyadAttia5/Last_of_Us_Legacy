package model.characters;

import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;

import exceptions.NoAvailableResourcesException;

public class Fighter extends Hero {

	public Fighter(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);

	}
	@Override
	public void attack() throws InvalidTargetException, NotEnoughActionsException {
		if (this.getTarget() == null) 
			throw new InvalidTargetException("No target is selected");
		 else {
			if (this.isTargetAdjacent()) {
				if (!isSpecialAction()) {
					if (getActionsAvailable() > 0) {
						if (getTarget() instanceof Zombie) {
							getTarget().getAttackers().add(this);
							getTarget().setCurrentHp(this.getTarget().getCurrentHp() - this.getAttackDmg());
							setActionsAvailable(getActionsAvailable() - 1);
						}
						else
							throw new exceptions.InvalidTargetException(
									"Invalid Target, You Cannot Attack Other Heros");
					}
					else
						throw new NotEnoughActionsException("Not Enough Actions Available");
				}
				// special action is true
				else {
					if (this.getTarget() instanceof Zombie) {
						getTarget().getAttackers().add(this);
						this.getTarget().setCurrentHp(this.getTarget().getCurrentHp() - this.getAttackDmg());
						System.out.println("Fighter Available Actions: "+this.getActionsAvailable());
					}
					else
						throw new exceptions.InvalidTargetException("Invalid Target, You Cannot Attack Other Heros");
				}
			} else
				throw new exceptions.InvalidTargetException("Target is not adjacent.");
		}
	}

	public void useSpecial() throws NoAvailableResourcesException {
		// When a supply is used, Fighter can attack as many times in a turn
		// without costing action points, for 1 turn.
		if (this.getSupplyInventory().size() > 0) {
			this.getSupplyInventory().get(0).use(this);
		} else {
			throw new exceptions.NoAvailableResourcesException("Insufficient Supplies");
		}
	}

}
