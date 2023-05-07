package model.characters;

import exceptions.NoAvailableResourcesException;

public class Fighter extends Hero{

	
	public Fighter(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
		
	}

	
	@Override
	public void useSpecial() throws NoAvailableResourcesException{
		//When a supply is used, Fighter can attack as many times in a turn 
		//without costing action points, for 1 turn.
	}
	
	
	
	

}
