package model.world;

import java.util.Random;

public class TrapCell extends Cell {
	private Random rand = new Random();
	private int random = 10 * rand.nextInt(4);
	
	private int trapDamage = random;

	public TrapCell() {
		super();
	}
	
	public int getTrapDamage() {
		return trapDamage;
	}

}
