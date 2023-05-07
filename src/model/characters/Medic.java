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
			this.getSupplyInventory().remove(0);
			this.getTarget().setCurrentHp(getMaxHp());
		}
		else {
			throw new exceptions.NoAvailableResourcesException("Insufficient Resources");
		}
	}


}
