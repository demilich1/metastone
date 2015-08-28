package net.demilich.metastone.game.spells.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

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
		Map<SpellArg, Object> arguments = SpellDesc.build(TrackingSpell.class);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
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
