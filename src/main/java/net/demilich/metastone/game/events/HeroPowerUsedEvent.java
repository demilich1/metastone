package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.heroes.powers.HeroPower;

public class HeroPowerUsedEvent extends GameEvent {

	private final int playerId;
	private final HeroPower heroPower;

	public HeroPowerUsedEvent(GameContext context, int playerId, HeroPower heroPower) {
		super(context);
		this.playerId = playerId;
		this.heroPower = heroPower;
	}
	
	@Override
	public Entity getEventSource() {
		return null;
	}

	@Override
	public Entity getEventTarget() {
		return getHeroPower();
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.HERO_POWER_USED;
	}

	public HeroPower getHeroPower() {
		return heroPower;
	}

	public int getPlayerId() {
		return playerId;
	}

}
