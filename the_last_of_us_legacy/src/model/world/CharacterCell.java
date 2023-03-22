package model.world;

public class CharacterCell extends Cell {
	 private Character character;
	 private boolean isSafe;
	 
	 public CharacterCell() {
		 super();
	}
	 
	 public void setisSafe(boolean isSafe) {
		 this.isSafe = isSafe;
	 }
	 
	 public boolean isSafe() {
		 return isSafe;
	 }
	 
	 public void setCharacter(Character character) {
		 this.character = character;
	 }
	 
	 public Character getCharacter() {
		 return character;
	 }	
	 


}
