package model.characters;

public class Zombie extends Character {
	private static int ZOMBIES_COUNT;
	public Zombie(String name,int maxHp,int attackDmg) {
		super(name, maxHp, attackDmg);
		setName("Zombie" + " " + (ZOMBIES_COUNT+1) + "");
		setMaxHp(40);
		setAttackDmg(10);
		ZOMBIES_COUNT++;
	}

}
