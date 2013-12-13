package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.HeroPowerAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.Hero;

public class SteadyShot extends HeroPower {

	public static final int DAMAGE = 2;

	public SteadyShot() {
		super("Steady Shot");
	}

	@Override
	public PlayCardAction play() {
		return new HeroPowerAction(this) {
			{
				setTargetRequirement(TargetRequirement.NONE);
				setEffectHint(EffectHint.NEGATIVE);
			}

			@Override
			protected void cast(GameContext context, Player player) {
				Hero enemyHero = context.getOpponent(player).getHero();
				context.getLogic().damage(enemyHero, DAMAGE);
			}
		};

	}

}
