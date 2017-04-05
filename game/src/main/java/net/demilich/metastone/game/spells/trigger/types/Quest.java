package net.demilich.metastone.game.spells.trigger.types;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.GameEventTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class Quest extends SpellTrigger {

	private Card source;

	public Quest(GameEventTrigger trigger, SpellDesc spell, Card source) {
		super(trigger, spell);
		this.source = source;
	}

	public Card getSource() {
		return source;
	}

	@Override
	protected void onFire(int ownerId, SpellDesc spell, GameEvent event) {
		super.onFire(ownerId, spell, event);
		Player owner = event.getGameContext().getPlayer(ownerId);
		event.getGameContext().getLogic().questTriggered(owner, this);
		expire();
	}

	@Override
	public void onGameEvent(GameEvent event) {
		super.onGameEvent(event);
	}

}
