package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ISpell;

public class Tracking extends SpellCard {

	public Tracking() {
		super("Tracking", Rarity.FREE, HeroClass.HUNTER, 1);
		setSpell(new TrackingSpell());
		setTargetRequirement(TargetSelection.NONE);
	}
	
	// Tracking description:
	// "Look at the top three cards of your deck.
	// Draw one and discard the others."
	// ------------------
	// this is tough to implement correctly, because it
	// involves player meta-game knowledge
	// so for now, implemented as: draw 3, pick a random
	// TODO: offer AI choice of discarded cards
	private class TrackingSpell implements ISpell {

		@Override
		public void cast(GameContext context, Player player, Entity target) {
			
			// this is a tough one
			List<Card> drawnCards = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				drawnCards.add(player.getDeck().removeFirst());
			}
			player.getHand().add(drawnCards.remove(0));
			while (!drawnCards.isEmpty()) {
				player.getGraveyard().add(drawnCards.get(0));
			}
		}
		
	}
	
	

}
