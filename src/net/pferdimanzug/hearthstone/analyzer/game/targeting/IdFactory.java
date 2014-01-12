package net.pferdimanzug.hearthstone.analyzer.game.targeting;

public class IdFactory {
	
	private int id;
	
	public int generateId() {
		return ++id;
	}
	
	public void reset() {
		id = 0;
	}

}
