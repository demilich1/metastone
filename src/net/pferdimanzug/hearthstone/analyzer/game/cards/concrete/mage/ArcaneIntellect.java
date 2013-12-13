package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.spells.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;

public class ArcaneIntellect extends SpellCard {

	public ArcaneIntellect() {
		super("Arcane Intellect", Rarity.FREE, HeroClass.MAGE, 3);
	}

	@Override
	public PlayCardAction play() {
		return new PlayCardAction(this) {
			{
				setTargetRequirement(TargetRequirement.NONE);
				setEffectHint(EffectHint.POSITIVE);
				setActionType(ActionType.SPELL);
			}
			
			@Override
			protected void cast(GameContext context, Player player) {
				context.getLogic().drawCard(player);
				context.getLogic().drawCard(player);
			}
		};
	}

}
