package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;
import net.pferdimanzug.hearthstone.analyzer.game.events.SummonEvent;

public class MinionCardPlayedTrigger extends GameEventTrigger {

	@Override
	public GameEventType interestedIn() {
		return GameEventType.SUMMON;
	}

	@Override
	public boolean fire(GameEvent event, Actor host) {
		SummonEvent summonEvent = (SummonEvent) event;
		return summonEvent.getSource() != null && summonEvent.getSource().getCardType() == CardType.MINION;
	}

}
