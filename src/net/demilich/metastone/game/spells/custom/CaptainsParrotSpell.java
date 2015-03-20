package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class CaptainsParrotSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(SpellDesc.build(CaptainsParrotSpell.class));
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		CardCollection pirateCards = SpellUtils.getCards(player.getDeck(), card -> card.getTag(GameTag.RACE) == Race.PIRATE);
		if (pirateCards.isEmpty()) {
			return;
		}
		Card randomPirateCard = pirateCards.getRandom();
		player.getDeck().remove(randomPirateCard);
		context.getLogic().receiveCard(player.getId(), randomPirateCard);
	}

}
