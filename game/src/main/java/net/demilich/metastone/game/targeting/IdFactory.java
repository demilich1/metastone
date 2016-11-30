package net.demilich.metastone.game.targeting;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.logic.CustomCloneable;

import java.io.Serializable;

public class IdFactory extends CustomCloneable implements Serializable {
	public static final int UNASSIGNED = -1;
	public static final int PLAYER_1 = GameContext.PLAYER_1;
	public static final int PLAYER_2 = GameContext.PLAYER_2;

	private int nextId;

	public IdFactory() {
		nextId = PLAYER_2 + 1;
	}

	public IdFactory(int resumeId) {
		this.nextId = resumeId;
	}

	@Override
	public IdFactory clone() {
		return new IdFactory(nextId);
	}

	public synchronized int generateId() {
		int result = nextId;
		nextId += 1;
		return result;
	}

	public int getInternalId() {
		return nextId;
	}
}
