package model.characters;

import engine.Game;

import java.awt.Point;
import java.util.ArrayList;

import model.characters.Character;
import model.collectibles.Collectible;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.Cell;
import engine.Game;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;
import exceptions.*;

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
				newLocation.x += 1;
				if (newLocation.x >= 15)
					throw new exceptions.MovementException("Invalid Move");
			} else if (d == Direction.DOWN) {
				newLocation.x -= 1;
				if (newLocation.x < 0)
					throw new exceptions.MovementException("Invalid Move");
			} else if (d == Direction.LEFT) {
				newLocation.y -= 1;
				if (newLocation.y < 0)
					throw new exceptions.MovementException("Invalid Move");
			} else if (d == Direction.RIGHT) {
				newLocation.y += 1;
				if (newLocation.y >= 15)
					throw new exceptions.MovementException("Invalid Move");
			}
			if (((CharacterCell) Game.map[newLocation.x][newLocation.y]).getCharacter() != null) {
				throw new exceptions.MovementException("Invalid Move");
			} else if (((CollectibleCell) Game.map[newLocation.x][newLocation.y]).getCollectible() != null) {
				((CollectibleCell) Game.map[newLocation.x][newLocation.y]).getCollectible().pickUp(this);
				Game.map[currLocation.x][currLocation.y] = new CharacterCell(this);
			} else if (Game.map[newLocation.x][newLocation.y] instanceof TrapCell) {
				this.setCurrentHp(getCurrentHp() - ((TrapCell) Game.map[newLocation.x][newLocation.y]).getTrapDamage());
			} else {
				// do nothing
			}
		} else {
			throw new exceptions.NotEnoughActionsException("Not Enough Action Points");
		}
		// After the hero moves, the new location becomes a CharacterCell
		((CharacterCell) Game.map[currLocation.x][currLocation.y]).setCharacter(null);
		((CharacterCell) Game.map[newLocation.x][newLocation.y]).setCharacter(this);
		this.setLocation(newLocation);
		actionsAvailable--;
		// set the visibility to true for all adjacent cells
		for (Cell adjCell : getAdjacentCells()) {
			adjCell.setVisible(true);
		}
	}

	public void useSpecial() throws NoAvailableResourcesException, InvalidTargetException {

	}

	public void cure() throws NoAvailableResourcesException, InvalidTargetException, NotEnoughActionsException {
		if (this.getActionsAvailable() <= 0) {
			throw new exceptions.NotEnoughActionsException("You don't have enough action points to spend");
		}
		if (!(this.getTarget() instanceof Zombie)) {
			throw new exceptions.InvalidTargetException("you can only cure zombies");
		}
		if (!(this.isTargetAdjacent())) {
			throw new exceptions.InvalidTargetException("The zombie is not adjacent");
		}
		if (!this.getVaccineInventory().isEmpty()) {
			this.getVaccineInventory().get(0).use(this);
			this.actionsAvailable--;
		} else {
			throw new exceptions.NoAvailableResourcesException("You do not have any Vaccines to cure the Zombie");
		}
	}

	@Override
	public void onCharacterDeath() {
		Game.heroes.remove(this);
	}

	public void attack() throws InvalidTargetException, NotEnoughActionsException {

		if (this.getTarget() == null) {
			throw new InvalidTargetException("No target is selected");
		}
		if (this.isTargetAdjacent()) {
			if (getActionsAvailable() > 0) {
				setActionsAvailable(getActionsAvailable() - 1);
				if (getTarget() instanceof Zombie)
					getTarget().setCurrentHp(getCurrentHp() - getAttackDmg());
				else
					throw new exceptions.InvalidTargetException("Invalid Target, You Cannot Attack Other Heros.");
			} else
				throw new NotEnoughActionsException("Not Enough Actions Available.");
			getTarget().getAttackers().add(this);
		} else
			throw new exceptions.InvalidTargetException("Target is not adjacent.");
	}

	public void defend(Character c) throws exceptions.InvalidTargetException {
		if (maxActions > 0) {
			maxActions--;
			if (!getAttackers().isEmpty()) {
				if (getAttackers().contains(c)) {
					c.setCurrentHp(c.getCurrentHp() - (c.getAttackDmg() / 2));
					getAttackers().clear();
				} else
					throw new InvalidTargetException("This target did not attack you.");
			} else
				throw new InvalidTargetException("You have not been attacked.");
		} else
			throw new InvalidTargetException("Not Enough Actions Available.");
	}
}
