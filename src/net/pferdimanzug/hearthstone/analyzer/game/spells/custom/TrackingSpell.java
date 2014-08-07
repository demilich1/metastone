package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

//Tracking description:
// "Look at the top three cards of your deck.
// Draw one and discard the others."
// ------------------
// this is tough to implement correctly, because it
// involves player meta-game knowledge
// so for now, implemented as: draw 3, pick a random
// TODO: offer AI choice of discarded cards
public class TrackingSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(TrackingSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		if (player.getDeck().isEmpty()) {
			// TODO: assuming tracking does nothing at all when deck is empty
			// need to check with real game though
			return;
		}
		List<Card> drawnCards = new ArrayList<>();
		for (int i = 0; i < Math.min(3, player.getDeck().getCount()); i++) {
			drawnCards.add(player.getDeck().removeFirst());
		}
		context.getLogic().receiveCard(player.getId(), drawnCards.remove(0));
	}

}
