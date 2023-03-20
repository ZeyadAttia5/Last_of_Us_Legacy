package model.characters;
import java.awt.*;

public abstract class Character {
	private String name;
	private Point location;
	private int maxHp;
	private int currentHp;
	private int attackDmg;
	private Character target;
	
	public Character(String name, int maxHp, int attackDmg) {
		setName(name);
		setMaxHp(maxHp);
		setAttackDmg(attackDmg);
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setLocation(Point location) {
		this.location = location;
	}
	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}
	public void setCurrentHp(int currentHp) {
		this.currentHp = currentHp;
	}
	public void setAttackDmg(int attackDmg) {
		this.attackDmg = attackDmg;
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
	public int getMaxHp() {
		return maxHp;
	}
	public int getCurrentHp() {
		return currentHp;
	}
	public int getAttackDmg() {
		return attackDmg;
	}
	public Character getTarget() {
		return target;
	}
}
