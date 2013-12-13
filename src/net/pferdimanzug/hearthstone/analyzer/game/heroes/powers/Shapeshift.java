package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.HeroPowerAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;

public class Shapeshift extends HeroPower {
	
	public Shapeshift() {
		super("Shapeshift");
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
				player.getHero().modifyArmor(+1);
				player.getHero().setBaseAttack(1);
			}
		};
	}

}
