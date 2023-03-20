package model.characters;

public class Explorer extends Hero{

	public Explorer(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
		setName(name);
		setMaxHp(maxHp);
		setAttackDmg(attackDmg);
		setActionsAvailable(maxActions);
	}
}
