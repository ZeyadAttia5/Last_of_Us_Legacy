package model.characters;

import engine.Game;
import java.awt.Point;
import java.util.ArrayList;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.Cell;
import engine.Game;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

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

	public void move(Direction d) throws exceptions.MovementException {
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
		}
	}

	// returns an array of points
	public ArrayList<Point> getAdjacentCharactersLocation() {
		ArrayList<Point> adjacentCharList = new ArrayList<Point>();

		int[] rowOffsets = { -1, 0, 1 }; // offsets for adjacent rows
		int[] colOffsets = { -1, 0, 1 }; // offsets for adjacent columns
		for (int i = 0; i < Game.map.length; i++) {
			for (int j = 0; j < Game.map[i].length; j++) {
				// loop over adjacent cells
				for (int rowOffset : rowOffsets) {
					for (int colOffset : colOffsets) {
						// calculate adjacent cell coordinates
						int adjRow = i + rowOffset;
						int adjCol = j + colOffset;

						// check if adjacent cell is within the Game.map bounds
						if (adjRow >= 0 && adjRow < Game.map.length && adjCol >= 0
								&& adjCol < Game.map[adjRow].length) {
							if (Game.map[adjRow][adjCol] instanceof CharacterCell) {
								Point adjLocation = new Point(adjRow, adjCol);
								adjacentCharList.add(adjLocation);
							}
						}
					}
				}
			}
		}
		return adjacentCharList;
	}

	public void onCharacterDeath() {
		Game.heroes.remove(this);
	}
}
