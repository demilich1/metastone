package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.HeroPowerAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;

public class LifeTap extends HeroPower {

	public static final int DAMAGE = 2;

	public LifeTap() {
		super("Life Tap");
	}

	@Override
	public PlayCardAction play() {
		return new HeroPowerAction(this) {
			{
				setTargetRequirement(TargetRequirement.NONE);
				setEffectHint(EffectHint.POSITIVE);
			}

			@Override
			protected void cast(GameContext context, Player player) {
				context.getLogic().damage(player.getHero(), DAMAGE);
				context.getLogic().drawCard(player);
			}
		};

	}

}
