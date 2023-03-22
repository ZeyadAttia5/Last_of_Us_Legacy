package model.characters;

public class Zombie extends Character {
	private static int ZOMBIES_COUNT = 0;
<<<<<<< Updated upstream
	public Zombie(String name,int maxHp,int attackDmg) {
		super(name, maxHp, attackDmg);
		String k = "Zombie" + " " + (ZOMBIES_COUNT+1) + "";
		setName(k);
		setMaxHp(40);
		setAttackDmg(10);
		//setCurrentHp(40);
=======
	public Zombie() {
		super("Zombie " + (ZOMBIES_COUNT+1), 40, 10);
>>>>>>> Stashed changes
		ZOMBIES_COUNT++;
	}

}
