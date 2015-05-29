package net.demilich.metastone.game.cards.desc;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

public class SecretCardDesc extends SpellCardDesc {
	
	public EventTriggerDesc trigger;

	@Override
	public Card createInstance() {
		return new SecretCard(this);
	}

}
