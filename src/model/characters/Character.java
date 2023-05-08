package model.characters;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.world.Cell;
import model.world.CharacterCell;

import java.awt.Point;
import java.util.ArrayList;

public abstract class Character {
	private String name;
	private Point location;
	private int maxHp;
	private int currentHp;
	private int attackDmg;
	private Character target;
	private ArrayList<Character> attackers;

	public Character() {
	}

	public Character(String name, int maxHp, int attackDmg) {
		this.name = name;
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
		if (currentHp < 0)
			this.currentHp = 0;
		else if (currentHp > maxHp)
			this.currentHp = maxHp;
		else
			this.currentHp = currentHp;
	}

	public int getAttackDmg() {
		return attackDmg;
	}

	public void attack() throws InvalidTargetException, NotEnoughActionsException {
		// SubClass Implementation
	}

	public void defend(Character c) throws exceptions.InvalidTargetException {

	}

	public void onCharacterDeath() {

	}

	public ArrayList<Cell> getAdjacentCells() {
		ArrayList<Cell> adjacentCharList = new ArrayList<Cell>();

		int[] rowOffsets = { -1, 0, 1 }; // offsets for adjacent rows
		int[] colOffsets = { -1, 0, 1 }; // offsets for adjacent columns
		// loop over adjacent cells
		for (int rowOffset : rowOffsets) {
			for (int colOffset : colOffsets) {
				// calculate adjacent cell coordinates
				int adjRow = this.getLocation().y + rowOffset;
				int adjCol = this.getLocation().x + colOffset;

				// check if adjacent cell is within the Game.map bounds
				if (adjRow >= 0 && adjRow < Game.map.length && adjCol >= 0 && adjCol < Game.map[adjRow].length) {
					adjacentCharList.add(Game.map[adjRow][adjCol]);
				}
			}
		}
		return adjacentCharList;
	}

	public boolean isTargetAdjacent() {
		boolean targetAdjacent = false;
		ArrayList<Cell> adjacentCells = this.getAdjacentCells();
		for (Cell adjCell : adjacentCells) {
			if (adjCell instanceof CharacterCell) {
				if (((CharacterCell) adjCell).getCharacter() == this.getTarget()) {
					targetAdjacent = true;
					break;
				}
			}
		}
		return targetAdjacent;
	}

	public ArrayList<Character> getAttackers() {
		return attackers;
	}

	public void setAttackers(ArrayList<Character> attackers) {
		this.attackers = attackers;
	}

}
