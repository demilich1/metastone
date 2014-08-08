package net.pferdimanzug.hearthstone.analyzer.game.targeting;

import net.pferdimanzug.hearthstone.analyzer.game.logic.CustomCloneable;

public class IdFactory extends CustomCloneable {
	
	public static final int UNASSIGNED = 0;
	
	private int id;
	
	public IdFactory() {
	}
	
	private IdFactory(int resumeId) {
		this.id = resumeId;
	}
	
	@Override
	public IdFactory clone() {
		return new IdFactory(id);
	}
	
	public int generateId() {
		return ++id;
	}
	
}
