package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.spells.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;

public class Assassinate extends SpellCard {

	public Assassinate() {
		super("Assasinate", Rarity.FREE, HeroClass.ROGUE, 5);
	}

	@Override
	public PlayCardAction play() {
		return new PlayCardAction(this) {
			{
				setTargetRequirement(TargetRequirement.ENEMY_MINIONS);
				setActionType(ActionType.SPELL);
				setEffectHint(EffectHint.NEGATIVE);
			}

			@Override
			protected void cast(GameContext context, Player player) {
				context.getLogic().destroy(getTarget());
			}
		};
	}

}
