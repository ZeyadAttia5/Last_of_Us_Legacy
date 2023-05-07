package model.characters;


import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;

import exceptions.NoAvailableResourcesException;


public class Fighter extends Hero{

	
	public Fighter(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
		
		
		
	}


	public void attack() throws InvalidTargetException, NotEnoughActionsException {
		if(getActionsAvailable() > 0 ) {
			setActionsAvailable(getActionsAvailable() - 1);
			if(getTarget() instanceof Zombie)
				getTarget().setCurrentHp(getCurrentHp() - getAttackDmg());
			else
				throw new exceptions.InvalidTargetException("Invalid Target, You Cannot Attack Other Heros");
		}
		else 
			throw new NotEnoughActionsException("Not Enough Actions Available");
		getTarget().setHasBeenAttacked(true);
	}


	
	@Override
	public void useSpecial() throws NoAvailableResourcesException{
		//When a supply is used, Fighter can attack as many times in a turn 
		//without costing action points, for 1 turn.
	}

	
	
	
	

}
