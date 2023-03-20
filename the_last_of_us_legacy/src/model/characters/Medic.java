package model.characters;

public class Medic extends Hero{

	public Medic(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
		setName(name);
		setMaxHp(maxHp);
		setAttackDmg(attackDmg);
		setActionsAvailable(maxActions);
	}
}
