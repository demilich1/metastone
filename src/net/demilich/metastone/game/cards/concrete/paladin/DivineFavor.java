package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.valueprovider.ValueProvider;
import net.demilich.metastone.game.targeting.TargetSelection;

public class DivineFavor extends SpellCard {

	public DivineFavor() {
		super("Divine Favor", Rarity.RARE, HeroClass.PALADIN, 3);
		setDescription("Draw cards until you have as many in hand as your opponent.");
		ValueProvider matchOpponentCardCount = new ValueProvider(null) {
			
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
		setSpell(DrawCardSpell.create(matchOpponentCardCount, TargetPlayer.SELF));
		
		setTargetRequirement(TargetSelection.NONE);
	}



	@Override
	public int getTypeId() {
		return 242;
	}
}
