package model.characters;

import model.world.*;
import engine.Game;
import exceptions.NoAvailableResourcesException;

public class Explorer extends Hero {

	public Explorer(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);

	}

	@Override
	public void useSpecial() throws NoAvailableResourcesException {
		// Whenever a supply is used, allows the player to see the entirety of the map
		// for 1 turn

		if (this.getSupplyInventory().size() > 0) {
			this.getSupplyInventory().get(0).use(this);
			this.setSpecialAction(true);
			for (int row = 0; row < Game.map.length; row++) {
				for (int col = 0; col < Game.map[row].length; col++) {
					Game.map[row][col].setVisible(true);
				}
			}
		} else {
			throw new exceptions.NoAvailableResourcesException("Insufficient Supplies");
		}
	}

}
