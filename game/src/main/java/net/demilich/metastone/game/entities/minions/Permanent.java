package net.demilich.metastone.game.entities.minions;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.cards.PermanentCard;
import net.demilich.metastone.game.entities.EntityType;

public class Permanent extends Summon {

	public Permanent(PermanentCard sourceCard) {
		super(sourceCard);
		setRace(Race.NONE);
	}

	@Override
	public Permanent clone() {
		Permanent clone = (Permanent) super.clone();
		return clone;
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.PERMANENT;
	}

	@Override
	public boolean isDestroyed() {
		return hasAttribute(Attribute.DESTROYED);
	}
	
}
