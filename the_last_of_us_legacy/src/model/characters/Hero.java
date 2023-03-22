package model.characters;

import model.collectibles.*;
import java.util.ArrayList;

public abstract class Hero extends Character {
	private int actionsAvailable;
	private int maxActions;
	private boolean specialAction;
	private ArrayList<Vaccine> vaccineInventory;
	private ArrayList<Supply> supplyInventory;

	public Hero(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg);
		this.maxActions = maxActions;
		this.vaccineInventory = new ArrayList<Vaccine>();
		this.supplyInventory = new ArrayList<Supply>();
		this.specialAction = false;
		setActionsAvailable(maxActions);
	}

	public void setActionsAvailable(int actionsAvailable) {
		this.actionsAvailable = actionsAvailable;
	}

	public int getActionsAvailable() {
		return actionsAvailable;
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

	public boolean isSpecialAction() {
		return specialAction;
	}

	public void setSpecialAction(boolean specialAction) {
		this.specialAction = specialAction;
	}
}
