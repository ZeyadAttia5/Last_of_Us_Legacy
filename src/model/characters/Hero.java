package model.characters;


import engine.Game;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;

import java.awt.Point;
import java.util.ArrayList;

import model.collectibles.Collectible;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.Cell;
import engine.Game;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;
import exceptions.*;
import exceptions.NotEnoughActionsException;

public abstract class Hero extends Character {

	private int actionsAvailable;
	private int maxActions;
	private ArrayList<Vaccine> vaccineInventory;
	private ArrayList<Supply> supplyInventory;
	private boolean specialAction;

	public Hero(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg);
		this.maxActions = maxActions;
		this.actionsAvailable = maxActions;
		this.vaccineInventory = new ArrayList<Vaccine>();
		this.supplyInventory = new ArrayList<Supply>();
		this.specialAction = false;

	}

	public boolean isSpecialAction() {
		return specialAction;
	}

	public void setSpecialAction(boolean specialAction) {
		this.specialAction = specialAction;
	}

	public int getActionsAvailable() {
		return actionsAvailable;
	}

	public void setActionsAvailable(int actionsAvailable) {
		this.actionsAvailable = actionsAvailable;
	}

	public int getMaxActions() {
		return maxActions;
	}

	public ArrayList<Vaccine> getVaccineInventory() {
		return vaccineInventory;
	}

	public ArrayList<Supply> getSupplyInventory() {
		return supplyInventory;
	}

	public void move(Direction d) throws exceptions.MovementException, NotEnoughActionsException {
		Point currLocation = this.getLocation();
		Point newLocation = currLocation;
		if (actionsAvailable > 0) {
			if (d == Direction.UP) {
				newLocation.y += 1;
				if (newLocation.y >= 15)
					throw new exceptions.MovementException("Invalid Move");
			} else if (d == Direction.DOWN) {
				newLocation.y -= 1;
				if (newLocation.y < 0)
					throw new exceptions.MovementException("Invalid Move");
			} else if (d == Direction.LEFT) {
				newLocation.x -= 1;
				if (newLocation.x < 0)
					throw new exceptions.MovementException("Invalid Move");
			} else if (d == Direction.RIGHT) {
				newLocation.x += 1;
				if (newLocation.x >= 15)
					throw new exceptions.MovementException("Invalid Move");
			}
			if(Game.map[newLocation.x][newLocation.y] instanceof CharacterCell) {
				throw new exceptions.MovementException("Invalid Move");
			}
			else if(Game.map[newLocation.x][newLocation.y] instanceof CollectibleCell) {
				this.setLocation(newLocation);
				actionsAvailable--;
				((CollectibleCell) Game.map[newLocation.x][newLocation.y]).getCollectible().pickUp(this);
			}
			else if(Game.map[newLocation.x][newLocation.y] instanceof TrapCell) {
				this.setLocation(newLocation);
				actionsAvailable--;
				this.setCurrentHp(getCurrentHp() - ((TrapCell) Game.map[newLocation.x][newLocation.y]).getTrapDamage());
			}
			else {
				this.setLocation(newLocation);
				actionsAvailable--;
			}
			//After the hero moves, the new location becomes a CharacterCell
			Game.map[newLocation.x][newLocation.y] = new CharacterCell(this);
			
			//set the visibility to true for all adjacent cells
			for(Cell adjCell : getAdjacentCells()) {
				adjCell.setVisible(true);
			}
		}
		else {
			throw new exceptions.NotEnoughActionsException("Not Enough Action Points");
		}
	}
	
	public void useSpecial() throws NoAvailableResourcesException {
		
	}


	@Override
	public void onCharacterDeath() {
		Game.heroes.remove(this);
		
		
	}
	public void attack() throws InvalidTargetException, NotEnoughActionsException{
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
