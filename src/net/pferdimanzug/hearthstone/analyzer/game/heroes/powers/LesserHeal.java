package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.HeroPowerAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;

public class LesserHeal extends HeroPower {
	
	public static final int HEALING = +2;
	
	public LesserHeal() {
		super("Lesser Heal");
	}

	@Override
	public PlayCardAction play() {
		return new HeroPowerAction(this) {
			{
				setTargetRequirement(TargetRequirement.ANY);
				setEffectHint(EffectHint.POSITIVE);
			}
			
			@Override
			protected void cast(GameContext context, Player player) {
				context.getLogic().heal(getTarget(), HEALING);
			}
		};
	}

}
