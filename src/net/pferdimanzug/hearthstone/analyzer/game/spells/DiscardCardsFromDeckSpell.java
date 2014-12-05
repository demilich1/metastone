package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardLocation;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class DiscardCardsFromDeckSpell extends Spell {
	
	public static SpellDesc create(int howMany) {
		SpellDesc desc = new SpellDesc(DiscardCardsFromDeckSpell.class);
		desc.setValue(howMany);
		desc.setTarget(EntityReference.NONE);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int howMany = desc.getValue();
		for (int i = 0; i < howMany; i++) {
			// Question: If I have no cards left and my Fel Reaver discards 3, do I draw 3 Fatigues or do I only Fatigue more when I draw a card?
			// Answer: Fel Reaver won't trigger fatigue
			// Source: Blue post
			if (player.getDeck().isEmpty()) {
				return;
			}
			Card card = context.getLogic().drawCard(player.getId());
			player.getHand().remove(card);
			card.setLocation(CardLocation.VOID);
		}
	}

}
