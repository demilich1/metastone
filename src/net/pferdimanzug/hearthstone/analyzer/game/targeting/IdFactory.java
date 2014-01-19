package net.pferdimanzug.hearthstone.analyzer.game.targeting;

public class IdFactory implements Cloneable {
	
	public static final int UNASSIGNED = 0;
	
	private int id;
	
	@Override
	public IdFactory clone() {
		try {
			return (IdFactory) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int generateId() {
		return ++id;
	}
	
	public void reset() {
		id = 0;
	}

}
