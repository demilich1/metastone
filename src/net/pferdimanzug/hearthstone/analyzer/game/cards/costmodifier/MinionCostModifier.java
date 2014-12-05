package net.pferdimanzug.hearthstone.analyzer.game.cards.costmodifier;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;

public class MinionCostModifier extends CardCostModifier {

	private Race requiredRace;

	public MinionCostModifier(int manaModifier) {
		super(CardType.MINION, manaModifier, false);
	}

	protected boolean appliesTo(Card card) {
		if (!super.appliesTo(card)) {
			return false;
		}
		if (getRequiredRace() != null && card.getTag(GameTag.RACE) != getRequiredRace()) {
			return false;
		}
		return true;
	}

	public Race getRequiredRace() {
		return requiredRace;
	}

	@Override
	public boolean interestedIn(GameEventType eventType) {
		return false;
	}

	@Override
	public void onGameEvent(GameEvent event) {
	}

	public void setRequiredRace(Race requiredRace) {
		this.requiredRace = requiredRace;
	}

}
