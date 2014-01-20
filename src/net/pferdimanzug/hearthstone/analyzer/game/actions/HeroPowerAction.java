package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.HeroPower;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public abstract class HeroPowerAction extends PlayCardAction {

	public HeroPowerAction(HeroPower heroPower) {
		super(heroPower);
		setActionType(ActionType.HERO_POWER);
	}
	
	@Override
	public void execute(GameContext context, int playerId) {
		context.getLogic().useHeroPower(playerId, getHeroPower());
		play(context, playerId);
	}
	
	private HeroPower getHeroPower() {
		return (HeroPower) getCard();
	}

	@Override
	public TargetSelection getTargetRequirement() {
		return getHeroPower().getTargetRequirement();
	}
	
}
