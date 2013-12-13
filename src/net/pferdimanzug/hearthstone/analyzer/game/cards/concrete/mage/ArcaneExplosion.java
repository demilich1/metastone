package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.spells.DamageSpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class ArcaneExplosion extends DamageSpellCard {

	public ArcaneExplosion() {
		super("Arcane Explosion", Rarity.FREE, HeroClass.MAGE, 2, 1);
	}

	@Override
	public PlayCardAction play() {
		return new PlayCardAction(this) {
			{
				setTargetRequirement(TargetRequirement.NONE);
				setActionType(ActionType.SPELL);
				setEffectHint(EffectHint.NEGATIVE);
			}
			
			@Override
			protected void cast(GameContext context, Player player) {
				Player opponent = context.getOpponent(player);
				for (Minion minion : opponent.getMinions()) {
					context.getLogic().damage(minion, getDamage());
				}
			}
		};
	}

	

}
