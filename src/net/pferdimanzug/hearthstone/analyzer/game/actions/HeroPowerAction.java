package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardLocation;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardReference;

public abstract class HeroPowerAction extends PlayCardAction {

	public HeroPowerAction(int playerId, int cardId) {
		super(new CardReference(playerId, CardLocation.HERO_POWER, cardId));
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
