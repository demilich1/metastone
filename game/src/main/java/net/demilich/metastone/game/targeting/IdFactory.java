package net.demilich.metastone.game.targeting;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.logic.CustomCloneable;

public class IdFactory extends CustomCloneable {

	public static final int UNASSIGNED = -1;
	public static final int PLAYER_1 = GameContext.PLAYER_1;
	public static final int PLAYER_2 = GameContext.PLAYER_2;

	private int id;

	public IdFactory() {
		id = PLAYER_2 + 1;
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
