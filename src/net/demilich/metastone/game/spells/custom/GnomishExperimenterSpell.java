package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.Chicken;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class GnomishExperimenterSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(GnomishExperimenterSpell.class);
		desc.setTarget(EntityReference.NONE);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Card card = context.getLogic().drawCard(player.getId());
		if (card == null) {
			return;
		}
		if (card.getCardType() == CardType.MINION) {
			player.getHand().remove(card);
			context.getLogic().receiveCard(player.getId(), new Chicken());
		}
	}

}
