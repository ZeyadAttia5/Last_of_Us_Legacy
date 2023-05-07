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
	
	@Override
	public void onCharacterDeath() {
		Game.zombies.remove(this);
		Game.zombiesRemoved.add(new Zombie());
	}
	
	public void attack() throws InvalidTargetException{
			if(getTarget() instanceof Hero)
				getTarget().setCurrentHp(getCurrentHp() - getAttackDmg());
			else
				throw new exceptions.InvalidTargetException("Invalid Target, You Cannot Attack Other Heros");
			getTarget().setHasBeenAttacked(true);
		}
		
}


