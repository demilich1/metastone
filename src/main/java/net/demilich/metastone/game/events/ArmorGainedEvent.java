package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.Hero;

public class ArmorGainedEvent extends GameEvent {

	private final Hero hero;
	private final int playerId;

	public ArmorGainedEvent(GameContext context, Hero hero) {
		super(context);
		this.playerId = hero.getOwner();
		this.hero = hero;
	}

	@Override
	public Entity getEventTarget() {
		return hero;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.ARMOR_GAINED;
	}

	public int getPlayerId() {
		return playerId;
	}

}
