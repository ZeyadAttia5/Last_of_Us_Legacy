package model.characters;

import exceptions.NoAvailableResourcesException;

public class Explorer extends Hero {
	

	public Explorer(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
		
	}

	@Override
	public void useSpecial() throws NoAvailableResourcesException {
		//Whenever a supply is used, allows the player to see the entirety of the map
		//for 1 turn
	}

	
}
