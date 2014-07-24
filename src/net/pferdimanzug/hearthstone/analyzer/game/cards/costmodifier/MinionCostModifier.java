package net.pferdimanzug.hearthstone.analyzer.game.cards.costmodifier;

import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEventType;

public class MinionCostModifier extends CardCostModifier {
	
	public MinionCostModifier(int manaModifier) {
		super(CardType.MINION, manaModifier, false);
	}

	@Override
	public boolean interestedIn(GameEventType eventType) {
		return false;
	}

	@Override
	public void onGameEvent(GameEvent event) {
		// TODO Auto-generated method stub
		
	}


}
