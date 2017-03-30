package net.demilich.metastone.game.spells.trigger.types;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.GameEventTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class Secret extends SpellTrigger {

	private Card source;

	public Secret(GameEventTrigger trigger, SpellDesc spell, Card source) {
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
		event.getGameContext().getLogic().secretTriggered(owner, this);
		expire();
	}

	@Override
	public void onGameEvent(GameEvent event) {
		if (event.getGameContext().getActivePlayerId() == getOwner()) {
			return;
		}
		super.onGameEvent(event);
	}

}
