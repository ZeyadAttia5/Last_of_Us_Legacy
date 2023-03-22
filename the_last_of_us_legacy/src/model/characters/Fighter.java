package model.characters;

public class Fighter extends Hero{

	public Fighter(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
		setName(name);
		setMaxHp(maxHp);
		setAttackDmg(attackDmg);
		setActionsAvailable(maxActions);
		//setCurrentHp(maxHp);
	}

}
