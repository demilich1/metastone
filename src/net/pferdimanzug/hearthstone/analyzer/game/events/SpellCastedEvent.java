package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;

public class SpellCastedEvent extends GameEvent {

	private final int playerId;

	public SpellCastedEvent(GameContext context, int playerId) {
		super(context);
		this.playerId = playerId;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.SPELL_CASTED;
	}

	public int getPlayerId() {
		return playerId;
	}

}
