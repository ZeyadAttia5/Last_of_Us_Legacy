package model.collectibles;
import engine.Game;
import model.characters.Hero;

public class Vaccine implements Collectible {
	public Vaccine() {
	}

	@Override
	public void pickUp(Hero h) {
		h.getVaccineInventory().add(this);
		
	}

	@Override
	public void use(Hero h) {
		
		Game.vaccinesUsed = Game.vaccinesUsed + 1;
		h.getVaccineInventory().remove((this));
		
	}

}
