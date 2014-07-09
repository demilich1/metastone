package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.IValueProvider;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class DivineFavor extends SpellCard {

	public DivineFavor() {
		super("Divine Favor", Rarity.RARE, HeroClass.PALADIN, 3);
		setDescription("Draw cards until you have as many in hand as your opponent.");
		IValueProvider matchOpponentCardCount = new IValueProvider() {
			
			@Override
			public int provideValue(GameContext context, Player player, Entity target) {
				Player opponent = context.getOpponent(player);
				int cardDifference = opponent.getHand().getCount() - player.getHand().getCount();
				if (cardDifference <= 0) {
					return 0;
				}
				return cardDifference;
			}
		};
		setSpell(new DrawCardSpell(matchOpponentCardCount, TargetPlayer.SELF));
		
		setTargetRequirement(TargetSelection.NONE);
	}

}
