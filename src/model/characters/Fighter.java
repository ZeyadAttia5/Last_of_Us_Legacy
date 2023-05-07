package model.characters;

import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;

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

	
	
	
	

}
