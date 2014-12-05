package net.pferdimanzug.hearthstone.analyzer.game.events;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;

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
