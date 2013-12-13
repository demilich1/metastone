package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.spells.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;

public class TheCoin extends SpellCard {
	
	public static final int MANA_GAIN = +1;

	public TheCoin() {
		super("The Coin", Rarity.FREE, HeroClass.ANY, 0);
	}

	@Override
	public PlayCardAction play() {
		return new PlayCardAction(this) {
			{
				setTargetRequirement(TargetRequirement.NONE);
				setActionType(ActionType.SPELL);
				setEffectHint(EffectHint.POSITIVE);
			}
			
			@Override
			protected void cast(GameContext context, Player player) {
				context.getLogic().modifyCurrentMana(player, MANA_GAIN);
			}
		};
		
	}

}
