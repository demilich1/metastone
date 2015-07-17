package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class ShuffleToDeckSpell extends Spell {

	public static SpellDesc create(TargetPlayer targetPlayer, Card card) {
		Map<SpellArg, Object> arguments = SpellDesc.build(ShuffleToDeckSpell.class);
		arguments.put(SpellArg.CARD, card);
		arguments.put(SpellArg.TARGET, EntityReference.NONE);
		arguments.put(SpellArg.TARGET_PLAYER, targetPlayer);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		String cardId = (String) desc.get(SpellArg.CARD);
		Card card = CardCatalogue.getCardById(cardId);
		shuffleToDeck(player, card);
	}

	private void shuffleToDeck(Player player, Card card) {
		Card randomCard = player.getDeck().getRandom();
		if (randomCard == null) {
			player.getDeck().add(card.clone());
		} else {
			player.getDeck().addAfter(card.clone(), randomCard);
		}
	}

}
