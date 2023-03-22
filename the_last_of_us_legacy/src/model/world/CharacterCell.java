package model.world;

import model.characters.Character;
public class CharacterCell extends Cell {
	 private Character character;
	 private boolean isSafe;
	 
	 public CharacterCell() {
		 super();
		 this.character = character;
	}
	 
	 public void setisSafe(boolean isSafe) {
		 this.isSafe = isSafe;
	 }
	 
	 public boolean isSafe() {
		 return isSafe;
	 }
	 
	// public void setCharacter(Character character) {
	//	 this.character = character;
	 //}
	 
	// public Character getCharacter() {
		 //return character;
	 //}	
	 


}
