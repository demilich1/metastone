package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class DivineFavor extends SpellCard {

	public DivineFavor() {
		super("Divine Favor", Rarity.RARE, HeroClass.PALADIN, 3);
		setDescription("Draw cards until you have as many in hand as your opponent.");
		setSpell(new DivineFavorSpell());
		
		setTargetRequirement(TargetSelection.NONE);
	}
	
	private class DivineFavorSpell extends DrawCardSpell {
		
		public DivineFavorSpell() {
			super(0, TargetPlayer.SELF);
		}

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Player opponent = context.getOpponent(player);
			int cardDifference = opponent.getHand().getCount() - player.getHand().getCount();
			if (cardDifference <= 0) {
				return;
			}
			setNumberOfCards(cardDifference);
			super.onCast(context, player, target);
		}
		
		
		
	}

}
