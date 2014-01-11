package net.pferdimanzug.hearthstone.analyzer.game.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.HeroPower;

public abstract class HeroPowerAction extends PlayCardAction {

	public HeroPowerAction(HeroPower heroPower) {
		super(heroPower);
		setActionType(ActionType.HERO_POWER);
	}
	
	@Override
	public void execute(GameContext context, Player player) {
		context.getLogic().useHeroPower(player, getHeroPower());
		play(context, player);
	}
	
	private HeroPower getHeroPower() {
		return (HeroPower) getCard();
	}

	@Override
	public TargetSelection getTargetRequirement() {
		return getHeroPower().getTargetRequirement();
	}

}
