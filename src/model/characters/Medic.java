package model.characters;
import exceptions.*;


public class Medic extends Hero {
	//Heal amount  attribute - quiz idea
	

	public Medic(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
	}
	
	@Override
	public void useSpecial() throws NoAvailableResourcesException{
		//Whenever a supply is used, allows Medic to other heros or themselves
		if(this.getSupplyInventory().size() > 0) {
			Hero target = (Hero) this.getTarget();
			this.setSpecialAction(true);
			this.getSupplyInventory().get(0).use(this);
			target.setCurrentHp(target.getMaxHp());
		}
		else {
			throw new exceptions.NoAvailableResourcesException("Insufficient Supplies");
		}
	}


}
