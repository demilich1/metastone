package net.demilich.metastone.game.targeting;

import net.demilich.metastone.game.logic.CustomCloneable;

public class IdFactory extends CustomCloneable {

	public static final int UNASSIGNED = -1;

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
