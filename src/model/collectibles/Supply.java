package model.collectibles;

import model.characters.Hero;

public class Supply implements Collectible  {

	

	
	public Supply() {
		
	}

	@Override
	public void pickUp(Hero h) {
		h.getSupplyInventory().add(this);
		
	}

	@Override
	public void use(Hero h) {
<<<<<<< Updated upstream
		h.getSupplyInventory().remove((h.getSupplyInventory().size()-1));
=======
		h.getSupplyInventory().remove(this);
>>>>>>> Stashed changes
		
	}


	
		
		

}
