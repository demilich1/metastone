package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.Hero;

public class ArmorGainedEvent extends GameEvent {

	private final Hero hero;

	public ArmorGainedEvent(GameContext context, Hero hero) {
		super(context, hero.getOwner(), -1);
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

}
