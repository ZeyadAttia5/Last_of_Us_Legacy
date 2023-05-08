package model.collectibles;

import engine.Game;
import model.characters.Hero;
import model.world.CharacterCell;
import java.util.Random;

public class Vaccine implements Collectible {
	public Vaccine() {
	}

	@Override
	public void pickUp(Hero h) {
		h.getVaccineInventory().add(this);

	}

	@Override
	public void use(Hero h) {
		//Game.vaccinesUsed = Game.vaccinesUsed + 1;
		
		Random rand = new Random();
		h.getVaccineInventory().remove((0));
		int indexOfZombie = Game.zombies.indexOf(h.getTarget());
		Hero newHero = Game.availableHeroes.get(rand.nextInt(Game.availableHeroes.size()));
		newHero.setLocation(h.getTarget().getLocation());
		((CharacterCell) Game.map[h.getTarget().getLocation().x][h.getTarget().getLocation().y]).setCharacter(newHero);
		int newHeroIndex = Game.availableHeroes.indexOf(newHero);
		
		Game.heroes.add(newHero);
		Game.availableHeroes.remove(newHeroIndex);
		Game.zombies.remove(indexOfZombie);

	}

}
