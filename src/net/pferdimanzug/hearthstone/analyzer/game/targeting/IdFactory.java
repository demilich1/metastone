package net.pferdimanzug.hearthstone.analyzer.game.targeting;

public class IdFactory {
	
	public static final int UNASSIGNED = 0;
	
	private int id;
	
	public int generateId() {
		return ++id;
	}
	
	public void reset() {
		id = 0;
	}

}
