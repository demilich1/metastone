package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Tracking extends SpellCard {

	// Tracking description:
	// "Look at the top three cards of your deck.
	// Draw one and discard the others."
	// ------------------
	// this is tough to implement correctly, because it
	// involves player meta-game knowledge
	// so for now, implemented as: draw 3, pick a random
	// TODO: offer AI choice of discarded cards
	private class TrackingSpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			if (player.getDeck().isEmpty()) {
				//TODO: assuming tracking does nothing at all when deck is empty
				// need to check with real game though
				return;
			}
			List<Card> drawnCards = new ArrayList<>();
			for (int i = 0; i < Math.min(3, player.getDeck().getCount()); i++) {
				drawnCards.add(player.getDeck().removeFirst());
			}
			context.getLogic().receiveCard(player.getId(), drawnCards.remove(0));
			while (!drawnCards.isEmpty()) {
				player.getGraveyard().add(drawnCards.remove(0));
			}
		}
		
	}
	
	public Tracking() {
		super("Tracking", Rarity.FREE, HeroClass.HUNTER, 1);
		setSpell(new TrackingSpell());
		setTargetRequirement(TargetSelection.NONE);
	}
	
	

}
