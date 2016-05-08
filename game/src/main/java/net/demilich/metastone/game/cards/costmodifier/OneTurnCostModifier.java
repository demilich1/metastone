package net.demilich.metastone.game.cards.costmodifier;

import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.desc.manamodifier.CardCostModifierDesc;
import net.demilich.metastone.game.spells.trigger.GameEventTrigger;
import net.demilich.metastone.game.spells.trigger.TurnStartTrigger;

public class OneTurnCostModifier extends CardCostModifier {

	private GameEventTrigger turnStartTrigger = new TurnStartTrigger();

	public OneTurnCostModifier(CardCostModifierDesc desc) {
		super(desc);
	}

	@Override
	public OneTurnCostModifier clone() {
		OneTurnCostModifier clone = (OneTurnCostModifier) super.clone();
		clone.turnStartTrigger = (GameEventTrigger) turnStartTrigger.clone();
		return clone;
	}

	@Override
	public boolean interestedIn(GameEventType eventType) {
		if (turnStartTrigger.interestedIn() == eventType) {
			return true;
		}

		return super.interestedIn(eventType);
	}

	@Override
	public void onGameEvent(GameEvent event) {
		Entity host = event.getGameContext().resolveSingleTarget(getHostReference());
		if (event.getEventType() == turnStartTrigger.interestedIn() && turnStartTrigger.fires(event, host)) {
			expire();
		}

		super.onGameEvent(event);
	}
	
	@Override
	public boolean oneTurnOnly() {
		return true;
	}

	@Override
	public void setOwner(int playerIndex) {
		super.setOwner(playerIndex);
		turnStartTrigger.setOwner(playerIndex);
	}

}
