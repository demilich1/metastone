package net.demilich.metastone.game.actions;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.targeting.CardLocation;
import net.demilich.metastone.game.targeting.CardReference;

public abstract class HeroPowerAction extends PlayCardAction {

	public HeroPowerAction(int playerId, int cardId) {
		super(new CardReference(playerId, CardLocation.HERO_POWER, cardId, "Hero power"));
		setActionType(ActionType.HERO_POWER);
	}
	
	@Override
	public void execute(GameContext context, int playerId) {
		context.getLogic().useHeroPower(playerId);
		play(context, playerId);
	}
	
	@Override
	public String getPromptText() {
		return "[Use hero power]";
	}
	
}
