package model.characters;
import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;

import java.awt.Point;


public abstract class Character {
	private String name;
	private Point location;
	private int maxHp;
	private int currentHp;
	private int attackDmg;
	private Character target;
	private boolean hasBeenAttacked;
	
	
	public Character() {
	}
	

	public Character(String name, int maxHp, int attackDmg) {
		this.name=name;
		this.maxHp = maxHp;
		this.currentHp = maxHp;
		this.attackDmg = attackDmg;
	}
		
	public Character getTarget() {
		return target;
	}

	public void setTarget(Character target) {
		this.target = target;
	}
	
	public String getName() {
		return name;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(int currentHp) {
		if(currentHp < 0) 
			this.currentHp = 0;
		else if(currentHp > maxHp) 
			this.currentHp = maxHp;
		else 
			this.currentHp = currentHp;
	}

	public int getAttackDmg() {
		return attackDmg;
	}
	
	public void attack () throws InvalidTargetException, NotEnoughActionsException{
			//SubClass Implementation
	}
	
	void defend(Character c) {
		if (HasBeenAttacked()) {
			
		}
	}
	void onCharacterDeath() {
		
	}


	public boolean HasBeenAttacked() {
		return hasBeenAttacked;
	}


	public void setHasBeenAttacked(boolean hasBeenAttacked) {
		this.hasBeenAttacked = hasBeenAttacked;
	}
	

}
