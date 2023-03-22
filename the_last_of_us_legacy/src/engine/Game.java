package engine;
package model.characters;
import java.io.File;
import java.util.*;
import model.world.Cell;

public class Game {
	static ArrayList<Hero> availableHeros;
	static ArrayList<Hero> heros;
	static ArrayList<Zombie> zombies;
	static Cell [][] map;

	public Game() {}
	
	public static void loadHeros(String filePath) throws Exception {
		Scanner Heros = new Scanner(new File("C:\\Users\\amrkh\\Desktop\\Heros.csv"));
		Heros.useDelimiter(",");
		int i = 0;
	    while (Heros.hasNext()) {
		availabeHeros = Heros.next();	
	    }
	    Heros.close();
	}

}
