package model.characters;

import java.util.ArrayList;

public class Hero extends Character {
	private int actionsAvailable;
	private int maxActions;
	private boolean specialAction;
	private ArrayList<Vaccine> vaccineInventory;
	private ArrayList<Supply> supplyInventory;
	public Hero(String name, int maxHp,int attackDmg, int maxActions ) {
		super(name, maxHp, attackDmg);
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
	public void SetSpecialAction(boolean specialAction) {
		this.specialAction = specialAction;
	}
	public boolean getSpecialAction() {
		return specialAction;
	}
	public ArrayList getVaccineInventory() {
		return vaccineInventory;
	}
	public ArrayList getSupplyInventory() {
		return supplyInventory;
	}
}
